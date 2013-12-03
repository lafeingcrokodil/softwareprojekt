package main;

import java.util.Iterator;
import java.util.List;

public class ReconstructedSimpleGraph<V> implements Graph<V> {
	{

	}

	@Override
	public Iterator<V> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<V> getNeighbours(V node) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setNode(V node){
		// TODO
	}
	
	public void setKante(V nodeA, V nodeB, RVPair<V> punkte){
		
	}
	public void setDistance(V nodeA, V nodeB, double distance){
		
	}
	public RVPair<V> getComponent(V nodeA, V nodeB){
		//Todo
		return null;
	}
	@Override
	public double getDistance(V node1, V node2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<V> getAllNodes() {
		// TODO Auto-generated method stub
		return null;
	}
}