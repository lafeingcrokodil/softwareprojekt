package main;


 
public class RipsVietoris<E>{
	private LinkedList<RVPairs> components;

	RipsVietoris(MetricSpace<E> Space, double radius){
		for( E point1: Space){
			LinkedList<E> init = new LinkedList<E>();
			RVPair buff = new RVPair(point1,init);
			for( E point2: Space){
				if( Space.distance(point1,point2) == 0){
					//do nothing
				}
				else if( Space.distance(point1,point2) <= radius){
					buff.add_neighbour(point2);
				}
				else{
					//do nothing
				}
			}
			this.components.addFirst(buff);
		}
	}
	public LinkedList<RVPairs> getComponents(){
		return components;
	}
	public int deg(E point){
		for(RVPair current: components){
			if(current.get_representative() == point){
				return current.get_neighbours().size();
			}
		}
		return 0; // temporary solution
	}
			        
                
}
