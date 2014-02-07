package main;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * A Rips-Vietoris graph.
 *
 * @author Mahmoud Kassem, Moritz Walter
 * 
 * @param <V> the type of vertex in this graph
 */
public class RipsVietoris<V> {

	/** The metric space upon which this graph is based. */
	private MetricSpace<V> space;

	/** The radius parameter used in constructing this graph. */
	private double radius;

	/** The set of connected components in this graph. */
	private Set<Set<V>> components;
	
	/**
	 * Constructs a Rips-Vietoris graph based on the specified metric space.
	 * 
	 * @param space a metric space
	 * @param radius the radius parameter to be used in constructing the graph
	 */
	public RipsVietoris(MetricSpace<V> space, double radius) {
		this.space = space;
		this.radius = radius;
		this.components = calculateComponents();
	}
	
	private Set<Set<V>> calculateComponents() {
		Set<Set<V>> components = new HashSet<>();

		// copy all points from the metric space to a queue
		Queue<V> mainQueue = new LinkedList<>();
		mainQueue.addAll(space);

		while (!mainQueue.isEmpty()) {
			// create a new component
			Set<V> component = new HashSet<>();

			// add starting point to a new queue of points belonging to the component
			Queue<V> componentQueue = new LinkedList<>();
			componentQueue.add(mainQueue.poll());

			// look for other points in the same component
			while (!componentQueue.isEmpty()) {

				// take a point from the component queue
				V point = componentQueue.poll();

				// find all "neighbours" of the point and add them to the queue
				for (Iterator<V> iter = mainQueue.iterator(); iter.hasNext(); ){
					V otherPoint = iter.next(); // potential neighbour
					if (space.distance(point, otherPoint) <= radius) {
						componentQueue.add(otherPoint);
						iter.remove();
					}
				}

				// add the processed point to the component
				component.add(point);
			}

			// add the new component to the set of components
			components.add(component);
		}

		return components;
	}

	/**
	 * Returns the connected components of this graph.
	 * 
	 * @return the connected components of this graph
	 */
	public Set<Set<V>> getComponents() {
		return components;
	}

	/**
	 * Returns the degree of this graph. The degree is defined as the number
	 * of connected components.
	 * 
	 * @return the number of connected components in this graph
	 */
	public int getDegree() {
		return components.size();
	}

}
