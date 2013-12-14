package preprocessing;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * A metric space based on raw GPS data from OpenStreetMap.org.
 * 
 * The set of points in the metric space corresponds to the GPS coordinates in a
 * GPS trace, and the distance method is based on the shortest path metric of a
 * neighbourhood graph on the set of points.
 */
public class GPSMetricSpace extends NeighbourhoodGraph {

	private static final long serialVersionUID = 639481610289888732L;

	/**
	 * Constructs a metric space based on the raw GPS data in the specified file.
	 * 
	 * @param filename the name of the file containing the data
	 * @param alpha the constant used in calculating the underlying alpha complex
	 * @param epsilon the constant used in reducing the point set
	 * @throws IOException if an error occurs while reading the GPS trace file
	 */
	public GPSMetricSpace(String filename, double alpha, double epsilon) throws IOException {
		super(alpha);
		Set<Point2D> allCoordinates = parse(filename);
		setVertices(getEpsilonNet(allCoordinates, epsilon));
	}

	/**
	 * Extracts all GPS coordinates from the specified file and stores them in a
	 * set.
	 * 
	 * @param filename the file containing the coordinates
	 * @return the set of coordinates
	 * @throws IOException if an error occurs while reading the GPS trace file
	 */
	private Set<Point2D> parse(String filename) throws IOException {

		Set<Point2D> coordinates = new HashSet<Point2D>();

		// read the GPS trace file
		RandomAccessFile file = new RandomAccessFile(filename, "rw");

		byte[] data = new byte[(int) (file.length())];
		int a = file.read(data);
		file.close();

		String s1 = new String(data); // switch the byte array into a string

		// switch to signalSymbols (in front of the coordinates)
		// and replace other symbols

		String s2 = s1.replaceAll("lon=", "!");
		s2 = s2.replaceAll("lat=", "#");
		s2 = s2.replace("\"", "");
		s2 = s2.replace("?", "");
		s2 = s2.replace("<", " ");
		s2 = s2.replace(">", "");
		s2 = s2.replace("/", "");
		s2 = s2.replace(":", "");
		s2 = s2.replace("=", "");

		// delete all literals

		for (int i = 65; i < 91; i++) {
			String x = Character.toString((char) i);
			String y = Character.toString((char) (i + 32));
			s2 = s2.replace(x, "");
			s2 = s2.replace(y, "");
		}

		// String of all coordinates
		s2 = s2.replace("-", "/");

		/*
		 * the tokenizer takes all out of the string s2, replaces the last
		 * signalSymbols and turns the coordinates into Integers
		 */
		StringTokenizer tokenizer = new StringTokenizer(s2);
		LinkedList<Integer> coordX = new LinkedList<Integer>();
		LinkedList<Integer> coordY = new LinkedList<Integer>();

		while (tokenizer.hasMoreTokens()) {

			String write2 = (String) tokenizer.nextToken();

			if (write2.contains("!")) {

				write2 = write2.replace("!", "");
				write2 = write2.replace(".", "");
				coordX.add(Integer.parseInt(write2));
			}
			if (write2.contains("#")) {

				write2 = write2.replace("#", "");
				write2 = write2.replace(".", "");
				coordY.add(Integer.parseInt(write2));
			}
		}

		// loop -> place them into the set

		for (int i = 0; i < coordY.size(); i++) {
			Point2D point = new Point2D.Double();
			point.setLocation(coordX.poll(), coordY.poll());
			coordinates.add(point);
		}

		// returns the set

		return coordinates;
	}

	private Set<Point2D> getEpsilonNet(Set<Point2D> coordinates, double epsilon) {
		// TODO: implement this method (using reducePs code?)
		return null;
	}

	private Set<Point2D> reducePs(Set<Point2D> coordinates, int parts) {

		// divide the coordinate system into parts*parts pieces

		Point2D[][] reducedPoints = new Point2D[parts][parts];

		Set<Point2D> copyOfCoordinates = new HashSet<Point2D>();
		copyOfCoordinates = coordinates;

		Set<Point2D> returnSet = new HashSet<Point2D>();

		for (int i = 0; i < parts; i++) {
			for (int j = 0; j < parts; j++) {
				while (copyOfCoordinates.iterator().hasNext()) {
					Point2D point = copyOfCoordinates.iterator().next();
					if (point.getX() < (copyOfCoordinates.size() / parts) * i) {
						if (point.getY() < (copyOfCoordinates.size() / parts)
								* j) {
							if (reducedPoints[i][j] != null) {
								reducedPoints[i][j] = point;
							} else {
								double x, y;
								x = (reducedPoints[i][j].getX() + point.getX()) / 2;
								y = (reducedPoints[i][j].getY() + point.getY()) / 2;
								reducedPoints[i][j].setLocation(x, y);

							}
						}
					}
				}
			}

			System.out.println(reducedPoints.toString());

			for (int k = 0; k < parts; k++) {
				for (int l = 0; l < parts; l++) {
					if (reducedPoints[k][l] != null) {
						returnSet.add(reducedPoints[k][l]);
					}
				}
			}
		}

		return returnSet;
	}
}
