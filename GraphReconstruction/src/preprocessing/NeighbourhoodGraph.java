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
 * An alpha complex is a subcomplex of the Delaunay triangulation of a set of
 * points. A face of the Delaunay triangulation is included in the alpha complex
 * if it passes the alpha test (i.e. its circumcircle is empty and has a radius
 * no larger than alpha) or if it is part of a higher-dimensional face that
 * passes the test.
 * <p>
 * The idea is to avoid directly connecting points that are far away from each
 * other, while also avoiding a mess of edges among points that are close to
 * each other. (This last point is a major difference between this graph and a
 * Rips-Vietoris graph, where all points within a certain distance of each other
 * are directly connected.)
 * 
 * @author Terese Haimberger
 */
public class NeighbourhoodGraph extends HashSet<Point2D> implements MetricGraph<Point2D>{

	private static final long serialVersionUID = -6029923560094150514L;

	/**
	 * A map storing the adjacencies between vertices in this graph.
	 */
	private Map<Point2D, List<Edge<Point2D>>> adjacencyLists = new HashMap<>();

	/**
	 * The constant used in calculating the underlying alpha complex.
	 */
	private double alpha;

	/**
	 * Creates a new empty neighbourhood graph.
	 * 
	 * @param alpha the constant used in calculating the underlying alpha complex
	 */
	public NeighbourhoodGraph(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * Sets the vertices of this graph to consist of the specified point set.
	 * 
	 * @param points the new vertices
	 */
	public void setVertices(Set<Point2D> points) {
		clear();        // remove any vertices that were previously added to this graph
		addAll(points); // add all specified points to the set of vertices

		// calculate the Delaunay triangulation of the point set
		DelaunayTriangulationBuilder triangulator = new DelaunayTriangulationBuilder();
		triangulator.setSites(toCoordinateSet(points));
		// ASSUMPTION getEdges() always returns a MultiLineString (as claimed in documentation)
		MultiLineString edges = (MultiLineString) triangulator.getEdges(new GeometryFactory());

		// add edges of the triangulation to this graph
		for (int i = 0; i < edges.getNumGeometries(); i++) { // loop over edges
			Coordinate[] coordinates = edges.getGeometryN(i).getCoordinates();
			// ASSUMPTION each edge has exactly two vertices
			Point2D a = new Point2D.Double(coordinates[0].x, coordinates[0].y);
			Point2D b = new Point2D.Double(coordinates[1].x, coordinates[1].y);
			double edgeLength = a.distance(b); // Euclidean distance
			// add edge to graph if its circumcircle has a radius of at most alpha
			if (edgeLength <= 2 * alpha) {
				addAdjacency(a, b, edgeLength);
				addAdjacency(b, a, edgeLength);
			}
		}
	}

	@Override
	public double distance(Point2D a, Point2D b) {
		// TODO implement distance method (shortest path distance)
		return 0;
	}

	@Override
	public List<Edge<Point2D>> getNeighbours(Point2D vertex) {
		return adjacencyLists.get(vertex);
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
	 * Adds a one-sided adjacency to this graph.
	 * 
	 * @param point the point, to which an adjacency is to be added
	 * @param adjacentPoint the adjacent point
	 */
	private void addAdjacency(Point2D point, Point2D adjacentPoint, double edgeLength) {
		if (!adjacencyLists.containsKey(point))
			adjacencyLists.put(point, new ArrayList<Edge<Point2D>>());
		adjacencyLists.get(point).add(new Edge<>(adjacentPoint, edgeLength));
	}

}
