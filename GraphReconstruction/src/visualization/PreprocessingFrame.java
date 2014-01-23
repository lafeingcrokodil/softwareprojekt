package visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import main.MetricGraph;
import main.MetricGraph.Edge;
import preprocessing.GPSMetricSpace;
import preprocessing.ImageMetricSpace;

public class PreprocessingFrame extends JFrame {

	private static final long serialVersionUID = -3982152879505777650L;

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	private static final int PADDING = 10;

	private Canvas canvas;
	private MetricGraph<Point2D> graph;

	JTextField fileInput, epsilonInput, alphaInput;
	JButton browseButton, previewButton, continueButton;
	JFileChooser fileChooser;

	public PreprocessingFrame() throws IOException {
		super("Preprocessing");

		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getTopPanel(), BorderLayout.NORTH);
		getContentPane().add(getCanvasPanel(), BorderLayout.CENTER);
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);

		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fileChooser.showOpenDialog(PreprocessingFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fileInput.setText(fileChooser.getSelectedFile().getPath());
				}
			}
		});

		previewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String file = fileInput.getText();
				double epsilon = Double.parseDouble(epsilonInput.getText());
				double alpha = Double.parseDouble(alphaInput.getText());

				try {
					if (file.endsWith(".gpx"))
						PreprocessingFrame.this.setGraph(new GPSMetricSpace(file, epsilon, alpha, false));
					else
						PreprocessingFrame.this.setGraph(new ImageMetricSpace(file, epsilon, alpha, false));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO check if preview graph already exists (avoid redundant calculations)

				String file = fileInput.getText();
				double epsilon = Double.parseDouble(epsilonInput.getText());
				double alpha = Double.parseDouble(alphaInput.getText());

				try {
					if (file.endsWith(".gpx"))
						graph = new GPSMetricSpace(file, epsilon, alpha);
					else
						graph = new ImageMetricSpace(file, epsilon, alpha);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel getTopPanel() {
		JPanel topPanel = new JPanel(new GridLayout(2,1));
		topPanel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
		topPanel.setBackground(Color.DARK_GRAY);
		topPanel.add(getFilePanel());
		topPanel.add(getParameterPanel());
		return topPanel;
	}

	private JPanel getFilePanel() {
		// create panel
		JPanel filePanel = new JPanel(new BorderLayout());
		filePanel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
		filePanel.setBackground(Color.DARK_GRAY);

		// add label
		JLabel fileLabel = new JLabel("File:");
		fileLabel.setForeground(Color.WHITE);
		filePanel.add(fileLabel, BorderLayout.WEST);

		// add text field for file name
		fileInput = new JTextField();
		fileInput.setBorder(new CompoundBorder(
				new MatteBorder(0, PADDING, 0, PADDING, Color.DARK_GRAY),
				fileInput.getBorder()
		));
		filePanel.add(fileInput, BorderLayout.CENTER);

		// add browse button
		browseButton = new JButton("Browse");
		filePanel.add(browseButton, BorderLayout.EAST);

		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		return filePanel;
	}

	private JPanel getParameterPanel() {
		JPanel parameterPanel = new JPanel(new GridLayout(1,2));
		parameterPanel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
		parameterPanel.setBackground(Color.DARK_GRAY);

		parameterPanel.add(getEpsilonPanel());
		parameterPanel.add(getAlphaPanel());

		return parameterPanel;
	}

	private JPanel getEpsilonPanel() {
		// create panel
		JPanel epsilonPanel = new JPanel(new BorderLayout());
		epsilonPanel.setBackground(Color.DARK_GRAY);

		// add label
		JLabel epsilonLabel = new JLabel("epsilon:");
		epsilonLabel.setForeground(Color.WHITE);
		epsilonPanel.add(epsilonLabel, BorderLayout.WEST);

		// add text field for epsilon value
		epsilonInput = new JTextField("0");
		epsilonInput.setBorder(new CompoundBorder(
				new MatteBorder(0, PADDING, 0, PADDING, Color.DARK_GRAY),
				epsilonInput.getBorder()
		));
		epsilonPanel.add(epsilonInput, BorderLayout.CENTER);

		return epsilonPanel;
	}

	private JPanel getAlphaPanel() {
		// create panel
		JPanel alphaPanel = new JPanel(new BorderLayout());
		alphaPanel.setBackground(Color.DARK_GRAY);

		// add label
		JLabel alphaLabel = new JLabel("alpha:");
		alphaLabel.setForeground(Color.WHITE);
		alphaPanel.add(alphaLabel, BorderLayout.WEST);

		// add text field for epsilon value
		alphaInput = new JTextField("0");
		alphaInput.setBorder(new CompoundBorder(
				new MatteBorder(0, PADDING, 0, 0, Color.DARK_GRAY),
				alphaInput.getBorder()
		));
		alphaPanel.add(alphaInput, BorderLayout.CENTER);

		return alphaPanel;
	}

	private JPanel getCanvasPanel() {
		JPanel canvasPanel = new JPanel(new BorderLayout());
		canvasPanel.setBorder(new EmptyBorder(0, 2*PADDING, 2*PADDING, 2*PADDING));
		canvasPanel.setBackground(Color.DARK_GRAY);
		canvas = new Canvas(WIDTH, HEIGHT);
		canvasPanel.add(canvas, BorderLayout.CENTER);
		return canvasPanel;
	}

	private JPanel getBottomPanel() {
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBorder(new EmptyBorder(0, 2*PADDING, 2*PADDING, 2*PADDING));
		bottomPanel.setBackground(Color.DARK_GRAY);

		previewButton = new JButton("Preview");
		bottomPanel.add(previewButton, BorderLayout.WEST);

		continueButton = new JButton("Continue");
		bottomPanel.add(continueButton, BorderLayout.EAST);

		return bottomPanel;
	}

	public void setGraph(MetricGraph<Point2D> graph) {
		this.graph = graph;
		canvas.setScale();
		repaint();
	}

	private class Canvas extends JPanel {

		private static final long serialVersionUID = -882600185441468062L;

		private static final int POINT_SIZE = 3;
		private double offsetX, offsetY;
		private double factor;

		public Canvas(int width, int height) {
//			setPreferredSize(new Dimension(width, height));
			setBackground(Color.WHITE);
		}

		private void setScale() {
			offsetX = Double.POSITIVE_INFINITY;
			offsetY = Double.POSITIVE_INFINITY;
			double maxX = Double.NEGATIVE_INFINITY;
			double maxY = Double.NEGATIVE_INFINITY;
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

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if (graph == null) return;
			setScale();
			Graphics2D g2 = (Graphics2D) g;
			System.out.println("Painting vertices and edges...");
			for (Point2D point : graph) {
				Point pixel = getPixel(point);
				g2.drawOval(
						pixel.x - (POINT_SIZE / 2),
						pixel.y - (POINT_SIZE / 2),
						POINT_SIZE, POINT_SIZE
				);
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
		new PreprocessingFrame();
	}
}
