package preprocessing;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import main.MetricSpace;

/**
 * A metric space based on raw GPS data from OpenStreetMap.org.
 * 
 * The set of points in the metric space corresponds to the
 * GPS coordinates in a GPS trace.
 * 
 * The distance method is based on the shortest path metric of
 * a neighbourhood graph on the set of points.
 * 
 * @author Daniel Theus
 */
public class GPSMetricSpace extends HashSet<Point2D> implements MetricSpace<Point2D> {

	private static final long serialVersionUID = 639481610289888732L;

	/**
	 * Constructs a metric space based on the raw GPS data
	 * in the specified file.
	 * 
	 * @param filename the name of the file containing the data
	 */
	public GPSMetricSpace(String filename) {
		// TODO implement constructor
	}

	@Override
	public double distance(Point2D a, Point2D b) {
		// TODO implement distance() method
		return 0;
	}

	/**
	 * Extracts all GPS coordinates from the specified file
	 * and stores them in a set.
	 * 
	 * @param filename the file containing the coordinates
	 * @return the set of coordinates
	 */
	private Set<Point2D> parse(String filename) {
		// TODO implement parse() method
		return null;
	}

}
