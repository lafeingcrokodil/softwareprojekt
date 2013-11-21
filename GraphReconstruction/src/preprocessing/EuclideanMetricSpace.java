package preprocessing;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import main.MetricSpace;

/**
 * A subset of the two-dimensional Euclidean space.
 * 
 * @author Terese Haimberger
 */
public class EuclideanMetricSpace extends HashSet<Point2D> implements MetricSpace<Point2D> {

	private static final long serialVersionUID = -8100775106409469482L;

	/**
	 * Creates an empty Euclidean metric space.
	 */
	public EuclideanMetricSpace() {}

	/**
	 * Creates a Euclidean metric space with the specified set of points.
	 * 
	 * @param points the points to be included in the metric space
	 */
	public EuclideanMetricSpace(Set<Point2D> points) {
		addAll(points);
	}

	/**
	 * Creates a metric space with a randomly generated set of points.
	 * The x and y coordinates of the points lie between 0 (inclusive)
	 * and 1 (exclusive).
	 * 
	 * @param size the number of points to be generated
	 */
	public EuclideanMetricSpace(int size) {
		Random randomGen = new Random();
		for (int i = 0; i < size; i++) {
			double x = randomGen.nextDouble();
			double y = randomGen.nextDouble();
			add(new Point2D.Double(x, y));
		}
	}

	@Override
	public double distance(Point2D a, Point2D b) {
		return a.distance(b);
	}

}
