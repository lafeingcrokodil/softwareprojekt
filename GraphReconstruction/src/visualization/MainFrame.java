package visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import main.MetricGraph;
import main.MetricGraph.Edge;
import main.MetricSpace;
import main.MetricSpaceImplemented;
import main.Reconstruction;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1449318343332371574L;

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	private static final int PADDING = 10;

	private Canvas canvas;
	private MetricSpace<Point2D> inputSpace;
	private MetricSpaceImplemented<Point2D> previewSpace;
	private MetricGraph<LinkedList<Point2D>> outputGraph;

	JTextField radiusInput;
	JButton previewButton, reconstructButton;

	public MainFrame(MetricSpace<Point2D> space) {
		super("Metric Graph Reconstruction");

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getTopPanel(), BorderLayout.NORTH);
		getContentPane().add(getCanvasPanel(), BorderLayout.CENTER);
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		pack();
		
		inputSpace = space;
		canvas.update(inputSpace);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				Reconstruction<Point2D> reconstr = new Reconstruction<>(inputSpace, radius);
				previewSpace = reconstr.testreturn();
				canvas.repaint();
			}
		});
		bottomPanel.add(previewButton, BorderLayout.WEST);

		reconstructButton = new JButton("Reconstruct!");
		reconstructButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double radius = Double.parseDouble(radiusInput.getText());
				Reconstruction<Point2D> reconstr = new Reconstruction<>(inputSpace, radius);
				outputGraph = reconstr.get_graph();
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
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;

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
					previewSpace.getLabeledAs(MetricSpaceImplemented.EDGE),
					POINT_SIZE, Color.BLUE, g2
			);

			drawPoints( // draw branch points
					previewSpace.getLabeledAs(MetricSpaceImplemented.BRANCH),
					POINT_SIZE, Color.RED, g2
			);

			drawPoints( // draw preliminary branch points
					previewSpace.getLabeledAs(MetricSpaceImplemented.PREL_BRANCH),
					POINT_SIZE, Color.ORANGE, g2
			);
		}

		private void drawOutputGraph(Graphics2D g2) {
			for (LinkedList<Point2D> vertex : outputGraph) {
				drawPoint(vertex.get(0), 2*POINT_SIZE, Color.RED, g2);
				for (Edge<LinkedList<Point2D>> edge : outputGraph.getNeighbours(vertex)) {
					if (vertex.get(0).getX() <= edge.neighbour.get(0).getX()) {
						drawEdge(vertex.get(0), edge.neighbour.get(0), Color.BLUE, g2);
					}
				}
			}
		}
	}

}
