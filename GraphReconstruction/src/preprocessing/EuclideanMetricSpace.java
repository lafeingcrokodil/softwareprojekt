package preprocessing;

import java.awt.geom.Point2D;
import java.util.HashSet;

import main.MetricSpace;

/**
 * A subset of the two-dimensional Euclidean space.
 * 
 * @author Terese Haimberger
 */
public class EuclideanMetricSpace extends HashSet<Point2D> implements MetricSpace<Point2D> {

	private static final long serialVersionUID = -8100775106409469482L;

	@Override
	public double distance(Point2D a, Point2D b) {
		return a.distance(b);
	}

}
