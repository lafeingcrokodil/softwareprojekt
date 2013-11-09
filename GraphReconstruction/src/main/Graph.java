package main;

import java.util.Set;

/**
 * A graph. Supports iteration over the set of nodes.
 *
 * @param <V> the type of nodes in this graph
 */
public interface Graph<V> extends Iterable<V> {

	/**
	 * Returns all nodes that are directly connected to the specified node by an edge.
	 * 
	 * @param node the node in question
	 * @return all neighbours of the node in question
	 */
	Set<V> getNeighbours(V node);

}
