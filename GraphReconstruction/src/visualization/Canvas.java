package visualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Collection;

import javax.swing.JPanel;

public class Canvas extends JPanel {

	private static final long serialVersionUID = -1932890107668271868L;

	protected final int PADDING;

	private double offsetX, offsetY;
	private double factor;

	public Canvas(int padding) {
		PADDING = padding;
		setBackground(Color.WHITE);
	}

	public void update(Collection<Point2D> points) {
		setScale(points);
		repaint();
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
}
