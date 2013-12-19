package main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Reconstruction<P> {
	private MetricSpace<P> inputSpace;
	private MetricSpaceImplemented<P> workspace;
	private MetricSpaceImplemented<P> emptyspace;
	private double radius;
	private MetricGraph<P> returngraph;

	Reconstruction(MetricSpace<P> space, double r) {
		this.inputSpace = space;
		this.workspace = new MetricSpaceImplemented<P>(space);
		this.radius = r;
		MetricSpaceImplemented<P> tempspace = new MetricSpaceImplemented<P>(this.inputSpace);
		Iterator<P> iter = tempspace.iterator();
		while (iter.hasNext()) {
			P punkt = iter.next();
			tempspace.remove(punkt);
		}
		this.emptyspace = tempspace;
		try {
			this.reconstruction();
		} catch (Exception e) {
			System.err.println("Fehler bei der Rekonstrucktion \n" +e);
			System.exit(-1);
		}
		
		
		
	}

	private void reconstruction() throws Exception {
		/*
		 * Umsetzung des Algorithmus unter Verwendung der Hilfsklassen. #xx gibt
		 * Zeilennummerierung im Paper wieder
		 */
		// #1 Labeling Points as edge or branch
		Iterator<P> iter = this.workspace.iterator(); // #2
		while (iter.hasNext()) {
			P punkt = iter.next();
			MetricSpaceImplemented<P> space;

			space = this.workspace.differenceSet(
					this.workspace.pointsInRadius(punkt, 5 * this.radius / 3),
					this.workspace.pointsInRadius(punkt, this.radius));// #3
			RipsVietoris<P> r = new RipsVietoris<P>(space, this.radius * 4 / 3);// 4

			//if (r.deg(punkt) == 2) {// #5
			if (r.deg() == 2) {// #5
				/*
				 * label: Label 1 = Preliminary Branch Label 2 = Edge Label 3 =
				 * Branch
				 */
				this.workspace.labelAs(punkt, 2);// #6
			} else {// #7
				this.workspace.labelAs(punkt, 1);// #8
			}// #9

		}// #10
		Iterator<P> iter2 = this.workspace.iterator();// #11

		while (iter2.hasNext()) {
			P punkt = iter2.next();
			if (this.workspace.getLabeledAs(0).contains(punkt)) {
				this.workspace.labelInRadius(punkt, 2 * this.radius);// #11

			}
		}

		MetricSpaceImplemented<P> edgepoints;// #12,#13
		MetricSpaceImplemented<P> branchpoints;
		edgepoints = this.workspace.getLabeledAs(2);
		branchpoints = this.workspace.getLabeledAs(3);

		// #14 Reconstructing the Graph Structure
		RipsVietoris<P> kantenRips = new RipsVietoris<P>(edgepoints, this.radius * 2);// #15
		RipsVietoris<P> eckenRips = new RipsVietoris<P>(branchpoints, this.radius * 2);
		//LinkedList<LinkedList<P>> kanten = kantenRips.getComponents(); // #16

		ReconstructedSimpleGraph<LinkedList<P>> graph = this.findeKanten(kantenRips, eckenRips); // #16, #17

		// #18 Reconstructing the Metric

		this.returngraph = createGraphwithDiameter(graph);
	}

	private MetricGraph<P> createGraphwithDiameter(ReconstructedSimpleGraph<LinkedList<P>> graph) {
		
		List<LinkedList<P>> nodes = graph.getAllNodes();
		Iterator<LinkedList<P>> iter3 = nodes.iterator();

		while (iter3.hasNext()) {
			LinkedList<P> punkt = iter3.next();
			List<LinkedList<P>> nachbarn = graph.getNeighbours(punkt);
			Iterator<LinkedList<P>> iter4 = nachbarn.iterator();
			while (iter4.hasNext()) {
				LinkedList<P> punkt2 = iter4.next();
				graph.setDistance(punkt, punkt2, this.getDiameter(graph.getComponent(punkt, punkt2)));
			}
		}
		return null;
	}

	private ReconstructedSimpleGraph<LinkedList<P>> findeKanten(
			RipsVietoris<P> kantenRips, RipsVietoris<P> eckenRips)
			throws Exception {
		// INWORK - testing
		LinkedList<LinkedList<P>> kanten = kantenRips.getComponents();
		LinkedList<LinkedList<P>> ecken = eckenRips.getComponents();
		double faktor = 2.5;
		ReconstructedSimpleGraph<LinkedList<P>> result = new ReconstructedSimpleGraph<LinkedList<P>>(
				ecken.size() + 1);

		for (int k = 0; k < ecken.size(); k++) {

			LinkedList<P> ecke = ecken.get(k);
			result.setNode(ecke);
		}

		for (int k = 0; k < ecken.size(); k++) {
			for (int j = 0; j < ecken.size(); j++) {

				for (int l = 0; l < kanten.size(); l++) {
					LinkedList<P> ecke1 = ecken.get(l);
					LinkedList<P> ecke2 = ecken.get(j);
					LinkedList<P> kante = kanten.get(k);

					for (int i = 0; i < ecke1.size(); i++) { // kante+ecke
																// einfuegen
						this.emptyspace.add(ecke1.get(i));
					//	this.emptyspace.labelAs(ecke1.get(i), 2);
					}
					for (int i = 0; i < ecke2.size(); i++) {
						this.emptyspace.add(ecke2.get(i));
					//	this.emptyspace.labelAs(ecke2.get(i), 2);
					}
					for (int i = 0; i < kante.size(); i++) {
						P point = kante.get(i);
						this.emptyspace.add(point);
					}

					if (kante(ecke1, ecke2, kante, faktor)) {
						result.setKante(ecke1, ecke2, kante);
					}

					for (int i = 0; i < ecke1.size(); i++) { // kante + ecke
																// entfernen
						this.emptyspace.remove(ecke1.get(i));
					}
					for (int i = 0; i < ecke2.size(); i++) {
						this.emptyspace.remove(ecke2.get(i));
					}
					for (int i = 0; i < kante.size(); i++) {
						this.emptyspace.remove(kante.get(i));
					}
				}
			}
		}

		return result;
	}

	private boolean kante(LinkedList<P> ecke1, LinkedList<P> ecke2,
			LinkedList<P> kante, double faktor) {

		boolean node1 = false;
		boolean node2 = false;

		for (int i = 0; i < ecke1.size(); i++) {
			P point = ecke1.get(i);
			MetricSpaceImplemented<P> temppoints = this.emptyspace
					.pointsInRadius(point, faktor * this.radius);
			Iterator<P> iter = temppoints.iterator();
			while (iter.hasNext()) {
				P temppunkt = iter.next();
				if (kante.contains(temppunkt)) {
					node1 = true;
					break;
				}
				if (node1) {
					break;
				}
			}
		}

		for (int i = 0; i < ecke2.size(); i++) {
			P point = ecke2.get(i);
			MetricSpaceImplemented<P> temppoints = this.emptyspace
					.pointsInRadius(point, faktor * this.radius);
			Iterator<P> iter = temppoints.iterator();
			while (iter.hasNext()) {
				P temppunkt = iter.next();
				if (kante.contains(temppunkt)) {
					node2 = true;
					break;
				}
				if (node2) {
					break;
				}
			}
		}
		return node1 && node2;
	}

	public MetricGraph<P> get_graph() {
		return this.returngraph;
	}

	private double getDiameter(LinkedList<P> punkte) {
		// unter der annahme das die distanzfunktion den kuerzesten weg ueber die punktmenge sucht.
		for (int i=0; i < punkte.size(); i++ ){
			this.emptyspace.add(punkte.get(i));
		}
		
		double dist = 0.0;
		for (int k=0; k < punkte.size(); k++ ){
			for (int l=0; l < punkte.size(); l++ ){
				P eins = punkte.get(k);
				P zwei = punkte.get(l);
				if (dist < this.emptyspace.distance(eins, zwei)){
					dist = this.emptyspace.distance(eins, zwei);
				}
			}	
		}
		
		
		for (int z=0; z < punkte.size(); z++ ){
			this.emptyspace.remove(punkte.get(z));
		}
		return dist;
	}

}
