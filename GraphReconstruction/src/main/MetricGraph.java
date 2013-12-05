package main;

import java.util.List;

/**
 * A graph that supports the calculation of distances between nodes.
 * 
 * The metric space underlying this metric graph is the set of nodes,
 * together with a distance method.
 * 
 * @param <V> the type of nodes in this graph
 */
public interface MetricGraph<V> extends MetricSpace<V> {

	/**
	 * Returns all edges adjacent to the specified node.
	 * 
	 * @param node a node in the graph
	 * @return all edges adjacent to the specified node
	 */
	List<Edge<V>> getNeighbours(V node);

	/**
	 * An edge adjacent to some node x in the graph. Since x is known
	 * already, it is enough to specify the node y that is at the
	 * other end of the edge. This node y is stored in the
	 * <code>neighbour</code> field.
	 *
	 * @param <V> the type of nodes in the graph
	 */
	public class Edge<V> {
		/** The node at the other end of this edge. */
		public V neighbour;

		/** The length of this edge. */
		public double distance;

		/**
		 * Creates a new edge.
		 * 
		 * @param neighbour the node at the other end of this edge
		 * @param distance the length of this edge
		 */
		public Edge(V neighbour, double distance) {
			this.neighbour = neighbour;
			this.distance = distance;
		}
	}

}