package preprocessing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.MetricGraph;
import main.MetricGraph.Edge;

public class PreprocessingVisualizer {

	private static class Canvas extends JPanel {

		private static final long serialVersionUID = -882600185441468062L;

		private MetricGraph<Point2D> graph;

		private final int CANVAS_SIZE = 500;

		private double offsetX, offsetY;
		private double factor;

		public Canvas(MetricGraph<Point2D> graph) {
			this.graph = graph;
			this.setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));
			setScale();
		}

		private void setScale() {
			offsetX = Double.POSITIVE_INFINITY;
			offsetY = Double.POSITIVE_INFINITY;
			double maxX = 0, maxY = 0;
			for (Point2D vertex : graph) {
				if (vertex.getX() < offsetX)
					offsetX = vertex.getX();
				if (vertex.getY() < offsetY)
					offsetY = vertex.getY();
				if (vertex.getX() > maxX)
					maxX = vertex.getX();
				if (vertex.getY() > maxY)
					maxY = vertex.getY();
			}
			double width = maxX - offsetX;
			double height = maxY - offsetY;
			factor = CANVAS_SIZE / Math.max(width, height);
		}

		private Point getPixel(Point2D point) {
			int x = (int) (factor * (point.getX() - offsetX));
			int y = (int) (factor * (point.getY() - offsetY));
			return new Point(x, y);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			System.out.println("Painting vertices and edges...");
			for (Point2D point : graph) {
				Point pixel = getPixel(point);
				g2.fillOval(pixel.x - 1, pixel.y - 1, 3, 3);
				for (Edge<Point2D> edge : graph.getNeighbours(point)) {
					if (point.getX() <= edge.neighbour.getX()) {
						Point otherPixel = getPixel(edge.neighbour);
						g2.drawLine(pixel.x, pixel.y, otherPixel.x, otherPixel.y);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		MetricGraph<Point2D> graph = getMetricGraph("HIKE");

		// set up main panel (which contains the canvas)
		final int PADDING = 10;
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
		mainPanel.add(new Canvas(graph));

		// set up the frame
		JFrame frame = new JFrame("Preprocessing Visualizer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	private static MetricGraph<Point2D> getMetricGraph(String name) throws IOException {
		switch (name) {
		case "WALK":
			return new GPSMetricSpace("traces/20131229_1358_walk.gpx", 0.00005, 0.00005);
		case "HIKE":
			return new GPSMetricSpace("traces/kamenny_chodnik4OSM.gpx", 0.001, 0.001);
		case "CUBE":
			return new ImageMetricSpace("images/cubeTest.png", 1);
		case "GRAPH":
			return new ImageMetricSpace("images/graph.png", 5, 5);
		default:
			return getMetricGraph();
		}
	}

	private static MetricGraph<Point2D> getMetricGraph() throws IOException {
		Scanner input = new Scanner(System.in);
		System.out.print("Data file: ");
		String file = input.nextLine();
		System.out.print("epsilon = ");
		double epsilon = Double.parseDouble(input.nextLine());
		System.out.print("alpha = ");
		double alpha = Double.parseDouble(input.nextLine());
		input.close();
		if (file.endsWith(".gpx"))
			return new GPSMetricSpace(file, epsilon, alpha);
		else
			return new ImageMetricSpace(file, alpha);
	}
}
