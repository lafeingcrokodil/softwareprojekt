package preprocessing;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

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
public class ImageMetricSpace extends NeighbourhoodGraph {

	private static final long serialVersionUID = 8681862627788355587L;

	/**
	 * Creates a new ImageMetricSpace based on a representative subset of the
	 * black pixels in the specified image file. If epsilon is set to 0,
	 * the entire set of pixels will be used.
	 * 
	 * @param filename the name of the image file
	 * @param epsilon the constant used in reducing the point set
	 * @param alpha the constant used in calculating the underlying alpha complex
	 * @throws IOException if an error occurs while reading the image
	 */
	public ImageMetricSpace(String filename, double epsilon, double alpha) throws IOException {
		super(alpha);
		BufferedImage image = ImageIO.read(new File(filename));
		Set<Point2D> pixels = extractPixels(image, Color.BLACK);
		if (epsilon != 0)
			pixels = new EpsilonNet(pixels, epsilon);
		setVertices(pixels);
		calculateAllDistances();
	}

	/**
	 * Constructs a neighbourhood graph (without distances!) based on
	 * the black pixels in the specified image file.
	 * 
	 * @param filename the name of the image file
	 * @param epsilon the constant used in reducing the point set
	 * @param alpha the constant used in calculating the underlying alpha complex
	 * @throws IOException if an error occurs while reading the image
	 */
	public static NeighbourhoodGraph preview(String filename, double epsilon, double alpha) throws IOException {
		BufferedImage image = ImageIO.read(new File(filename));
		Set<Point2D> pixels = extractPixels(image, Color.BLACK);
		if (epsilon != 0)
			pixels = new EpsilonNet(pixels, epsilon);
		NeighbourhoodGraph previewGraph = new NeighbourhoodGraph(alpha);
		previewGraph.setVertices(pixels);
		LOGGER.info("Done.");
		return previewGraph;
	}

	/**
	 * Extracts the coordinates of all pixels in the image
	 * with the specified colour and stores them in a set.
	 * 
	 * @param colour the colour of the pixels to be extracted
	 * @return the set of extracted pixel coordinates
	 */
	private static Set<Point2D> extractPixels(BufferedImage image, Color colour) {
		LOGGER.info("Extracting relevant pixel coordinates from image...");
		Set<Point2D> coordinates = new HashSet<Point2D>();
		Point topLeft = new Point(image.getMinX(), image.getMinY());
		Point bottomRight = new Point(topLeft.x + image.getWidth(), topLeft.y + image.getHeight());
		for (int y = topLeft.y; y < bottomRight.y; y++) {
			for (int x = topLeft.x; x < bottomRight.x; x++) {
				if (image.getRGB(x, y) == colour.getRGB()) {
					coordinates.add(new Point(x, y));
				}
			}
		}
		return coordinates;
	}

}
