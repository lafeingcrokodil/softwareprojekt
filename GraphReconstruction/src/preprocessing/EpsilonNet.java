package preprocessing;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * An epsilon net of a set of points in two-dimensional Euclidean space. The
 * following properties are satisfied:
 * 
 * <ul>
 * <li>For every point x in the base set, there is a point y in the epsilon net
 * such that distance(x, y) < epsilon.
 * <li>For every two distinct points x and y in the epsilon net, distance(x, y) &ge; epsilon.
 * </ul>
 * 
 * Implemented using the algorithm described in the proof of Lemma 2.2 in <a
 * href="http://web.engr.illinois.edu/~raichel2/aggregate.pdf">
 * "Net and Prune: A Linear Time Algorithm for Euclidean Distance Problems" </a>
 * (by Sariel Har-Peled and Benjamin Raichel).
 * 
 * @author Terese Haimberger
 */
public class EpsilonNet extends HashSet<Point2D> {

	private static final long serialVersionUID = 1705833901027258624L;

	/**
	 * The width of the grid cells used in this implementation.
	 */
	private double gridSize;

	/**
	 * Comparator that compares points in lexicographical order.
	 */
	private static Comparator<Point2D> pointComparator = new Comparator<Point2D>() {
		@Override
		public int compare(Point2D a, Point2D b) {
			if (a.getX() < b.getX())
				return -1;
			else if (a.getX() > b.getX())
				return 1;
			else if (a.getY() < b.getY())
				return -1;
			else if (a.getY() > b.getY())
				return 1;
			else
				return 0;
		}
	};

	/**
	 * Creates an epsilon net of the specified point set.
	 * 
	 * @param points a point set
	 * @param epsilon specifies the granularity of the epsilon net
	 */
	public EpsilonNet(Set<Point2D> points, double epsilon) {
		gridSize = epsilon / (2 * Math.sqrt(2));

		// sort points by grid cell
		Map<Point, Set<Point2D>> cells = new HashMap<>();
		for (Point2D point : points) {
			// calculate grid cell key for the point
			Point cellKey = getCellKey(point);

			// add point to grid cell
			if (!cells.containsKey(cellKey))
				cells.put(cellKey, new HashSet<Point2D>());
			cells.get(cellKey).add(point);
		}

		// store set of points that aren't yet covered by the epsilon net
		SortedSet<Point2D> remainingPoints = new TreeSet<Point2D>(pointComparator);
		remainingPoints.addAll(points);

		while (!remainingPoints.isEmpty()) {
			// we consider points in lexicographical order
			Point2D point = remainingPoints.first();

			// add the current point to the epsilon net
			add(point);
			
			// remove the point and all neighbouring points from remaining points
			remainingPoints.remove(point);
			for (Point key : getNeighbourhoodKeys(point)) {
				if (cells.containsKey(key))
					for (Point2D otherPoint : cells.get(key))
						if (point.distance(otherPoint) < epsilon)
							remainingPoints.remove(otherPoint);
			}
		}
	}

	/**
	 * Returns the coordinates of the lower left hand corner of the grid cell
	 * containing the specified point. These coordinates function as the key for
	 * the grid cell.
	 * 
	 * @param point the point in question
	 * @return the key for the cell containing the point
	 */
	private Point getCellKey(Point2D point) {
		int x = (int) Math.floor(point.getX() / gridSize);
		int y = (int) Math.floor(point.getY() / gridSize);
		return new Point(x, y);
	}

	/**
	 * Returns the keys for all grid cells that might contain a point in the
	 * specified point's epsilon neighbourhood.
	 * 
	 * Since epsilon = 2*sqrt(2)*gridSize = 2.8*gridSize (approximately), the
	 * neighbourhood includes all cells within three cells of the centre cell.
	 * 
	 * Technically, some of the cells around the edges could be left out, but we
	 * left them in because the added complexity would not be worth the small
	 * gain in efficiency.
	 * 
	 * @param point the point in question
	 * @return keys for grid cells that may contain neighbours
	 */
	private Set<Point> getNeighbourhoodKeys(Point2D point) {
		Point cellKey = getCellKey(point);
		Set<Point> keys = new HashSet<>();
		for (int dx = -3; dx <= 3; dx++)
			for (int dy = -3; dy <= 3; dy++)
				keys.add(new Point(cellKey.x + dx, cellKey.y + dy));
		return keys;
	}
}
