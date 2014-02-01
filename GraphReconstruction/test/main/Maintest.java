package main;



import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;

import preprocessing.ImageMetricSpace;
import main.MetricSpace;
import main.SimpleMetricSpacePlott;




public class Maintest {
	// pfade unter umstaenden anpassen
    static String bild = "E:\\ProgrammProjekteEclipse\\GraphReconstruction\\test\\main\\mediumTest.png";
    static String bild2 = "E:\\ProgrammProjekteEclipse\\GraphReconstruction\\test\\main\\mediumTest2.png";
    static String cluster = "E:\\ProgrammProjekteEclipse\\GraphReconstruction\\test\\main\\cluster.png";
    private static String cube = "images/cubeTest.png";
    static String graph1 = "test/main/graph1.png";
    private static String reconstr = "images/reconstr.png";
    public static void main(String[] args) throws IOException {
    	//plottertest();
    	reconstructiontest();
    	//riepsvitoristest();
	}
    private static void riepsvitoristest() throws IOException {
    	LinkedList<Color> farben= new LinkedList<Color>();
    	farben.add(Color.RED);
    	farben.add(Color.BLUE);
    	farben.add(Color.GREEN);
    	farben.add(Color.MAGENTA);
    	farben.add(Color.ORANGE);
    	MetricSpace<Point2D> space = new ImageMetricSpace(cube, 0, 1);
    	SimpleMetricSpacePlott<Point2D> test = new SimpleMetricSpacePlott<Point2D>("test");
    //	test.add(space, Color.BLACK);
    	
    	//-------------------------------------------------------
    	// Test rieps vietrois erstellen
    	
    	RipsVietoris<Point2D> rip = new RipsVietoris<Point2D>(space, 3.0); // teste 3, 9, 50
    	
    	
    	
    	//-------------------------------------------------------
    	
    	
    	System.out.println("Degree: " + rip.getDegree());
    	Set<Set<Point2D>> a = rip.getComponents();
    
    	int i = 0;
    	for (Set<Point2D> pointSet : a){
    		MetricSpace<Point2D> temp = new Simpleimagespace();
    		for (Point2D point : pointSet){
    			temp.add(point);
    			
    		//	System.out.println(a.get(i).get(j).toString());
    		}
    		test.add(temp, farben.get((i++) % farben.size()));
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
    			MetricSpace<Point2D> space = new Simpleimagespace(cube); // standard image space geht nicht
    			double r = 1;
    			Reconstruction<Point2D> rec = new Reconstruction<Point2D>(space,r);
    			SimpleMetricSpacePlott<Point2D> test = new SimpleMetricSpacePlott<Point2D>("test");
    			rec.reconstructMetricGraph();
    			MetricSpaceImplemented<Point2D> u = rec.getWorkspace();
    			Set<Point2D> liste1 = u.getLabelledAs(MetricSpaceImplemented.BRANCH);
    			Set<Point2D> liste2 = u.getLabelledAs(MetricSpaceImplemented.EDGE);
    			MetricSpace<Point2D> temp1 = new Simpleimagespace();
    			System.out.println("liste1 (3) " + liste1.size());
	    		MetricSpace<Point2D> temp2 = new Simpleimagespace();
	    		System.out.println("liste2 (2) " + liste2.size());
	    		MetricSpace<Point2D> temp3 = new Simpleimagespace();
    			for (Point2D point : u){
    	    		if (liste1.contains(point)){
    	    			temp1.add(point);
    	    		}else if( liste2.contains(point)){
    	    			temp2.add(point);
    	    		}else {
    	    			temp3.add(point);
    	    		}
    	    	}
        	

    		test.add(temp2, Color.RED);
    		test.add(temp1, Color.CYAN);
    		test.add(temp3, Color.BLACK);
    		test.start();
    			//SimpleMetricSpacePlott<Point2D> test = new SimpleMetricSpacePlott<Point2D>(space,"test", Color.BLUE);
    		//	test.add(space2, Color.BLACK);
    		//	test.start();
    }
}
