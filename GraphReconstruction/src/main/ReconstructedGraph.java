package main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A metric graph that has been reconstructed from a metric space.
 * Each vertex is represented by the set of points corresponding to it
 * in the original metric space. The edges also contain references to
 * the corresponding points.
 *
 * @param <P> The type of points in the original metric space.
 */
public class ReconstructedGraph<P> extends HashSet<Set<P>> implements MetricGraph<Set<P>> {

	private static final long serialVersionUID = 5005484792683676217L;

	/** A map storing the adjacencies between vertices in this graph. */
	protected Map<Set<P>, Set<Edge<Set<P>>>> adjacencyLists = new HashMap<>();

	/** A map storing the shortest path distances between each pair of points. */
	// TODO save space by avoiding redundancy?
	protected Map<Set<P>, Map<Set<P>, Double>> distanceMap = new HashMap<>();

	/**
	 * Sets the vertices of this graph to consist of the specified point set.
	 * 
	 * @param points the new vertices
	 */
	public void setVertices(Set<Set<P>> points) {
		addAll(points);
	}

	/**
	 * Adds an edge from the source vertex to the target vertex with the specified distance.
	 * 
	 * @param sourceVertex the source vertex
	 * @param targetVertex the target vertex
	 * @param edgePoints the points corresponding to the new edge in the original metric space
	 * @param distance the length of the edge
	 */
	public void addEdge(Set<P> sourceVertex, Set<P> targetVertex, Set<P> edgePoints, double distance) {
		if (!adjacencyLists.containsKey(sourceVertex))
			adjacencyLists.put(sourceVertex, new HashSet<Edge<Set<P>>>());
		adjacencyLists.get(sourceVertex).add(new ReconstructedEdge(targetVertex, distance, edgePoints));
	}

	@Override
	public double distance(Set<P> a, Set<P> b) {
		if (distanceMap.containsKey(a) && distanceMap.get(a).containsKey(b))
			return distanceMap.get(a).get(b);
		else
			return Double.POSITIVE_INFINITY;
	}

	@Override
	public Set<Edge<Set<P>>> getNeighbours(Set<P> vertex) {
		if (!adjacencyLists.containsKey(vertex))
			adjacencyLists.put(vertex, new HashSet<Edge<Set<P>>>());
		return adjacencyLists.get(vertex);
	}

	/**
	 * An edge of a reconstructed graph that contains a reference
	 * to the corresponding set of points in the original metric space.
	 */
	public class ReconstructedEdge extends Edge<Set<P>> {

		/** The set of points corresponding to this edge in the original metric space. */
		public Set<P> points;

		/**
		 * Creates a new edge.
		 * 
		 * @param neighbour the vertex at the other end of this edge
		 * @param distance the length of this edge
		 * @param points the corresponding set of points in the original metric space
		 */
		public ReconstructedEdge(Set<P> neighbour, double distance, Set<P> points) {
			super(neighbour, distance);
			this.points = points;
		}
	}

}
