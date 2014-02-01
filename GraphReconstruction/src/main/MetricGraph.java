package main;

import java.util.Set;

/**
 * A graph that supports the calculation of distances between vertices.
 * 
 * The metric space underlying this metric graph is the set of vertices,
 * together with a distance method.
 * 
 * @param <V> the type of vertices in this graph
 */
public interface MetricGraph<V> extends MetricSpace<V> {

	/**
	 * Returns all edges adjacent to the specified vertex.
	 * 
	 * @param vertex a vertex in the graph
	 * @return all edges adjacent to the specified vertex
	 */
	Set<Edge<V>> getNeighbours(V vertex);

	/**
	 * An edge adjacent to some vertex x in the graph. Since x is known
	 * already, it is enough to specify the vertex y that is at the
	 * other end of the edge. This vertex y is stored in the
	 * <code>neighbour</code> field.
	 *
	 * @param <V> the type of vertices in the graph
	 */
	public class Edge<V> {
		/** The vertex at the other end of this edge. */
		public V neighbour;

		/** The length of this edge. */
		public double distance;

		/**
		 * Creates a new edge.
		 * 
		 * @param neighbour the vertex at the other end of this edge
		 * @param distance the length of this edge
		 */
		public Edge(V neighbour, double distance) {
			this.neighbour = neighbour;
			this.distance = distance;
		}
	}

}