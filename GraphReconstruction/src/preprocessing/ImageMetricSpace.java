package preprocessing;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import main.MetricSpace;

/**
 * A metric space based on an image (e.g. a hand-drawn sketch of a graph).
 * 
 * The set of points in the metric space corresponds to a set of
 * similar-coloured pixels in the image (e.g. black pixels in
 * a black and white image).
 * 
 * The distance method is based on the shortest path metric of
 * a neighbourhood graph on the set of points.
 * 
 * @author Terese Haimberger
 */
public class ImageMetricSpace extends HashSet<Point> implements MetricSpace<Point> {

	private static final long serialVersionUID = 8681862627788355587L;

	/**
	 * Creates a new ImageMetricSpace based on the specified image file.
	 * 
	 * @param filename the name of the image file
	 */
	public ImageMetricSpace(String filename) {
		// TODO implement constructor
	}

	@Override
	public double distance(Point a, Point b) {
		// TODO implement distance() method
		return 0;
	}

	/**
	 * Extracts the coordinates of all black pixels in a black
	 * and white image and stores them in a set.
	 * 
	 * @param filename the black and white image file
	 * @return the set of coordinates
	 */
	private Set<Point> extractPixels(String filename) {
		// TODO implement extractPixels() method
		return null;
	}

}
