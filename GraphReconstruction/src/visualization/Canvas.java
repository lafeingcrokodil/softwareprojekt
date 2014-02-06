package visualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Set;

import javax.swing.JPanel;

public class Canvas extends JPanel {

	private static final long serialVersionUID = -1932890107668271868L;

	protected final int PADDING;

	private double offsetX, offsetY;
	private double factor;

	private CubicCurve2D curve;

	public Canvas(int padding) {
		PADDING = padding;
		setBackground(Color.WHITE);
		curve = new CubicCurve2D.Double();
	}

	public void setScale(Collection<Point2D> points) {
		offsetX = Double.POSITIVE_INFINITY;
		offsetY = Double.POSITIVE_INFINITY;
		double maxX = Double.NEGATIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;
		for (Point2D point : points) {
			if (point.getX() < offsetX)
				offsetX = point.getX();
			if (point.getY() < offsetY)
				offsetY = point.getY();
			if (point.getX() > maxX)
				maxX = point.getX();
			if (point.getY() > maxY)
				maxY = point.getY();
		}
		double width = maxX - offsetX;
		double height = maxY - offsetY;
		Dimension canvasSize = getSize();
		factor = Math.min(
				(canvasSize.width - 2 * PADDING) / width,
				(canvasSize.height - 2 * PADDING) / height
		);
	}

	private Point getPixel(Point2D point) {
		int x = (int) (factor * (point.getX() - offsetX)) + PADDING;
		int y = (int) (factor * (point.getY() - offsetY)) + PADDING;
		return new Point(x, y);
	}

	protected void drawPoints(Collection<Point2D> points, int size, Color colour, Graphics2D g2) {
		for (Point2D point : points) {
			drawPoint(point, size, colour, g2);
		}
	}

	protected void drawPoint(Point2D point, int size, Color colour, Graphics2D g2) {
		Point pixel = getPixel(point);
		g2.setColor(colour);
		g2.fillOval(
				pixel.x - (size / 2),
				pixel.y - (size / 2),
				size, size
		);
	}

	protected void drawEdge(Point2D a, Point2D b, Color colour, Graphics2D g2) {
		Point aPixel = getPixel(a);
		Point bPixel = getPixel(b);
		g2.setColor(colour);
		g2.drawLine(aPixel.x, aPixel.y, bPixel.x, bPixel.y);
	}

	protected void drawLoop(Point2D vertex, Set<Point2D> loopPoints, Color colour, Graphics2D g2) {
		Point pixel = getPixel(vertex);
		Point loopCentre = getPixel(getCentrePoint(loopPoints));

		// initialize control points
		Point[] controlPoints = {
				new Point(pixel.x + 100, pixel.y - 100),
				new Point(pixel.x + 100, pixel.y + 100)
		};

		// rotate control points to point in the direction of the loop's centre
		double angle = Math.atan((double) (loopCentre.y - pixel.y) / (loopCentre.x - pixel.x));
		AffineTransform rotation = AffineTransform.getRotateInstance(angle, pixel.x, pixel.y);
		rotation.transform(controlPoints[0], controlPoints[0]);
		rotation.transform(controlPoints[1], controlPoints[1]);

		// draw the loop
		g2.setColor(colour);
		curve.setCurve(pixel, controlPoints[0], controlPoints[1], pixel);
		g2.draw(curve);
	}

	protected Point2D getCentrePoint(Set<Point2D> points) {
		double minX = Double.POSITIVE_INFINITY;
		double minY = Double.POSITIVE_INFINITY;
		double maxX = Double.NEGATIVE_INFINITY;
		double maxY = Double.NEGATIVE_INFINITY;

		for (Point2D point : points) {
			minX = Math.min(minX, point.getX());
			minY = Math.min(minY, point.getY());
			maxX = Math.max(maxX, point.getX());
			maxY = Math.max(maxY, point.getY());
		}

		return new Point2D.Double((minX + maxX) / 2, (minY + maxY) / 2);
	}
}
