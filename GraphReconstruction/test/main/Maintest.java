package main;



import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.LinkedList;

import main.MetricSpace;
import main.SimpleMetricSpacePlott;




public class Maintest {
	// pfade unter umstaenden anpassen
    static String bild = "E:\\ProgrammProjekteEclipse\\GraphReconstruction\\test\\main\\mediumTest.png";
    static String bild2 = "E:\\ProgrammProjekteEclipse\\GraphReconstruction\\test\\main\\mediumTest2.png";
    static String cluster = "E:\\ProgrammProjekteEclipse\\GraphReconstruction\\test\\main\\cluster.png";
    public static void main(String[] args) throws IOException {
    	//plottertest();
    	//reconstructiontest();
    	riepsvitoristest();
	}
    private static void riepsvitoristest() throws IOException {
    	LinkedList<Color> farben= new LinkedList<Color>();
    	farben.add(Color.RED);
    	farben.add(Color.BLUE);
    	farben.add(Color.GREEN);
    	farben.add(Color.MAGENTA);
    	farben.add(Color.ORANGE);
    	MetricSpace<Point2D> space = new Simpleimagespace(cluster);
    	SimpleMetricSpacePlott<Point2D> test = new SimpleMetricSpacePlott<Point2D>("test");
    //	test.add(space, Color.BLACK);
    	
    	//-------------------------------------------------------
    	// Test rieps vietrois erstellen
    	
    	RipsVietoris<Point2D> rip = new RipsVietoris<Point2D>(space, 3.0); // teste 3, 9, 50
    	
    	
    	
    	//-------------------------------------------------------
    	
    	
    	System.out.println("Degree: " + rip.deg());
    	LinkedList<LinkedList<Point2D>> a = rip.getComponents();
    
    	for (int i = 0; i < a.size(); i++){
    		MetricSpace<Point2D> temp = new Simpleimagespace();
    		for (int j = 0; j < a.get(i).size(); j++){
    			temp.add(a.get(i).get(j));
    			
    		//	System.out.println(a.get(i).get(j).toString());
    		}
    		test.add(temp, farben.get(i % farben.size()));
			//System.out.println(temp.toString() + " " + farben.get(i % farben.size()).toString());

    	}
		test.start();
		
	}
	public static void plottertest() throws IOException{
    	//MetricSpace<Point2D> space = new ImageMetricSpace(bild,1.0); // welche werte fuer alpha
    			MetricSpace<Point2D> space = new Simpleimagespace(bild); // standard image space geht nicht
    			MetricSpace<Point2D> space2 = new Simpleimagespace(bild2);
    			SimpleMetricSpacePlott<Point2D> test = new SimpleMetricSpacePlott<Point2D>(space,"test", Color.BLUE);
    			test.add(space2, Color.BLACK);
    			test.start();
    }
    public static void reconstructiontest() throws IOException{
    	//MetricSpace<Point2D> space = new ImageMetricSpace(bild,1.0); // welche werte fuer alpha
    			MetricSpace<Point2D> space = new Simpleimagespace(bild); // standard image space geht nicht
    			double r = 1.0;
    			Reconstruction<Point2D> rec = new Reconstruction<Point2D>(space,r);
    			SimpleMetricSpacePlott<Point2D> test = new SimpleMetricSpacePlott<Point2D>("test");
    			MetricGraph<LinkedList<Point2D>> a = rec.get_graph();
    			//test.add(a, Color.BLACK);
    		//	test.start();
    			//SimpleMetricSpacePlott<Point2D> test = new SimpleMetricSpacePlott<Point2D>(space,"test", Color.BLUE);
    		//	test.add(space2, Color.BLACK);
    		//	test.start();
    }
}
