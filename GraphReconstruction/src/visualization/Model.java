package visualization;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Model {

	// to store data string
	ArrayList<String> dataset = new ArrayList<String>();
	Point point = new Point();
	// to manupilate data
	HashMap<Integer, Point> points = new HashMap<Integer, Point>();

	public Model() {
		dataset.add("123.2 , 1.3");
		dataset.add("223.1 ,23.3");
		dataset.add("323.4 , 98.2");
		dataset.add("423.9 , 2.0");
		dataset.add("523.2 , 21.3");
		dataset.add("623.8 , 98.3");
		dataset.add("723.1 , 89.3");
	}

	// holding point
	class Point {
		float xValue;
		float yValue;
	}

	public void addPoint(String point) {
		dataset.add(point);
	}

	public void removePoint(int index) {
		points.remove(index);
	}

	public void editPoint(int index) {
	}

	public HashMap<Integer, Point> loadDataset(String filepath)
			throws FileNotFoundException {
		int index = 0;
		Scanner s = new Scanner(new File(filepath));
		while (s.hasNext()) {
			points.put(index, createPoint(s.nextLine()));
			index++;
			dataset.add(printPoint());
		}
		s.close();
		return points;
	}

	public Point createPoint(String line) {
		String[] parts = line.split(",");
		String x = parts[0];
		String y = parts[1];
		// Point curPoint = new Point();
		// curPoint.xValue = Float.parseFloat(x);
		// curPoint.yValue = Float.parseFloat(y);
		point.xValue = Float.parseFloat(x);
		point.yValue = Float.parseFloat(y);
		return point;
	}

	public Point getPointFromInput(String x, String y) {
		point.xValue = Float.parseFloat(x);
		point.yValue = Float.parseFloat(y);
		return point;
	}

	public String printPoint() {
		String pointForPrint;
		pointForPrint = (Float.toString(point.xValue) + " , " + Float
				.toString(point.yValue));
		return pointForPrint;
	}

	public void savaDataset() {

	}

	public void setDataset() {

	}

	public ArrayList<String> getDataset() {
		return dataset;
	}

	public HashMap<Integer, Point> getPoints() {
		return points;
	}

	public float getCurrentPointX() {
		return point.xValue;
	}

	public float getCurrentPointY() {
		return point.yValue;
	}
}
