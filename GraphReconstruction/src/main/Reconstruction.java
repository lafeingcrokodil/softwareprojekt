package main;

import java.util.Iterator;
import java.util.LinkedList;

public class Reconstruction<P>{
		private MetricSpace<P> inputSpace;
		private MetricSpaceImplemented<P> workspace;
		private double radius;
		private ReconstructedGraph<P> graph;
		
		Reconstruction(MetricSpace<P> space, double r){
			this.inputSpace = space;
			this.workspace = new MetricSpaceImplemented<P>(space);
			this.radius = r;
			this.reconstruction();
		}
		
		private void reconstruction(){
			/*
			 * Umsetzung des Algorithmus unter Verwendung der
			 * Hilfsklassen. #xx gibt Zeilennummerierung im Paper wieder
			 */
			 //#1 Labeling Points as edge or branch
			 Iterator<P> iter = this.workspace.iterator(); //#2
		        while (iter.hasNext()) {
		            P punkt = iter.next();
		            MetricSpaceImplemented<P> space;
		            
		            space = this.workspace.intersectionset(this.workspace.pointsInRadius(punkt, 5*this.radius/3),
		            										this.workspace.pointsInRadius(punkt, this.radius)    );//#3
		            RipsVietoris<P> r = new RipsVietoris<P>(space,this.radius*4/3);//4
		            
		            if (r.deg()==2) {//#5
		            	/* label: 		 
		            	 * Label 1 = Preliminary Branch
		            	 * Label 2 = Edge
		            	 * Label 3 = Branch
		            	 */
		            	this.workspace.labelAs(punkt, 2);//#6
		            }
		            else {//#7
		            	this.workspace.labelAs(punkt, 1);//#8
		            }//#9
		            

		        }//#10
		        Iterator<P> iter2 = this.workspace.iterator();//#11
		        while (iter2.hasNext()) {
		        	 P punkt = iter2.next();
		        	 if (this.workspace.isLabeledAs(punkt)==0){
				        	 this.workspace.labelInRadius(punkt, 2*this.radius);// branch point label uebergeben ?
		        		 
		        	 }
		        }
		        
		        
		        MetricSpaceImplemented<P> edgepoints;//#11,#12
		        MetricSpaceImplemented<P> branchpoints;
		        edgepoints = this.workspace.getLabeledAs(2);
		        branchpoints = this.workspace.getLabeledAs(3);
		        
		        //#14 Reconstructing the Graph Structure
		        RipsVietoris<P> edgeRips = new RipsVietoris<P>(edgepoints,this.radius*2);//#15
		        RipsVietoris<P> branchRips = new RipsVietoris<P>(branchpoints,this.radius*2);
		        LinkedList<RVPair> vertices = edgeRips.getComponents();
		        
		        
		        
		        /*
		         * ...
		         * ab hier weiter in arbeit
		         */
		}
		
		public ReconstructedGraph<P> get_graph(){
			return this.graph;
		}
		
}
