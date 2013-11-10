package main;

public class MetricSpaceImplemented implements MetricSpace{
	public MetricSpace<P> pointsInRadius(P p, double r){
		
		/* 
		 * TODO: returns MetricSpace composed of points within radius r
		 */
	}
	
	public void labelAs(P p, int label){
		/*
		 * TODO: how to store associated label to a point?
		 */
	}
	
	public void labelInRadius(P p, double r, MetricSpace<P> space){
		/*
		 * TODO: MetricSpace as input parameter?
		 */
	}
	
	public MetricSpace<P> getLabeledAs(int label){
		/*
		 * TODO: find all points labeled with "label"
		 */
	}
	
	
}
