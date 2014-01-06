package main;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * Benutzung:
 * 
 * konstrucktor aufrufen mit oder ohne metric Space, farbe fuer den MetricSpace hinzufuegbar, standardmaeﬂig Schwarz.
 * String im konstruktor ist fuer den Titel des fensters
 * weitere MetricSpaces hinzufuegbar mit add(MetricSpace, farbe); Achtung, metric space muss compatibel sein, d.h. gleiche distanzfunktion
 * 
 * anzeige starten mit start();
 * 
 * @author Moritz Walter
 * 
 * @param <P>
 */

public class SimpleMetricSpacePlott<P> {
	// plotts metricspace. contains probably display errors
	MetricSpace<P> space;
	static int sizex = 0;
	static int sizey = 0;
	static double refactor = 1.0;
	static double maxlength = 0.0;
	P ursprung = null;
	P referenz = null;
	int counter = 0;
	LinkedList<Tupel<P,Color>> storage;
	static double maxdist = 0.0;
	static String name;
	double referenzx, referenzy, drehungx, drehungy;
	P drehung = null;
	static LinkedList<Tripel<Double, Double, Color>> punkte;

	SimpleMetricSpacePlott(MetricSpace<P> s, String title) {
		this.space = s;
		name = title;
		punkte = new LinkedList<Tripel<Double, Double, Color>>();
		storage = new LinkedList<Tupel<P,Color>>();
		Iterator<P> iter = s.iterator();
		while (iter.hasNext()) {
			P temp1 = iter.next();
			counter++;
			System.out.println("punkte: " + counter);
			storage.add(new Tupel<P,Color>(temp1,Color.BLACK));
		}
		//translate(Color.BLACK);
	}
	SimpleMetricSpacePlott(MetricSpace<P> s, String title, Color farbe) {
		this.space = s;
		storage = new LinkedList<Tupel<P,Color>>();
		name = title;
		punkte = new LinkedList<Tripel<Double, Double, Color>>();
		Iterator<P> iter = s.iterator();
		while (iter.hasNext()) {
			P temp1 = iter.next();
			counter++;
			System.out.println("punkte: " + counter);
			storage.add(new Tupel<P,Color>(temp1,farbe));
		}
		//translate(farbe);
	}
	SimpleMetricSpacePlott(String title) {
		this.space =null;
		storage = new LinkedList<Tupel<P,Color>>();
		name = title;
		punkte = new LinkedList<Tripel<Double, Double, Color>>();
	}
	/*
	public void add(MetricSpace<P> s, Color farbe) {
		if (this.space==null){
			this.space = s;
		}
		else {
		this.space = s;
		//this.space.add(ursprung);
		//this.space.add(drehung);
		//this.space.add(referenz);
		}
		this.translate(farbe);
	}
*/
	public void add(MetricSpace<P> s, Color farbe) {
		if (this.space==null){
			this.space = s;
			Iterator<P> iter = s.iterator();
			while (iter.hasNext()) {
				P temp1 = iter.next();
				storage.add(new Tupel<P,Color>(temp1,farbe));
			//	counter++;
				//System.out.println(temp1.toString() + " punkte: " + counter);
			}
		}
		else {
			Iterator<P> iter = s.iterator();
			while (iter.hasNext()) {
				P temp1 = iter.next();
			if(!this.space.contains(temp1)){
				//	counter++;
					//System.out.println(temp1.toString() + " punkte: " + counter);
					this.storage.add(new Tupel<P,Color>(temp1,farbe));
				}else{
				//	counter++;
				//	System.out.println("Rejected, punkte: " + counter);
				}
			}
		}
		//this.translate(farbe);
	}
	private void translate(){//Color farbe) {
		
		while (!storage.isEmpty()){
			Tupel<P, Color> temptup =  storage.removeFirst();
			P temp = temptup.getFirst();
			Color farbe = temptup.getSecond();
			// in liste um, wenn cord gleich weiter bis ende, dann zur¸ck, liste abarbeiten.
			if (ursprung != null && referenz != null && drehung != null) { // (x,y)
				// Hier sind noch probleme, ein paar Punkte "verschwinden" hier
				// bzw. bekommen die selben koordinaten.
				// -> drehung muss richtig eingebracht werden.
				/*
				 * punktproblem korigiert, geht aber besser.
				 */
				Kreiscalc k = new Kreiscalc();
				k.setKreis1(0.0, 0.0, space.distance(ursprung, temp));
				k.setKreis2(referenzx, referenzy, space.distance(referenz, temp));
				Tupel<Double, Double> schnitt = k.berechne_schnittp().getFirst();
				Tupel<Double, Double> schnitt2 = k.berechne_schnittp().getLast();
				boolean eins=false;
				boolean zwei=false;
				Iterator<Tripel<Double, Double, Color>> iter5 = punkte.iterator();
				while (iter5.hasNext()) {
					Tripel<Double, Double, Color> z = iter5.next();
					int a = schnitt.getFirst().intValue();
					int c = schnitt2.getFirst().intValue();
					int b = schnitt.getSecond().intValue();
					int d = schnitt2.getSecond().intValue();
					int e = z.getFirst().intValue();
					int f = z.getSecond().intValue();
					if(a==e && b == f){
						eins = true;
						break;
					}
					if(c==e && d ==f){
						zwei = true;
						break;
					}
				}
				if (eins) {
					if (maxdist < space.distance(ursprung, temp)) {
						maxdist = space.distance(ursprung, temp);
					}
					punkte.add(new Tripel<Double, Double, Color> (schnitt2, farbe));
				} else if(zwei){
					if (maxdist < space.distance(ursprung, temp)) {
						maxdist = space.distance(ursprung, temp);
					}
					punkte.add(new Tripel<Double, Double, Color> (schnitt,farbe));
				}
				else if (space.distance(ursprung, temp) < space.distance(drehung, temp)
						&& space.distance(referenz, temp) < space.distance(
								drehung, temp)) {
					// oberhalb
					schnitt = k.berechne_schnittp().getFirst();
					if (maxdist < space.distance(ursprung, temp)) {
						maxdist = space.distance(ursprung, temp);
					}
					punkte.add(new Tripel<Double, Double, Color> (schnitt,farbe));
				} else {
					// unterhalb
					schnitt = k.berechne_schnittp()	.getLast();
					if (maxdist < space.distance(ursprung, temp)) {
						maxdist = space.distance(ursprung, temp);
					}
					punkte.add(new Tripel<Double, Double, Color> (schnitt,farbe));
				}
				
				/*
				if (space.distance(ursprung, temp) < space.distance(drehung,
						temp)
						&& space.distance(referenz, temp) < space.distance(
								drehung, temp)) {
					// oberhalb
					Kreiscalc k = new Kreiscalc();
					k.setKreis1(0.0, 0.0, space.distance(ursprung, temp));
					k.setKreis2(referenzx, referenzy,
							space.distance(referenz, temp));
					schnitt = k.berechne_schnittp()
							.getFirst();
					if (maxdist < space.distance(ursprung, temp)) {
						maxdist = space.distance(ursprung, temp);
					}
					punkte.add(schnitt);
				} else {
					// unterhalb
					Kreiscalc k = new Kreiscalc();
					k.setKreis1(0.0, 0.0, space.distance(ursprung, temp));
					k.setKreis2(referenzx, referenzy,
							space.distance(referenz, temp));
					schnitt = k.berechne_schnittp()
							.getLast();
					if (maxdist < space.distance(ursprung, temp)) {
						maxdist = space.distance(ursprung, temp);
					}
					punkte.add(schnitt);
				}*/
			}
			if (ursprung != null && referenz != null && drehung == null) { // (x,y)
				
				Kreiscalc k = new Kreiscalc();
				k.setKreis1(0.0, 0.0, space.distance(ursprung, temp));
				k.setKreis2(referenzx, referenzy, space.distance(referenz, temp));
				Tupel<Double, Double> schnitt = k.berechne_schnittp().getFirst();
				Tupel<Double, Double> schnitt2 = k.berechne_schnittp().getLast();
				int a = schnitt.getFirst().intValue();
				int b = schnitt2.getFirst().intValue();
				int c = schnitt.getSecond().intValue();
				int d = schnitt2.getSecond().intValue();
			//	System.out.println(a);
			//	System.out.println(b);
			//	System.out.println(c);
			//	System.out.println(d);

				
				
				if (a==b && c== d){
					Tripel<Double, Double, Color> z = punkte.remove(1);
				//	System.out.println("tah");

					storage.add(new Tupel<P,Color> (referenz,z.getThird()));
					referenz = temp;
					referenzx = space.distance(ursprung, referenz);
					maxdist = space.distance(ursprung, referenz);
					punkte.add(new Tripel<Double, Double, Color>(referenzx, referenzy,farbe));
				}
				else {
					if (maxdist < space.distance(ursprung, temp)) {
						maxdist = space.distance(ursprung, temp);
					}
					drehung = temp;
					punkte.add(new Tripel<Double, Double, Color> (schnitt,farbe));
				//	System.out.println(schnitt.print());
				//	System.out.println(schnitt2.print());

				}
				
			}
			if (ursprung != null && referenz == null) { // (x,0)
				referenz = temp;
				referenzx = space.distance(ursprung, referenz);
				maxdist = space.distance(ursprung, referenz);
				referenzy = 0.0;
				punkte.add(new Tripel<Double, Double, Color> (referenzx, referenzy,farbe));
			}
			if (ursprung == null) { // (0/0)
				ursprung = temp;
				punkte.add(new Tripel<Double, Double, Color> (0.0, 0.0,farbe));
			}

		}
		
	}
	public void start(){
		translate();
		start2();
	}
	public static void start2() {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		double dimension = 500.0;
		refactor = (dimension - dimension * 0.02) / maxdist;
		JFrame f = new JFrame(name + " ref: " + Double.toString(refactor));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		f.setBounds(0, 0, 1000, 1000);
		f.setFocusable(true);
		f.setResizable(false);
		Rectangle a = f.getBounds();
		
		if (name.contains("vorhertest")) {
			f.add(new Plotter2(a, punkte, 2.0));
		} else {
			f.add(new Plotter2(a, punkte, refactor));
		}
		f.pack();
		f.setVisible(true);
	}
}

class Plotter2 extends JPanel {
	private int dimx = 0;
	private int dimy = 0;
	private Double refactor;
	private LinkedList<Tripel<Double, Double, Color>> point;

	public Plotter2(Rectangle a, LinkedList<Tripel<Double, Double, Color>> points,
			Double ref) {
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
		Iterator<Tripel<Double, Double, Color>> iter = point.iterator();
		while (iter.hasNext()) {
			Tripel<Double, Double, Color> p = iter.next();
		//	System.out.println("Zeichne Punkt: "
	//				+ Integer.toString(p.getFirst().intValue()) + " "
		//			+ Integer.toString(p.getSecond().intValue()));
			g.setColor(p.getThird());
			if (p.getThird()== Color.CYAN){
				g.fillOval(new Double(p.getFirst() * refactor).intValue()
					+ dimx / 2-5, new Double(p.getSecond() * refactor).intValue()
					+ dimy / 2-5, 10, 10);
				
			}else {
			g.drawString("x", new Double(p.getFirst() * refactor).intValue()
					+ dimx / 2, new Double(p.getSecond() * refactor).intValue()
					+ dimy / 2);
			}
		}
	//	System.out.println("--------------------------");

	}
}