package preprocessing;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.MetricGraph;

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

	private Map<Point2D,List<Edge<Point2D>>> adjacencyLists = new HashMap<>();

	/**
	 * Creates a neighbourhood graph based on the specified set of points.
	 * 
	 * @param points the nodes of the neighbourhood graph
	 * @param alpha the constant used in calculating the underlying alpha complex
	 */
	public NeighbourhoodGraph(Set<Point2D> points, double alpha) {
		// TODO implement constructor
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

}
