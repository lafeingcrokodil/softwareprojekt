package preprocessing;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The Delaunay triangulation of a set of points in two-dimensional Euclidean space.
 * 
 * Pseudocode: http://www.comp.nus.edu.sg/~tantc/ioi_training/CG/l10cs4235.pdf
 * 
 * @author Terese Haimberger
 */
public class DelaunayTriangulation {

	private Triangle root;

	/**
	 * Triangulates the specified set of points in two-dimensional
	 * Euclidean space.
	 * 
	 * @param points the set of points to be triangulated
	 */
	public DelaunayTriangulation(Set<Point2D> points) {
		Set<UninsertedPoint> uninserted = new HashSet<>();
		for (Point2D point : points)
			uninserted.add(new UninsertedPoint(point));
		root = getRoot(uninserted);
		for (UninsertedPoint point : uninserted)
			insert(point);
	}

	/**
	 * Creates the large triangle that encloses the entire triangulation.
	 * 
	 * @param uninserted the set of points to be inserted into the triangulation
	 * @return the large initial triangle
	 */
	private Triangle getRoot(Set<UninsertedPoint> uninserted) {
		final double MAX_COORD = getMaxCoordinate(uninserted);
		Point2D[] vertices = {
				new Point2D.Double(0, 3*MAX_COORD),
				new Point2D.Double(3*MAX_COORD, 0),
				new Point2D.Double(-3*MAX_COORD, -3*MAX_COORD)
		};
		Edge[] edges = {
				new Edge(vertices[0], vertices[1]),
				new Edge(vertices[1], vertices[2]),
				new Edge(vertices[2], vertices[0])
		};
		Triangle root = new Triangle(edges[0], uninserted);
		for (int i = 0; i < edges.length; i++) {
			edges[i].nextEdge = edges[(i+1) % 3];
			edges[i].triangle = root;
		}
		return root;
	}

	/**
	 * Get the maximum absolute value of any coordinate in
	 * the specified set of points.
	 * 
	 * @param points any set of points
	 * @return the maximum absolute value of a coordinate in the set
	 */
	private double getMaxCoordinate(Collection<? extends Point2D> points) {
		double currMax = 0;
		for (Point2D point : points) {
			double absX = Math.abs(point.getX());
			double absY = Math.abs(point.getY());
			currMax = absX > currMax ? absX : currMax;
			currMax = absY > currMax ? absY : currMax;
		}
		return currMax;
	}

	private void insert(UninsertedPoint point) {
		// TODO implement insert method
	}

	/**
	 * A triangle in a triangulation.
	 */
	private class Triangle {
		/** Any one of the three edges enclosing this triangle. */
		Edge enclosingEdge;

		/** The uninserted points contained in this triangle. */
		Set<UninsertedPoint> uninserted;

		/**
		 * Creates a new triangle enclosed by the specified list of
		 * edges and containing the specified set of uninserted points.
		 * 
		 * @param enclosingEdge any one of the three edges enclosing the triangle
		 * @param uninserted the uninserted points in the triangle
		 */
		Triangle(Edge enclosingEdge, Set<UninsertedPoint> uninserted) {
			this.enclosingEdge = enclosingEdge;
			this.uninserted = uninserted;
		}
	}

	/**
	 * A directed edge in a triangulation.
	 */
	private class Edge {
		/** The origin of this edge. */
		Point2D origin;

		/** The destination of this edge. */
		Point2D destination;

		/**
		 * The triangle directly to the right of this edge
		 * when facing along the edge to the destination point.
		 */
		Triangle triangle;

		/**
		 * The next edge that borders on this edge's triangle.
		 */
		Edge nextEdge;

		/**
		 * The edge that connects the same two points as this edge
		 * but points in the opposite direction (thus belonging to
		 * the triangle to the left of this edge). May be null if
		 * there is no triangle to the left of this edge.
		 */
		Edge counterpart;

		/**
		 * Creates a new directed edge with the specified origin and destination.
		 * 
		 * @param origin the origin of this edge
		 * @param destination the destination of this edge
		 */
		Edge(Point2D origin, Point2D destination) {
			this.origin = origin;
			this.destination = destination;
		}
	}

	/**
	 * A point that has not yet been inserted into the triangulation.
	 */
	private class UninsertedPoint extends Point2D.Double {
		/**
		 * The triangle in the triangulation that contains this point.
		 */
		Triangle enclosingTriangle;

		/**
		 * Creates a new uninserted point at the specified location.
		 * 
		 * @param point the location of the new uninserted point
		 */
		UninsertedPoint(Point2D point) {
			super(point.getX(), point.getY());
		}
	}

}
