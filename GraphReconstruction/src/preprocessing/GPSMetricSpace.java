package preprocessing;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;

import main.MetricSpace;

/**
 * A metric space based on raw GPS data from OpenStreetMap.org.
 * 
 * The set of points in the metric space corresponds to the GPS coordinates in a
 * GPS trace.
 * 
 * The distance method is based on the shortest path metric of a neighbourhood
 * graph on the set of points.
 * 
 * 
 */
public class GPSMetricSpace extends HashSet<Point2D> implements
		MetricSpace<Point2D> {
	
	
	
	Set<Point2D> koordinates = new HashSet<Point2D>();

	private static final long serialVersionUID = 639481610289888732L;

	/**
	 * Constructs a metric space based on the raw GPS data in the specified
	 * file.
	 * 
	 * @param filename
	 *            the name of the file containing the data
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
	 * Extracts all GPS coordinates from the specified file and stores them in a
	 * set.
	 * 
	 * @param filename
	 *            the file containing the coordinates
	 * @return the set of coordinates
	 * @throws IOException 
	 */

	private Set<Point2D> parse(String filename) throws IOException {

		String path = filename;

		Set<Point2D> koordinates = new HashSet<Point2D>();

		// Read the GPS-Trace File
		RandomAccessFile file1 = new RandomAccessFile(path, "rw");

		byte[] data = new byte[(int) (file1.length())];
		int a = file1.read(data);
		file1.close();

		String s1 = new String(data); // switch the byte array into a string

		// switch to singalSymbols (in front of the koordinates)
		// and replace other Symbols

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

		// String of all koordinates
		s2 = s2.replace("-", "/");

		/*
		 * the tokenizer takes all out of the string s2 ,replaces the last
		 * singalSymbols and turns the koordinates into Integers
		 */
		StringTokenizer tokenizer = new StringTokenizer(s2);
		LinkedList<Integer> koordX = new LinkedList<Integer>();
		LinkedList<Integer> koordY = new LinkedList<Integer>();

		while (tokenizer.hasMoreTokens()) {

			String write2 = (String) tokenizer.nextToken();

			if (write2.contains("!")) {
				
				write2 = write2.replace("!", "");
				write2 = write2.replace(".", "");
				koordX.add(Integer.parseInt(write2));
			}
			if (write2.contains("#")) {
				

				write2 = write2.replace("#", "");
				write2 = write2.replace(".", "");
				koordY.add(Integer.parseInt(write2));
			}
		}

		// loop -> place them into the set

		for (int i = 0; i < koordY.size(); i++) {
			Point2D point = new Point2D.Double();
			point.setLocation(koordX.poll(), koordY.poll());
			koordinates.add(point);
		}

		// returns the set

		return koordinates;
	}

	
public Set<Point2D> reducePs(int parts){
		
		// divide the koordinatesystem into parts*parts pieces
		
		Point2D[][] reducedPoints= new Point2D[parts][parts];
		
		Set<Point2D> copyOfKoordinates = new HashSet<Point2D>();
		copyOfKoordinates = koordinates;
				
		Set<Point2D> returnSet = new HashSet<Point2D>();
		
		for (int i=0;i<parts;i++){
			for (int j=0; j<parts;j++){
				while(copyOfKoordinates.iterator().hasNext()){
					Point2D point = copyOfKoordinates.iterator().next();
					if(point.getX()<(copyOfKoordinates.size()/parts)*i){
						if (point.getY()<(copyOfKoordinates.size()/parts)*j){
							if (reducedPoints[i][j]!=null){
								reducedPoints[i][j]= point;
							}else{
								double x,y;
								x = (reducedPoints[i][j].getX() + point.getX())/2;
								y = (reducedPoints[i][j].getY() + point.getY())/2;
								reducedPoints[i][j].setLocation(x, y);
								
							}
						}
					}
				}
			}
		
		System.out.println (reducedPoints.toString());	
		
		
		
		
		for (int k=0;k<parts;k++){
			for (int l=0; l<parts;l++){
				if(reducedPoints[k][l]!=null){
					returnSet.add(reducedPoints[k][l]);
				}
				
			}
		}
		
		}
		return returnSet;
		
	}
}
