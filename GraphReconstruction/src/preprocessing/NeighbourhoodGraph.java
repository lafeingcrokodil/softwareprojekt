package preprocessing;

import java.util.HashSet;
import java.util.Set;

import main.MetricGraph;
import main.MetricSpace;

/**
 * A graph connecting all nodes within a certain distance of each other.
 * 
 * @author Terese Haimberger
 * @param <V> the type of nodes in this graph
 */
public class NeighbourhoodGraph<V> extends HashSet<V> implements MetricGraph<V>{

	/**
	 * Creates a neighbourhood graph based on the specified metric space.
	 * The set of nodes in the graph corresponds to the set of points in
	 * the metric space, and an edge exists between two nodes if and only
	 * if the distance between those two nodes is at most the specified radius.
	 * 
	 * @param space the metric space to be transformed into a neighbourhood graph
	 * @param radius the maximum length of an edge
	 */
	public NeighbourhoodGraph(MetricSpace<V> space, double radius) {
		// TODO implement constructor
	}

	@Override
	public double distance(V a, V b) {
		// TODO implement distance method (shortest path distance)
		return 0;
	}

	@Override
	public Set<V> getNeighbours(V node) {
		// TODO implement getNeighbours method
		return null;
	}

}
