package main;



public class RVPair <E>{
    private <E> representative;
    private LinkedList<E> neighbours;

    public RVPair(<E> representative, LinkedList<E> neighbours){
        this.rep   = representative;
        this.neighbours = neighbours;
    }

    public <E> get_representative() { return representative; }
    public LinkedList<E> get_neighbours() { return neighbours; }
    public void add_neighbour(<E> point) { neighbours.addFirst(point); }
}
