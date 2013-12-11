package main;
import java.util.HashSet;

	/*
	 * @param <P> the type of points in the metric space
	 */

public class MetricSpaceImplemented<P> extends HashSet<P> implements MetricSpace<P>{
	/*
	 * MetricSpaceImplemented instances for grouping labels
	 */

	MetricSpaceImplemented<P> edge;
	MetricSpaceImplemented<P> prelBranch;
	MetricSpaceImplemented<P> branch;
	/*
	 * Constructors
	 */
	public MetricSpaceImplemented(MetricSpace<P> space) {
		//TESTED: ok!
		/*
		 * receives space from preprocessing and turns it into a MetricSpace
		 * TODO: distance method?
		 */
		this.addAll(space);
		edge = new MetricSpaceImplemented<P>("noSpace");
		prelBranch  = new MetricSpaceImplemented<P>("noSpace");
		branch = new MetricSpaceImplemented<P>("noSpace");

	}
	public MetricSpaceImplemented() {
		edge = new MetricSpaceImplemented<P>("noSpace");
		prelBranch  = new MetricSpaceImplemented<P>("noSpace");
		branch = new MetricSpaceImplemented<P>("noSpace");
	}
	/*
	 * Constructor that doesn't invoke the MetricSpaceImplemented-Constructor
	 */
	public MetricSpaceImplemented(String noOtherSpace){

	}
	/*
	 * Methods
	 */
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
	
	/*
	 * returns all points that are contained in space1 but not in space2 (difference quantity)
	 */
	public MetricSpaceImplemented<P> differenceSet(MetricSpaceImplemented<P> space1, MetricSpaceImplemented<P> space2){
		//TESTED: ok!
		for(P point : space2){
			space1.remove(point);
		}
		return space1;
	}
	
	
	public void labelAs(P p, int label){
		//TESTED: ok!
		/*
		 * Label 1 = Preliminary Branch
		 * Label 2 = Edge
		 * Label 3 = Branch: relabeling: point must be removed from preliminary label-list
		 * TODO: might be implemented more efficiently
		 */
		/*edge = new MetricSpaceImplemented<P>();
		prelBranch  = new MetricSpaceImplemented<P>();
		branch = new MetricSpaceImplemented<P>();*/
		if(label == 1) prelBranch.add(p);
		if(label == 2) edge.add(p);
		if(label == 3){
			branch.add(p);
			if(prelBranch.contains(p)) prelBranch.remove(p);
			else edge.remove(p);
		}
	}
	
	public void labelInRadius(P p, double r){
		/*
		 * Does eventual relabeling of edge points and preliminary branch points
		 * as branch points. Calls pointsInRadius()- and labelAs()-method with label
		 * 3 (= branch points)
		 */
		for(P point : pointsInRadius(p, r)){
			labelAs(point, 3);
		}
	}
	
	public MetricSpaceImplemented<P> getLabeledAs (int label){
		//TESTED: ok!
		//TODO: Add exception?
		if(label == 1) return prelBranch;
		if(label == 2) return edge;
		else return branch;
	}
	
	@Override
	public double distance(P a, P b){
		return (Double) null;
	}
}
