package main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Reconstruction<P> {
	//private MetricSpace<P> inputSpace;
	private MetricSpaceImplemented<P> workspace;
	//private MetricSpaceImplemented<P> emptyspace;
	private double radius;
	private MetricGraph<LinkedList<P>> returngraph;

	public Reconstruction(MetricSpace<P> space, double r) {
	//	this.inputSpace = space;
		this.workspace = new MetricSpaceImplemented<P>(space);
		this.radius = r;
		try {
			this.reconstruction();
		} catch (Exception e) {
			System.err.println("Fehler bei der Rekonstrucktion \n");
			e.printStackTrace();
			System.exit(-1);
		}
		
		
		
	}
	public MetricSpaceImplemented<P> testreturn(){
		return this.workspace;
	}
	private void reconstruction() throws Exception {
		/*
		 * Umsetzung des Algorithmus unter Verwendung der Hilfsklassen. #xx gibt
		 * Zeilennummerierung im Paper wieder
		 */
		System.out.println("#1 Labeling Points as edge or branch");
		for (int i =0; i < this.workspace.size(); i++){
			P punkt = this.workspace.get(i);
			LinkedList<P> space;
			space = this.workspace.pointsInRadius(punkt, 5*this.radius/3, this.radius);
			RipsVietoris<P> r = new RipsVietoris<P>(this.workspace.getMetric(), this.radius * 4 / 3, space);//System.out.println("# 4");
			//if (r.deg(punkt) == 2) {
		//	System.out.println("Degree: " + punkt.toString()+ " -----" + r.deg());
			if (r.deg() == 2) {//System.out.println(" #5");
				/*
				 * label: Label 1 = Preliminary Branch Label 2 = Edge Label 3 =
				 * Branch
				 */
				this.workspace.labelAs(punkt, 2); //System.out.println("#6");
			} else { //System.out.println("#7");
				this.workspace.labelAs(punkt, 1); //System.out.println("#8");
			}// System.out.println("#9");

		}// System.out.println("#10");
		LinkedList<P> temp = this.workspace.getLabeledAs(1);
	//	System.out.println("#11");
		/*while (temp.size()>0){
			temp = this.workspace.getLabeledAs(1);
			this.workspace.labelInRadius( temp.getFirst(), 2 * this.radius); 
		}*/
		for(int j = 0; j < temp.size(); j++){
			this.workspace.labelInRadius(temp.get(j), 2*this.radius);
		}

		// System.out.println("#12,#13");
		LinkedList<P> edgepoints = this.workspace.getLabeledAs(2);
		LinkedList<P> branchpoints = this.workspace.getLabeledAs(3);
		
		
		//---------------- bis hierher getestet und funktionierend -----------------------
		System.out.println("#14 Reconstructing the Graph Structure");
		System.out.println("this can take some time...");
		RipsVietoris<P> kantenRips = new RipsVietoris<P>(this.workspace.getMetric(), this.radius * 2,edgepoints);// #15
		RipsVietoris<P> eckenRips = new RipsVietoris<P>(this.workspace.getMetric(), this.radius * 2,branchpoints);
		LinkedList<LinkedList<P>> kanten = kantenRips.getComponents(); // #16
		LinkedList<LinkedList<P>> ecken = eckenRips.getComponents(); // #16

		 System.out.println("Kanten: " + kanten.size());
		 System.out.println("Ecken: " + ecken.size());
		 ReconstructedSimpleGraph<LinkedList<P>> graph = this.findeKanten(kanten, ecken); // #16, #17

		 System.out.println("#18 Reconstructing the Metric");

		this.returngraph = createGraphwithDiameter(graph);
	}

	private MetricGraph<LinkedList<P>> createGraphwithDiameter(ReconstructedSimpleGraph<LinkedList<P>> graph) {
		
		List<LinkedList<P>> nodes = graph.getAllNodes();
		Iterator<LinkedList<P>> iter3 = nodes.iterator();

		while (iter3.hasNext()) {
			LinkedList<P> punkt = iter3.next();
			List<LinkedList<P>> nachbarn = graph.getNeighbours_old(punkt);
			Iterator<LinkedList<P>> iter4 = nachbarn.iterator();
			while (iter4.hasNext()) {
				LinkedList<P> punkt2 = iter4.next();
				graph.setDistance(punkt, punkt2, this.getDiameter(graph.getComponent(punkt, punkt2)));
			}
		}
		return graph;
	}

	private ReconstructedSimpleGraph<LinkedList<P>> findeKanten(
			LinkedList<LinkedList<P>> kanten, LinkedList<LinkedList<P>> ecken)
			throws Exception {
		// INWORK - testing
		double faktor = 2.5;
		
		ReconstructedSimpleGraph<LinkedList<P>> result = new ReconstructedSimpleGraph<LinkedList<P>>(ecken.size() + 1);

		for (int k = 0; k < ecken.size(); k++) {
			LinkedList<P> ecke = ecken.get(k);
			result.setNode(ecke);
		}
		// System.out.println("Reconstruktion laeuft...finde_kanten... 0%");
		 
		for (int k = 0; k < ecken.size()-1; k++) {
			 System.out.println("Reconstruction laeuft...finde_kanten... "+ (k*100/ecken.size()) +"%");
			for (int j = k+1; j < ecken.size(); j++) {
			//	 System.out.println("Kontroll2: ");
				// System.out.println("Reconstruktion laeuft...finde_kanten... "+ ((k*100.0+j)/(ecken.size()*2)) +"%");

				for (int l = 0; l < kanten.size(); l++) {
					LinkedList<P> ecke1 = ecken.get(k);
					LinkedList<P> ecke2 = ecken.get(j);
					LinkedList<P> kante = kanten.get(l);
					boolean e1 = false;
					boolean e2 = false;
					for (int m =0; m <kante.size(); m++){
						LinkedList<P> g = this.workspace.pointsInRadius(kante.get(m), this.radius*faktor, 0.1*this.radius);
						for (int f = 0; f < g.size(); f++){
							if (ecke1.contains(g.get(f))){
								e1 = true;
							}
							if (ecke2.contains(g.get(f))){
								e2 = true;
							}
						}
					}
					if (e1 && e2){
						result.setKante(ecke1, ecke2, kante);
					}

				}
			}
		}

		return result;
	}

	public MetricGraph<LinkedList<P>> get_graph() {
		return this.returngraph;
	}

	private double getDiameter(LinkedList<P> punkte) {
		// unter der annahme das die distanzfunktion den kuerzesten weg ueber die punktmenge sucht.
		double dist = 0.0;
		for (int k=0; k < punkte.size()-1; k++ ){
			for (int l=k+1; l < punkte.size(); l++ ){
				P eins = punkte.get(k);
				P zwei = punkte.get(l);
				if (dist < this.workspace.distance(eins, zwei)){
					dist = this.workspace.distance(eins, zwei);
				}
			}	
		}
		return dist;
	}

}
