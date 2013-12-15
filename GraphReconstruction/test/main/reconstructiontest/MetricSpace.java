package maintest;


import java.util.Set;

/**
 * A set of points that supports the calculation of distances between points.
 *
 * @param <P> the type of points in the metric space
 */
public interface MetricSpace<P> extends Set<P> {

	/**
	 * Calculates the distance between two points in this metric space.
	 * 
	 * @param a a point in the metric space
	 * @param b another point in the metric space
	 * @return the distance between the two points
	 */
	double distance(P a, P b);
	
}
