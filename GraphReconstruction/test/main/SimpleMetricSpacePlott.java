package main;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Iterator;
import java.util.LinkedList;

public class SimpleMetricSpacePlott<P>{
	// plotts metricspace. contains probably display errors
	MetricSpace<P> space;
	static int sizex=0;
	static int sizey=0;
	static double refactor=1.0;
	static double maxlength = 0.0;
	P ursprung = null;
	P referenz = null;
	static double maxdist= 0.0;
	static String name;
	double referenzx,referenzy,drehungx,drehungy;
	P drehung = null;
	static LinkedList<Tupel<Double,Double>> punkte;
	
	SimpleMetricSpacePlott(MetricSpace<P> s, String title){
		this.space = s;
		name = title;
		punkte = new LinkedList<Tupel<Double,Double>>();
		translate();
		
	}
	
    private void translate() {
    	Iterator<P> iter = space.iterator();
		while (iter.hasNext()) {
			P temp = iter.next();
			double x = 0.0;
			double y = 0.0;
			if (ursprung!=null && referenz != null && drehung != null){ //(x,y)				
				// Hier sind noch probleme, ein paar Punkte "verschwinden" hier bzw. bekommen die selben koordinaten.
				// -> drehung muss richtig eingebracht werden.
				
				if (space.distance(ursprung, temp) < space.distance(drehung, temp) && space.distance(referenz, temp) < space.distance(drehung, temp)){
					// oberhalb
					Kreiscalc k = new Kreiscalc();
					k.setKreis1(0.0, 0.0,space.distance(ursprung,temp));
					k.setKreis2(referenzx, referenzy,space.distance(referenz,temp));
					Tupel<Double,Double> schnitt = k.berechne_schnittp().getFirst();
					if ( maxdist < space.distance(ursprung, temp)){
						maxdist =space.distance(ursprung, temp);
					}
					punkte.add(schnitt);
				}
				else {
					//unterhalb
					Kreiscalc k = new Kreiscalc();
					k.setKreis1(0.0, 0.0,space.distance(ursprung,temp));
					k.setKreis2(referenzx, referenzy,space.distance(referenz,temp));
					Tupel<Double,Double> schnitt = k.berechne_schnittp().getLast();
					if ( maxdist < space.distance(ursprung, temp)){
						maxdist =space.distance(ursprung, temp);
					}
					punkte.add(schnitt);
				}
			}
			if (ursprung!=null && referenz != null && drehung == null){ //(x,y)
				drehung = temp;
				Kreiscalc k = new Kreiscalc();
				k.setKreis1(0.0, 0.0,space.distance(ursprung,temp));
				k.setKreis2(referenzx, referenzy,space.distance(referenz,temp));
				if ( maxdist < space.distance(ursprung, temp)){
					maxdist =space.distance(ursprung, temp);
				}
				Tupel<Double,Double> schnitt = k.berechne_schnittp().getFirst();
				punkte.add(schnitt);
			}
			if (ursprung!=null && referenz == null){ //(x,0)
				referenz=temp;
				referenzx = space.distance(ursprung, referenz);
				maxdist =space.distance(ursprung, referenz);
				referenzy = 0.0;
				punkte.add(new Tupel<Double,Double>(referenzx,referenzy));
			}
			if (ursprung==null){ // (0/0)
				ursprung=temp;
				punkte.add(new Tupel<Double,Double>(x,y));
			}
			
		}

		
	}

	public static void start() {
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            createAndShowGUI(); 
        }
    });
	}
    private static void createAndShowGUI() {
        JFrame f = new JFrame(name);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        double dimension = 1000.0;
        f.setBounds(0, 0, 1000 ,  1000);
        f.setFocusable(true);
        f.setResizable(false);
        Rectangle a = f.getBounds();
        refactor = (dimension-dimension*0.02)/maxdist;
        f.add(new Plotter2(a,punkte,refactor));
        f.pack();
        f.setVisible(true);
    } 
}

class Plotter2 extends JPanel {
	private int dimx = 0;
	private int dimy = 0;
	private Double refactor;
	private LinkedList<Tupel<Double, Double>> point;

	public Plotter2(Rectangle a, LinkedList<Tupel<Double, Double>> points,Double ref) {
		this.refactor = ref;
		this.dimx = new Double(a.width).intValue();
		this.dimy = new Double(a.height).intValue();
		point = points;
		setBorder(BorderFactory.createLineBorder(Color.black));

	}

	public Dimension getPreferredSize() {
		return new Dimension(dimx, dimy);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.drawLine(dimx / 2, 0, dimx / 2, dimy); // y linie
		g.drawLine(0, dimy / 2, dimx, dimy / 2); // x linie
		g.setColor(Color.BLACK);
		Iterator<Tupel<Double, Double>> iter = point.iterator();
		while (iter.hasNext()) {
			Tupel<Double, Double> p = iter.next();
			System.out.println("Zeichne Punkt: " + Integer.toString(p.getFirst().intValue())+ " " + Integer.toString(p.getSecond().intValue()));
			g.drawString("x",new Double(p.getFirst()*refactor).intValue()+dimx/2, new Double(p.getSecond()*refactor).intValue()+dimy/2);
		}
		System.out.println("--------------------------");

	}
}