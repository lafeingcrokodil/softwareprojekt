package main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/**
 * Simple Graph, does not Support multiple edgdes between two Nodes
 *  
 * @author Moritz
 *
 * @param <V> Should contain the component that describes an edge or branch. probably RVPair<> 
 */
public class ReconstructedSimpleGraph<V> implements Graph<V> {
	private HashMap <V,LinkedList<V>> knoten; //<Knoten, Adjazenzliste>
	private HashMap <V,Integer> knotentoint; //<Knoten, label>
	private Integer knotenCount = 0; //aktuelle anzahl der Knoten
	private LinkedList<LinkedList<Tupel<V, Double>>> adjmatrix; // adjazenzmatrix Tupel (kante, double) kante existiert mit laenge double, -2 wenn keine laenge gesetzt, -1 wenn keine kante existiert.
	 
	/**
	 * Constructor, initializing variables
	 */
	ReconstructedSimpleGraph(Integer maxknoten){
		this.knoten = new HashMap<V,LinkedList<V>>();
		this.knotentoint = new HashMap<V,Integer>();
		this.adjmatrix = new LinkedList<LinkedList<Tupel<V, Double>>>();
		for(int i = 0;i<maxknoten;i++){
			LinkedList<Tupel<V, Double>> temp = new LinkedList<Tupel<V, Double>>();
			this.adjmatrix.add(temp);
			for(int j = 0;j<maxknoten;j++){
				Tupel<V, Double> temp2 = new Tupel<V, Double>(null,-1.0);
				this.adjmatrix.get(i).add(temp2);		
			}
		}
	}
	
	public Iterator<V> iterator() {
		return knoten.keySet().iterator();
	}

	public List<V> getNeighbours(V node) {
		
		if (this.knoten.containsKey(node)){
			int zahl = this.knotentoint.get(node);
			LinkedList<Tupel<V, Double>> temp = this.adjmatrix.get(zahl);
			LinkedList<Integer> tempresult = new LinkedList<Integer>();
			LinkedList<V> result = new LinkedList<V>();
			
			for(int j = 0; j<temp.size();j++){
				Tupel<V, Double> temp2 = temp.get(j);
				if (temp2.getSecond() > -2.0){
					tempresult.add(j);
				}
			}
			Iterator<V> iter = this.knoten.keySet().iterator();
			while (iter.hasNext()) {
				V knoten = iter.next();
				if (tempresult.contains(this.knoten.get(knoten))){
					result.add(knoten);
				}
			}
			
			return result; 
		}
		else{
			return null;
		}
	}
	
	/**
	 * sets a Node
	 * @param node
	 */
	public void setNode(V node) throws Exception{
		if (this.knotenCount >= this.adjmatrix.size()){
			throw new Exception();
		}
		LinkedList<V> temp = new LinkedList<V>();
		this.knoten.put(node, temp);
		this.knotentoint.put(node, this.knotenCount);
		this.knotenCount++;
		
	}
	/**
	 * Sets an edge between nodeA and nodeP. punkte is the component that describes the edge.
	 * throws an exception if nodeA or nodeB dont exist.
	 * @param nodeA
	 * @param nodeB
	 * @param punkte
	 */
	public void setKante(V nodeA, V nodeB, V punkte) throws Exception{
		if(!(this.knoten.containsKey(nodeA) && this.knoten.containsKey(nodeB))){
			throw new Exception();
		}
		this.knoten.get(nodeA).add(punkte);
		this.knoten.get(nodeB).add(punkte);
		this.adjmatrix.get(this.knotentoint.get(nodeA)).get(this.knotentoint.get(nodeB)).setFirst(punkte);
		this.adjmatrix.get(this.knotentoint.get(nodeB)).get(this.knotentoint.get(nodeA)).setFirst(punkte);
		this.adjmatrix.get(this.knotentoint.get(nodeA)).get(this.knotentoint.get(nodeB)).setSecond(-2.0);
		this.adjmatrix.get(this.knotentoint.get(nodeB)).get(this.knotentoint.get(nodeA)).setSecond(-2.0);
	}
	/**
	 * Sets the distance of an edge between nodeA and nodeB, but only if edge exits
	 * @param nodeA
	 * @param nodeB
	 * @param distance > 0
	 */
	public void setDistance(V nodeA, V nodeB, double distance){
		if ((this.adjmatrix.get(this.knotentoint.get(nodeA)).get(this.knotentoint.get(nodeB)).getSecond() != -1.0) && distance >0){
			this.adjmatrix.get(this.knotentoint.get(nodeA)).get(this.knotentoint.get(nodeB)).setSecond(distance);
			this.adjmatrix.get(this.knotentoint.get(nodeB)).get(this.knotentoint.get(nodeA)).setSecond(distance);
		}
	}
	/**
	 * 
	 * @param nodeA
	 * @param nodeB
	 * @return RiepsVietoris Component that describes the edge between nodeA and nodeB
	 */
	public V getComponent(V nodeA, V nodeB){
		return this.adjmatrix.get(this.knotentoint.get(nodeA)).get(this.knotentoint.get(nodeB)).getFirst();
	}

	public double getDistance(V nodeA, V nodeB) {
		// -2 if edge exists but no distance was entered
		return this.adjmatrix.get(this.knotentoint.get(nodeA)).get(this.knotentoint.get(nodeB)).getSecond();
	}

	public List<V> getAllNodes() {
		LinkedList<V> temp1 = new LinkedList<V>();
		temp1.addAll(this.knoten.keySet());
		return temp1;
	}
}