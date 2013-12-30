package preprocessing;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
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
	public Set<Point2D> parse(String filename) throws IOException {

		log.debug("Extracting GPS coordinates from trace file...");

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
		LinkedList<Double> coordX = new LinkedList<Double>();
		LinkedList<Double> coordY = new LinkedList<Double>();

		while (tokenizer.hasMoreTokens()) {

			String write2 = (String) tokenizer.nextToken();

			if (write2.contains("!")) {

				write2 = write2.replace("!", "");
				
				coordX.add(Double.parseDouble(write2));
			}
			if (write2.contains("#")) {

				write2 = write2.replace("#", "");
				
				coordY.add(Double.parseDouble(write2));
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

	
	
	public Set<Point2D> getEpsilonNet(Set<Point2D> coordinates, double epsilon) {
		
		
		ArrayList<Point2D> coordinatesCopy = new ArrayList<Point2D>();
		ArrayList<LinkedList<Point2D>> eNet = new ArrayList<LinkedList<Point2D>>();
		
		Set<Point2D> result = new HashSet<Point2D>();
		
				
		double cell = epsilon;
		
		
		// for better iteration -> copy coordinates in ArrayList
		
		log.debug("Copying coordinates into ArrayList...");
		
		int p=0;
		
		for (Point2D i : coordinates){
		
			coordinatesCopy.add(i);
			
			
		}
		
		log.debug("Finding min/max coordinates...");
		
		// max and mix -> needed for eNet borders
		// Initialize min, max with first point of array
		double minX =coordinatesCopy.get(0).getX();
		double maxX=coordinatesCopy.get(0).getX();
		double minY=coordinatesCopy.get(0).getY();
		double maxY=coordinatesCopy.get(0).getY();
		
		
		
		// find min, max
		
		for (int i=0; i<coordinatesCopy.size();i++){
			double tempX = coordinatesCopy.get(i).getX();
			double tempY = coordinatesCopy.get(i).getY();
			
			if ( tempX < minX){
				minX = tempX;
				
			}else if(tempX>maxX){
				maxX = tempX;
				
			}
			if(tempY<minY){
				minY=tempY;
			}else if(tempY>maxY){
				maxY=tempY;
			}
				
		}
				
		/* iterate through the net and select the points for each cell
		 the points of each cell will be stored in a linkedlist...
		every linkedlist will be stored in a ArrayList
		*/
		
		log.debug("Grouping points by grid cell...");
		
		for (double l=minX;l<maxX;l=l+epsilon){  // X-axis
					
			
			
			for (double k=minY;k<maxY;k=k+epsilon){ // Y-axis
				
				
				LinkedList<Point2D> temp = new LinkedList<Point2D>();
				
				for (int m=0;m<coordinatesCopy.size();m++){
					
					if (l<coordinatesCopy.get(m).getX() && 	(l+cell)>coordinatesCopy.get(m).getX()){
						
						if (k<coordinatesCopy.get(m).getY() && 	(k+cell)>coordinatesCopy.get(m).getX()){
							
							temp.add(coordinatesCopy.get(m));
						}
					}else{
						continue;
					}
				
				
			
			}
				
				if(temp.size()>0){
					eNet.add(temp);
				}
		
		  }
			
		}
		
		
		// find the average point of each cell and put him into the result (return) set
		
		log.debug("Calculating representative for each occupied grid cell...");
		log.debug("Epsilon net size: " + eNet.size());
		
		for (int i=0;i<eNet.size();i++){
			if (i % 10000 == 0)
				log.debug(i + " of " + eNet.size());
			result.add(averagePoint(eNet.get(i)));
		}
		
			
		return result;
	}

	// Method to get the average Point of a Cell in the eNet
	
	private static Point2D averagePoint(LinkedList<Point2D> test){
		  
		  double gesamtX=0;
		  double gesamtY=0;
		  double elemente=test.size();
		  
		  Point2D point = new Point2D.Double();
		  
		  for (int i=0; i<test.size();i++){
			  double tempX = test.get(i).getX();
			  double tempY = test.get(i).getY();
			  
			  gesamtX = gesamtX+tempX;
			  gesamtY = gesamtY+tempY;
		  }
		  
		  double pointX= gesamtX/elemente;
		  double pointY= gesamtY/elemente;
		  
		  point.setLocation(pointX, pointY);
		  return (point);
	  }
	
	
	
	
}