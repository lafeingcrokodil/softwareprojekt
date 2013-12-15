package maintest;

import java.awt.geom.Point2D;
import java.io.IOException;




public class Maintest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		MetricSpace<Point2D> space = new ImageMetricSpace("E:\\ProgrammProjekteEclipse\\Plotgraph\\src\\maintest\\mediumtest.png");
		SimpleMetricSpacePlott<Point2D> test = new SimpleMetricSpacePlott<Point2D>(space,"test");
		test.start();
	}

}
