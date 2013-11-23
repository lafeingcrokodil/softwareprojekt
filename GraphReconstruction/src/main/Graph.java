package main;
import java.util.List;

/* 
 * Main graph Interface. @visualization: if more or diffrent methods requested
 * add them here.
 */

/**
 * A graph. Supports iteration over the nodes.
 *
 * @param <V> the type of nodes in this graph
 */
public interface Graph<V> extends Iterable<V> {

	/**
	 * Returns all nodes that are directly connected to the specified node by an edge.
	 * 
	 * @param node the node in question
	 * @return all neighbours of the node in question, null if no neighbours exist
	 */
	List<V> getNeighbours(V node);
	/**
	 * Returns the length of the edge between node1 and node2.
	 * 
	 * @param node1, node2 the nodes in question
	 * @return double value of the distance and -1 if no edge exist between node1 and node2
	 */
	double getDistance(V node1, V node2);
	/**
	 * Returns all nodes in the graph.
	 * 
	 * @param none
	 * @return all nodes
	 */
	List<V> getAllNodes();
	
	
}
