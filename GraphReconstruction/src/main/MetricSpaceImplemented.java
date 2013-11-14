package main;

import java.util.HashSet;
	/*
	 * @param <P> the type of points in the metric space
	 */
public class MetricSpaceImplemented<P> extends HashSet<E> implements MetricSpace<P>{
	
	public MetricSpace<P> pointsInRadius(P p, double r){
		//MetricSpace<P> space;
		//return space;
		/* 
		 * TODO: returns MetricSpace composed of points within radius r
		 */
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
	
	public MetricSpace<P> getLabeledAs(int label){
		/*
		 * TODO: find all points labeled with "label"
		 */
	}
	
	
}
