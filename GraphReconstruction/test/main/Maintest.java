package main;



import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
import main.MetricSpace;
import main.SimpleMetricSpacePlott;




public class Maintest {
	// pfade unter umstaenden anpassen
    static String bild = "E:\\ProgrammProjekteEclipse\\GraphReconstruction\\test\\main\\mediumTest.png";
    static String bild2 = "E:\\ProgrammProjekteEclipse\\GraphReconstruction\\test\\main\\mediumTest2.png";
	
    public static void main(String[] args) throws IOException {
		//MetricSpace<Point2D> space = new ImageMetricSpace(bild,1.0); // welche werte fuer alpha
		MetricSpace<Point2D> space = new Simpleimagespace(bild); // standard image space geht nicht
		MetricSpace<Point2D> space2 = new Simpleimagespace(bild2);
		SimpleMetricSpacePlott<Point2D> test = new SimpleMetricSpacePlott<Point2D>(space,"test", Color.BLUE);
		test.add(space2, Color.BLACK);
		test.start();
		
	}
}
