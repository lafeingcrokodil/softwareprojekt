package main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Reconstruction<P> {
	private MetricSpace<P> inputSpace;
	private MetricSpaceImplemented<P> workspace;
	private double radius;
	private Graph<P> returngraph;

	Reconstruction(MetricSpace<P> space, double r) {
		this.inputSpace = space;
		this.workspace = new MetricSpaceImplemented<P>(space);
		this.radius = r;
		this.reconstruction();
	}

	private void reconstruction() {
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

			if (r.deg(punkt) == 2) {// #5
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
		RipsVietoris<P> kantenRips = new RipsVietoris<P>(edgepoints,
				this.radius * 2);// #15
		RipsVietoris<P> eckenRips = new RipsVietoris<P>(branchpoints,
				this.radius * 2);
		LinkedList<RVPair<P>> kanten = kantenRips.getComponents(); // #16

		ReconstructedSimpleGraph<P> graph = this.findeKanten(kantenRips,
				eckenRips); // #17

		// #18 Reconstructing the Metric

		this.returngraph = createGraphwithDiameter(graph);
	}

	private Graph<P> createGraphwithDiameter(ReconstructedSimpleGraph<P> graph) {
		// TODO Auto-generated method stub
		List<P> nodes = graph.getAllNodes();
		Iterator<P> iter3 = nodes.iterator();

		while (iter3.hasNext()) {
			P punkt = iter3.next();
			List<P> nachbarn = graph.getNeighbours(punkt);
			Iterator<P> iter4 = nachbarn.iterator();
			while (iter4.hasNext()) {
				P punkt2 = iter4.next();
				graph.setDistance(punkt, punkt2, this.getDiameter(graph.getComponent(punkt, punkt2)));
			}
		}
		return null;
	}

	private ReconstructedSimpleGraph<P> findeKanten(RipsVietoris<P> kantenRips,
			RipsVietoris<P> eckenRips) {
		// TODO Auto-generated method stub
		return null;
	}

	public Graph<P> get_graph() {
		return this.returngraph;
	}

	private double getDiameter(RVPair<P> punkte) {
		// @ TODO
		return 0.0;
	}

}
