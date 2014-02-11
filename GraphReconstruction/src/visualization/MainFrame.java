package visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import main.MetricGraph;
import main.MetricGraph.Edge;
import main.MetricSpace;
import main.MetricSpaceImplemented;
import main.ReconstructedGraph;
import main.Reconstruction;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1449318343332371574L;

	private final static Logger LOGGER = Logger.getLogger("Main");

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	private static final int PADDING = 10;

	private Canvas canvas;
	private MetricSpace<Point2D> inputSpace;
	private MetricSpaceImplemented<Point2D> previewSpace;
	private MetricGraph<Set<Point2D>> outputGraph;

	private JTextField radiusInput;
	private JLabel statusLabel;
	private JButton previewButton, reconstructButton;

	private Map<Shape, Point2D> vertices = new HashMap<>();
	private Map<Shape, Double> edges = new HashMap<>();

	public MainFrame(MetricSpace<Point2D> space) {
		super("Metric Graph Reconstruction");

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getTopPanel(), BorderLayout.NORTH);
		getContentPane().add(getCanvasPanel(), BorderLayout.CENTER);
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		pack();
		
		inputSpace = space;
		canvas.repaint();

		LOGGER.addHandler(new Handler() {
			@Override
			public void publish(LogRecord record) {
				statusLabel.setText(record.getMessage());
				statusLabel.paintImmediately(statusLabel.getVisibleRect());
			}

			@Override
			public void close() throws SecurityException {}

			@Override
			public void flush() {}
		});

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel getTopPanel() {
		JPanel topPanel = new JPanel(new GridLayout(1,1));
		topPanel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
		topPanel.setBackground(Color.DARK_GRAY);
		topPanel.add(getRadiusPanel());
		return topPanel;
	}

	private JPanel getRadiusPanel() {
		JPanel radiusPanel = new JPanel(new BorderLayout());
		radiusPanel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
		radiusPanel.setBackground(Color.DARK_GRAY);

		// add label
		JLabel radiusLabel = new JLabel("Radius:");
		radiusLabel.setForeground(Color.WHITE);
		radiusPanel.add(radiusLabel, BorderLayout.WEST);

		// add text field for file name
		radiusInput = new JTextField("0");
		radiusInput.setBorder(new CompoundBorder(
				new MatteBorder(0, PADDING, 0, 0, Color.DARK_GRAY),
				radiusInput.getBorder()
		));
		radiusPanel.add(radiusInput, BorderLayout.CENTER);

		return radiusPanel;
	}

	private JPanel getCanvasPanel() {
		JPanel canvasPanel = new JPanel(new BorderLayout());
		canvasPanel.setBorder(new EmptyBorder(0, 2*PADDING, 2*PADDING, 2*PADDING));
		canvasPanel.setBackground(Color.DARK_GRAY);
		canvas = new MainCanvas(PADDING);
		canvasPanel.add(canvas, BorderLayout.CENTER);
		return canvasPanel;
	}

	private JPanel getBottomPanel() {
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBorder(new EmptyBorder(0, 2*PADDING, 2*PADDING, 2*PADDING));
		bottomPanel.setBackground(Color.DARK_GRAY);

		previewButton = new JButton("Preview");
		previewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double radius = Double.parseDouble(radiusInput.getText());
				previewSpace = Reconstruction.labelPoints(inputSpace, radius);
				outputGraph = null;
				canvas.repaint();
			}
		});
		bottomPanel.add(previewButton, BorderLayout.WEST);

		statusLabel = new JLabel("", SwingConstants.CENTER);
		statusLabel.setForeground(Color.WHITE);
		statusLabel.setFont(new Font(null, Font.PLAIN, 18));
		bottomPanel.add(statusLabel, BorderLayout.CENTER);

		reconstructButton = new JButton("Reconstruct!");
		reconstructButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double radius = Double.parseDouble(radiusInput.getText());
				Reconstruction<Point2D> reconstr = new Reconstruction<>(inputSpace, radius);
				outputGraph = reconstr.reconstructMetricGraph();
				previewSpace = reconstr.getWorkspace();
				canvas.repaint();
			}
		});
		bottomPanel.add(reconstructButton, BorderLayout.EAST);

		return bottomPanel;
	}

	private class MainCanvas extends Canvas {

		private static final long serialVersionUID = -6303008591131877712L;

		private static final int POINT_SIZE = 5;

		public MainCanvas(int padding) {
			super(padding);
			addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					for (Shape vertex : vertices.keySet()) {
						if (vertex.intersects(new Rectangle2D.Double(
								e.getX() - POINT_SIZE / 2,
								e.getY() - POINT_SIZE / 2,
								POINT_SIZE, POINT_SIZE
						))) {
							Point2D v = vertices.get(vertex);
							statusLabel.setText("(" + v.getX() + "," + v.getY() + ")");
							return;
						}
					}

					for (Shape edge : edges.keySet()) {
						if (edge.intersects(new Rectangle2D.Double(
								e.getX() - POINT_SIZE / 2,
								e.getY() - POINT_SIZE / 2,
								POINT_SIZE, POINT_SIZE
						))) {
							statusLabel.setText(edges.get(edge).toString());
							return;
						}
					}
				}
			});
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;

			setScale(inputSpace); // in case the canvas has been resized

			// draw input metric space
			if (previewSpace != null)
				drawPreviewSpace(g2);
			else
				drawInputSpace(g2);

			// draw output metric graph
			if (outputGraph != null)
				drawOutputGraph(g2);
		}

		private void drawInputSpace(Graphics2D g2) {
			drawPoints(inputSpace, POINT_SIZE, Color.BLACK, g2);
		}

		private void drawPreviewSpace(Graphics2D g2) {
			drawPoints( // draw edge points
					previewSpace.getLabelledAs(MetricSpaceImplemented.EDGE),
					POINT_SIZE, Color.BLUE, g2
			);

			drawPoints( // draw branch points
					previewSpace.getLabelledAs(MetricSpaceImplemented.BRANCH),
					POINT_SIZE, Color.RED, g2
			);

			drawPoints( // draw preliminary branch points
					previewSpace.getLabelledAs(MetricSpaceImplemented.PREL_BRANCH),
					POINT_SIZE, Color.ORANGE, g2
			);
		}

		private void drawOutputGraph(Graphics2D g2) {
			vertices.clear();
			edges.clear();

			// gather all vertices and edges of the output graph
			for (Set<Point2D> vertex : outputGraph) {
				Point2D centrePoint = getCentrePoint(vertex);
				vertices.put(getPoint(centrePoint, 2*POINT_SIZE), centrePoint);
				for (Edge<Set<Point2D>> edge : outputGraph.getNeighbours(vertex)) {
					Point2D neighbourCentre = getCentrePoint(edge.neighbour);
					if (centrePoint.equals(neighbourCentre)) {
						ReconstructedGraph<Point2D>.ReconstructedEdge reconstrEdge = (ReconstructedGraph<Point2D>.ReconstructedEdge) edge;
						edges.put(getLoop(centrePoint, reconstrEdge.points), edge.distance);
					} else if (centrePoint.getX() < neighbourCentre.getX()
							|| (centrePoint.getX() == neighbourCentre.getX() && centrePoint.getY() <= neighbourCentre.getY())) {
						edges.put(getEdge(centrePoint, neighbourCentre), edge.distance);
					}
				}
			}

			// draw the edges in blue
			g2.setColor(Color.BLUE);
			for (Shape edge : edges.keySet()) {
				g2.draw(edge);
			}

			// draw the vertices in red
			g2.setColor(Color.RED);
			for (Shape vertex : vertices.keySet()) {
				g2.fill(vertex);
			}
		}
	}

}
