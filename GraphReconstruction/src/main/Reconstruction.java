package main;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * An implementation of the metric graph reconstruction algorithm described in
 * the paper by Aanjaneya et al.
 *
 * @author Moritz Walter
 * 
 * @param <P> The type of points in the input metric space.
 */
public class Reconstruction<P> {

	/** A working copy of the input metric space. */
	private MetricSpaceImplemented<P> workspace;

	/** The radius parameter used in the algorithm. */
	private double radius;

	/**
	 * Sets the metric space and radius to be used in the metric graph reconstruction.
	 * 
	 * @param space a metric space with an underlying metric graph
	 * @param r the radius parameter used in the reconstruction algorithm
	 */
	public Reconstruction(MetricSpace<P> space, double r) {
		workspace = new MetricSpaceImplemented<P>(space);
		radius = r;
	}

	/**
	 * Implementation of the metric space reconstruction algorithm.
	 * 
	 * @return the reconstructed metric graph
	 */
	public MetricGraph<Set<P>> reconstructMetricGraph() {
		// NOTE: #<number> refers to the line numbers in the paper's pseudocode

		System.out.println("I. Labelling points as edge or branch"); // #1

		for (P point : workspace){ // #2
			MetricSpaceImplemented<P> pointsInRadius = workspace.getPointsInRadius(point, 5 * radius / 3, radius); // #3
			RipsVietoris<P> ripsVietorisGraph = new RipsVietoris<P>(pointsInRadius, 4 * radius / 3); // #4
			if (ripsVietorisGraph.getDegree() == 2) { // #5
				workspace.labelAs(point, MetricSpaceImplemented.EDGE); // #6
			} else { // #7
				workspace.labelAs(point, MetricSpaceImplemented.PREL_BRANCH); // #8
			} // #9
		} // #10

		MetricSpaceImplemented<P> prelBranchPoints = workspace.getLabelledAs(MetricSpaceImplemented.PREL_BRANCH); // #11
		for (P point : prelBranchPoints)
			workspace.labelInRadius(point, 2 * radius);

		MetricSpaceImplemented<P> edgePoints = workspace.getLabelledAs(MetricSpaceImplemented.EDGE); // #12
		MetricSpaceImplemented<P> branchPoints = workspace.getLabelledAs(MetricSpaceImplemented.BRANCH); // #13

		System.out.println("II. Reconstructing the Graph Structure"); // # 14

		RipsVietoris<P> edgeGraph = new RipsVietoris<P>(edgePoints, 2 * radius); // #15
		RipsVietoris<P> vertexGraph = new RipsVietoris<P>(branchPoints, 2 * radius);
		Set<Set<P>> vertices = vertexGraph.getComponents(); // #16
		Set<Set<P>> edges = edgeGraph.getComponents();

		System.out.println("Number of vertices: " + vertices.size());
		System.out.println("Number of edges: " + edges.size());

		System.out.println("III. Reconstructing the Metric"); // #18
		System.out.println("This can take some time...");
		// we combined the last steps of the graph structure reconstruction with the metric reconstruction
		return matchEdges(vertices, edges); // #17/18
	}

	/**
	 * Matches edges of the partially reconstructed graph to the vertices at either end
	 * and sets the edge's distance.
	 * 
	 * @param vertices the vertices of the partially reconstructed graph
	 * @param edges the edges of the partially reconstructed graph
	 * @return the fully reconstructed graph
	 */
	private MetricGraph<Set<P>> matchEdges(Set<Set<P>> vertices, Set<Set<P>> edges) {

		// create a new graph with the specified vertices
		ReconstructedGraph<P> result = new ReconstructedGraph<>();
		result.setVertices(vertices);

		// set of edges that have not yet been used to connect two points (or one point to itself)
		Set<Set<P>> unclaimedEdges = new HashSet<>(edges);

		int progress = 0; // for the console output

		for (Set<P> sourceVertex : vertices) {

			System.out.println("Matching edges to vertices... " + (100 * (progress++) / vertices.size()) +"%");

			edgeLoop:
			for (Iterator<Set<P>> iter = unclaimedEdges.iterator(); iter.hasNext(); ) {
				Set<P> edge = iter.next();
				boolean adjacentToSource = false; // is the edge touching the source vertex?

				for (Set<P> targetVertex : vertices) {
					if (sourceVertex == targetVertex) continue; // loops will be handled later

					// check if any of the points in the edge has a neighbour that is in the vertex sets
					for (P edgePoint : edge) {
						// get the neighbours of the edge point
						Set<P> pointsInRadius = workspace.getPointsInRadius(edgePoint, 2 * radius, 0);
						// check if any of them are in the vertex sets
						for (P point : pointsInRadius) {
							adjacentToSource = adjacentToSource || sourceVertex.contains(point);
							if (adjacentToSource && targetVertex.contains(point)) {
								result.addEdge(sourceVertex, targetVertex, edge, getDiameter(edge) + 4 * radius);
								result.addEdge(targetVertex, sourceVertex, edge, getDiameter(edge) + 4 * radius);
								// the edge has been claimed and cannot belong to any other vertex or pair of vertices
								iter.remove(); // remove the edge from the set of unclaimed edges
								continue edgeLoop;
							}
						}
					}
				}
	
				if (adjacentToSource) { // the edge is adjacent to the source vertex, but to no other -> loop
					result.addEdge(sourceVertex, sourceVertex, edge, getDiameter(edge) + 4 * radius);
					iter.remove(); // remove the edge from the set of unclaimed edges
				}
			}
		}

		return result;
	}

	/**
	 * Gets the diameter of the specified set of points. The diameter is defined as
	 * the longest distance between any two points.
	 * 
	 * @param points a set of points
	 * @return the longest distance between any two points in the set
	 */
	private double getDiameter(Set<P> points) {
		/*
		 * We assume that the distance method calculates the shortest path
		 * distance between two points.
		 */
		double longestDistance = 0;

		for (P a : points) {
			for (P b : points) {
				longestDistance = Math.max(longestDistance, workspace.distance(a, b));
			}
		}

		return longestDistance;
	}

	/**
	 * Returns the working copy of the metric space.
	 */
	public MetricSpaceImplemented<P> getWorkspace() {
		return workspace;
	}

	/**
	 * Labels all points in the given metric space as branch or edge points.
	 * 
	 * @return the labelled metric space
	 */
	public static <P> MetricSpaceImplemented<P> labelPoints(MetricSpace<P> space, double radius) {

		MetricSpaceImplemented<P> workspace = new MetricSpaceImplemented<>(space);

		for (P point : workspace){
			MetricSpaceImplemented<P> pointsInRadius = workspace.getPointsInRadius(point, 5 * radius / 3, radius);
			RipsVietoris<P> ripsVietorisGraph = new RipsVietoris<P>(pointsInRadius, 4 * radius / 3);
			if (ripsVietorisGraph.getDegree() == 2)
				workspace.labelAs(point, MetricSpaceImplemented.EDGE);
			else
				workspace.labelAs(point, MetricSpaceImplemented.PREL_BRANCH);
		}

		MetricSpaceImplemented<P> prelBranchPoints = workspace.getLabelledAs(MetricSpaceImplemented.PREL_BRANCH);
		for (P point : prelBranchPoints)
			workspace.labelInRadius(point, 2 * radius);

		return workspace;
	}

}
