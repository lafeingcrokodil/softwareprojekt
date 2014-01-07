package main;

import java.util.Iterator;
import java.util.LinkedList;

 
public class RipsVietoris<E>{
	private LinkedList<LinkedList<E>> components;
	private LinkedList<E> storage;
	private double radius;
	MetricSpace<E> space;
	
	
	RipsVietoris(MetricSpace<E> sp, double radiu){
		this.space=sp;
		components = new LinkedList<LinkedList<E>>();
		storage = new LinkedList<E>();
		this.radius=radiu;
		Iterator<E> iter = space.iterator();
		while (iter.hasNext()) {
			E temp1 = iter.next();
			storage.add(temp1);
		}
		rieps();
	}
	RipsVietoris(MetricSpace<E> sp, double radiu, LinkedList<E> stor){
		this.space=sp;
		components = new LinkedList<LinkedList<E>>();
		storage = new LinkedList<E>();
		this.radius = radiu;
		for (int i =0; i< stor.size(); i++){		
				storage.add(stor.get(i));
		}
		rieps();
	}
	

	
	private void rieps(){
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
