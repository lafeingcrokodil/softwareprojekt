package preprocessing;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.MetricGraph;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.triangulate.DelaunayTriangulationBuilder;

/**
 * A graph consisting of the vertices and edges of an alpha complex.
 * <p>
 * An alpha complex is a subcomplex of the Delaunay triangulation of
 * a set of points. A face of the Delaunay triangulation is included
 * in the alpha complex if it passes the alpha test (i.e. its
 * circumcircle is empty and has a radius no larger than alpha) or
 * if it is part of a higher-dimensional face that passes the test.
 * <p>
 * The idea is to avoid directly connecting points that are far away
 * from each other, while also avoiding a mess of edges among points
 * that are close to each other. (This last point is a major
 * difference between this graph and a Rips-Vietoris graph, where
 * all points within a certain distance of each other are directly
 * connected.)
 * 
 * @author Terese Haimberger
 */
public class NeighbourhoodGraph extends HashSet<Point2D> implements MetricGraph<Point2D>{

	private static final long serialVersionUID = -6029923560094150514L;

	/**
	 * A map storing the adjacencies between nodes in this graph.
	 */
	private Map<Point2D, List<Edge<Point2D>>> adjacencyLists = new HashMap<>();

	/**
	 * Creates a neighbourhood graph based on the specified set of points.
	 * 
	 * @param points the nodes of the neighbourhood graph
	 * @param alpha the constant used in calculating the underlying alpha complex
	 */
	public NeighbourhoodGraph(Set<Point2D> points, double alpha) {
		addAllTriangulationEdges(points);
		removeLongEdges(alpha);
	}

	@Override
	public double distance(Point2D a, Point2D b) {
		// TODO implement distance method (shortest path distance)
		return 0;
	}

	@Override
	public List<Edge<Point2D>> getNeighbours(Point2D node) {
		return adjacencyLists.get(node);
	}

	/**
	 * Adds all edges of the Delaunay triangulation of the given set of points to
	 * this graph. The lengths of each edge is the Euclidean distance between
	 * the edge's two vertices.
	 * 
	 * @param points a set of points in two-dimensional space
	 */
	private void addAllTriangulationEdges(Set<Point2D> points) {
		DelaunayTriangulationBuilder triangulator = new DelaunayTriangulationBuilder();
		triangulator.setSites(toCoordinateSet(points));
		// ASSUMPTION getEdges() always returns a MultiLineString (as claimed in documentation)
		MultiLineString edges = (MultiLineString) triangulator.getEdges(new GeometryFactory());
		for (int i = 0; i < edges.getNumGeometries(); i++) { // loop over edges
			Coordinate[] coordinates = edges.getGeometryN(i).getCoordinates();
			// ASSUMPTION each edge has exactly two vertices
			addEdge(coordinates[0], coordinates[1]);
		}
	}

	/**
	 * Remove all edges whose circumcircle has a radius greater than alpha.
	 * 
	 * @param alpha the maximum circumcircle radius
	 */
	private void removeLongEdges(double alpha) {
		// TODO implement removeLongEdges method
	}

	/**
	 * Converts a set of Point2D objects to a set of Coordinate objects.
	 * 
	 * @param points the point set to be converted
	 * @return the same point set represented as Coordinate objects
	 */
	private Set<Coordinate> toCoordinateSet(Set<Point2D> points) {
		Set<Coordinate> coordinates = new HashSet<Coordinate>();
		for (Point2D point : points)
			coordinates.add(new Coordinate(point.getX(), point.getY()));
		return coordinates;
	}

	/**
	 * Adds an edge to this graph. After this method is called,
	 * the two specified coordinates will be considered adjacent
	 * to each other (in both directions).
	 * 
	 * @param a a vertex of the edge to be added
	 * @param b the other vertex of the edge to be added
	 */
	private void addEdge(Coordinate a, Coordinate b) {
		Point2D pointA = new Point2D.Double(a.x, a.y);
		Point2D pointB = new Point2D.Double(b.x, b.y);
		addAdjacency(pointA, pointB);
		addAdjacency(pointB, pointA);
	}

	/**
	 * Adds a one-sided adjacency to this graph.
	 * 
	 * @param point the point, to which an adjacency is to be added
	 * @param adjacentPoint the adjacent point
	 */
	private void addAdjacency(Point2D point, Point2D adjacentPoint) {
		if (!adjacencyLists.containsKey(point))
			adjacencyLists.put(point, new ArrayList<Edge<Point2D>>());
		adjacencyLists.get(point).add(new Edge<>(adjacentPoint, point.distance(adjacentPoint)));
	}

}
