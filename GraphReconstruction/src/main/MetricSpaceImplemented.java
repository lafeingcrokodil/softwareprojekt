package main;

import java.util.HashSet;
import java.util.Set;

/**
 * A metric space implementation that provides certain methods that are useful
 * when reconstructing the underlying metric graph.
 * 
 * @param <P> the type of points in the metric space
 */
public class MetricSpaceImplemented<P> extends HashSet<P> implements MetricSpace<P> {

	private static final long serialVersionUID = 8694603574871676777L;

	/** The label used for preliminary branch points. */
	public static final int PREL_BRANCH = 1;
	/** The label used for edge points. */
	public static final int EDGE = 2;
	/** The label used for branch points. */
	public static final int BRANCH = 3;

	/** Set of edge points in the metric space. */
	private Set<P> edgePoints = new HashSet<P>();
	/** Set of preliminary branch points in the metric space. */
	private Set<P> prelBranchPoints = new HashSet<P>();
	/** Set of branch points in the metric space. */
	private Set<P> branchPoints = new HashSet<P>();

	/** The input space given in the constructor. */
	private MetricSpace<P> inputSpace;

	/**
	 * Creates a copy of the given metric space.
	 * 
	 * @param space a metric space
	 */
	public MetricSpaceImplemented(MetricSpace<P> space) {
		inputSpace = space;
		addAll(space);
	}

	/**
	 * Returns all points that lie in a ring about the specified centre point.
	 * The following is guaranteed for all points p in the output set:
	 * innerRadius &le; distance(centre, p) &le; outerRadius.
	 * 
	 * @param centre the point at the centre of the ring
	 * @param outerRadius the outer radius of the ring
	 * @param innerRadius the inner radius of the ring
	 * @return the set of all points that lie in the specified ring.
	 */
	public MetricSpaceImplemented<P> getPointsInRadius(P centre,
			double outerRadius, double innerRadius) {

		// make a copy of this metric space
		MetricSpaceImplemented<P> pointsInRadius = new MetricSpaceImplemented<>(this);

		// remove all points that don't lie in the ring
		for (P point : this) {
			double distance = distance(centre, point);
			if (distance < innerRadius || distance > outerRadius)
				pointsInRadius.remove(point);
		}

		return pointsInRadius;
	}

	/**
	 * Labels the given point with the specified label.
	 * 
	 * @param point the point to be labelled
	 * @param label the label to use
	 */
	public void labelAs(P point, int label) {
		// relabeling: point must be removed from preliminary label set
		prelBranchPoints.remove(point);
		edgePoints.remove(point);
		branchPoints.remove(point);

		if (label == PREL_BRANCH)
			prelBranchPoints.add(point);
		else if (label == EDGE)
			edgePoints.add(point);
		else if (label == BRANCH)
			branchPoints.add(point);
	}

	/**
	 * Labels all points within the given radius of the specified centre point
	 * as branch points.
	 * 
	 * @param centre the centre point
	 * @param radius the radius within which points should be labelled
	 */
	public void labelInRadius(P centre, double radius) {
		/*
		 * Does relabelling of edge points and preliminary branch points as
		 * branch points if necessary.
		 */
		for (P point : this) {
			if (distance(centre, point) <= radius)
				labelAs(point, BRANCH);
		}
	}

	/**
	 * Returns a metric space containing all points in this metric space that
	 * were given the specified label.
	 * 
	 * @param label one of EDGE, PREL_BRANCH or BRANCH
	 * @return a metric space containing all points with the specified label
	 */
	public MetricSpaceImplemented<P> getLabelledAs(int label) {
		MetricSpaceImplemented<P> subspace = new MetricSpaceImplemented<>(this);

		// remove all points (blank slate)
		subspace.clear();

		// add the relevant points back
		if (label == PREL_BRANCH)
			subspace.addAll(prelBranchPoints);
		else if (label == EDGE)
			subspace.addAll(edgePoints);
		else if (label == BRANCH)
			subspace.addAll(branchPoints);

		return subspace;
	}

	@Override
	public double distance(P a, P b) {
		/*
		 * Uses the distance method of the MetricSpace instance that is given in
		 * the constructor.
		 */
		return inputSpace.distance(a, b);
	}
}
