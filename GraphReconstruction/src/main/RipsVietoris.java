package main;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

 
public class RipsVietoris<E>{
	private LinkedList<LinkedList<E>> components;

	RipsVietoris(MetricSpace<E> space, double radius){
		components = new LinkedList<LinkedList<E>>();
		LinkedList<E> storage = new LinkedList<E>();
		Iterator<E> iter = space.iterator();
		while (iter.hasNext()) {
			E temp1 = iter.next();
			storage.add(temp1);
		}
		
		while (!storage.isEmpty()){
			E punkt = storage.pollFirst();
			LinkedList<E> sammler = new LinkedList<E>();
			sammler.add(punkt);
			int sizeoben = sammler.size();
			for (int j = 0; j < sizeoben ; j++){
				E x = sammler.get(j);
				int size = storage.size();
				for (int i = 0; i < size ; i++){
					if (space.distance(x, storage.get(i)) <= radius){
						E y = storage.get(i);
						sammler.add(y);
						storage.remove(i);
						i--;
						sizeoben++;
						size--;
					}
				}
			}
			components.add(sammler);
		}
		
	}
	public LinkedList<LinkedList<E>> getComponents(){
		return components;
	}
	public int deg(){
		return components.size();
		
	}
			        
                
}
