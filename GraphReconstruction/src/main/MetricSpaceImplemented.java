package main;
import java.util.HashSet;

	/*
	 * @param <P> the type of points in the metric space
	 */

public class MetricSpaceImplemented<P> extends HashSet<P> implements MetricSpace<P>{
	
	public MetricSpaceImplemented<P> pointsInRadius(P p, double r){
		/* 
		 * returns MetricSpaceImplemented composed of points within radius r
		 * around point p.
		 */
		MetricSpaceImplemented<P> inRadiusSpace = new MetricSpaceImplemented<P>();
		for (P point : this) { 
			if(distance(p,point) <= r){
				inRadiusSpace.add(point);
			}
		}
		return inRadiusSpace;
	}
	
	
	public void labelAs(P p, int label){
		/*
		 * TODO: how to store associated label to a point?
		 */
	}
	
	public void labelInRadius(P p, double r){
		/*
		 * Label 1 = Preliminary Branch
		 * Label 2 = Edge
		 * Label 3 = Branch
		 */
		for(P point : pointsInRadius(p, r)){
			labelAs(point, 3);
		}
		
	}
	
	public MetricSpaceImplemented<P> getLabeledAs(int label){
		/*
		 * TODO: find all points labeled with "label"
		 */
		for (P point : this) { 

			}
	}
	
	
}
