package main;

import java.util.LinkedList;


 
public class RipsVietoris<E>{
	private LinkedList<LinkedList<E>> components;

	RipsVietoris(MetricSpace<E> Space, double radius){
		for( E point1: Space){
			LinkedList<E> init = new LinkedList<E>();
			init.addFirst(point1);
			for( E point2: Space){
				if( Space.distance(point1,point2) == 0){
					//do nothing
				}
				else if( Space.distance(point1,point2) <= radius){
					init.addFirst(point2);
				}
				else{
					//do nothing
				}
			}
			this.components.addFirst(init);
			Space.remove(init);
		}
	}
	public LinkedList<LinkedList<E>> getComponents(){
		return components;
	}
	public int deg(){
		return components.size();
		
	}
			        
                
}
