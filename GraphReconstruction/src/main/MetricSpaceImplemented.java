package main;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
	/*
	 * @param <P> the type of points in the metric space
	 */
public class MetricSpaceImplemented<P> extends LinkedList<P> implements MetricSpace<P>{
	/*
	 * MetricSpaceImplemented instances for grouping labels
	 */
	LinkedList<P> edge;
	LinkedList<P> prelBranch;
	LinkedList<P> branch;
	/*
	 * Variable for input space given in constructor
	 */
	MetricSpace<P> inputSet;
	
	
	/*
	 * Constructor
	 */
	public MetricSpaceImplemented(MetricSpace<P> space) {
		this.inputSet = space;
		edge = new LinkedList<P>();
		prelBranch= new LinkedList<P>();
		branch= new LinkedList<P>();
		Iterator<P> iter = space.iterator();
		while (iter.hasNext()) {
			P temp = iter.next();
			this.add(temp);
		}
	}
	
	/*
	 * Methods
	 */
	public LinkedList<P> pointsInRadius(P zentrum, double radius_gr,double radius_kl){
		LinkedList<P> inRadiusSpace = new LinkedList<P>();
		for (int i = 0; i < this.size(); i++){
			if (this.distance(zentrum, this.get(i)) <= radius_gr && this.distance(zentrum, this.get(i)) >= radius_kl){
				inRadiusSpace.add(this.get(i));
			}
		}
		return inRadiusSpace;
	}
	
	public MetricSpaceImplemented<P> differenceSet(MetricSpaceImplemented<P> space1, MetricSpaceImplemented<P> space2){
		//TESTED: ok!
		for(P point : space2){
			space1.remove(point);
		}
		return space1;
	}
	MetricSpace<P> getMetric(){
		return this.inputSet;
	}
	
	public void labelAs(P p, int label){
		/*
		 * Label 1 = Preliminary Branch
		 * Label 2 = Edge
		 * Label 3 = Branch: relabeling: point must be removed from preliminary label-list
		 */
		if(prelBranch.contains(p)){
			LinkedList<P> temp = new LinkedList<P>();
			for (int i = 0; i < prelBranch.size();i++){
				if (p != prelBranch.get(i)){
					temp.add(prelBranch.get(i));
				}
			}
			prelBranch = temp;
		}
		if(edge.contains(p)){
			LinkedList<P> temp = new LinkedList<P>();
			for (int i = 0; i < edge.size();i++){
				if (p != edge.get(i)){
					temp.add(edge.get(i));
				}
			}
			edge = temp;
		}
		if(branch.contains(p)){
			LinkedList<P> temp = new LinkedList<P>();
			for (int i = 0; i < branch.size();i++){
				if (p != branch.get(i)){
					temp.add(branch.get(i));
				}
			}
			branch = temp;
		}
		
		if(label == 1){ prelBranch.add(p);
		System.out.println("liste2 (1) " + prelBranch.size());
}
		if(label == 2){ edge.add(p);
		System.out.println("liste2 (2) " + edge.size());

		}
		if(label == 3){
			branch.add(p);
    		System.out.println("liste2 (3) " + branch.size());
		}
	}
	
	public void labelInRadius(P p, double r){
		//TESTED: ok!
		/*
		 * Does eventual relabeling of edge points and preliminary branch points
		 * as branch points. Calls pointsInRadius()- and labelAs()-method with label
		 * 3 (= branch points)
		 */
		for (int i = 0; i < this.size(); i++){
			if (this.distance(p, this.get(i)) <= r){
				this.labelAs(p, 3);
			}
		}
	}
	
	public LinkedList<P> getLabeledAs (int label){
		//TESTED: ok!
		//TODO: Add exception?
		if(label == 1) return prelBranch;
		if(label == 2) return edge;
		else return branch;
	}
	
	@Override
	public double distance(P a, P b){
		/*
		 * uses the distance-method of the MetricSpace instance that is given in the constructor
		 */
		return inputSet.distance(a, b);
	}
}
