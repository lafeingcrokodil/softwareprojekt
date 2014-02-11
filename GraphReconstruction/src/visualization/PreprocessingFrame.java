package visualization;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import main.MetricGraph.Edge;
import main.MetricSpace;
import preprocessing.GPSMetricSpace;
import preprocessing.ImageMetricSpace;
import preprocessing.NeighbourhoodGraph;

public class PreprocessingFrame extends JFrame {

	private static final long serialVersionUID = -3982152879505777650L;

	private final static Logger LOGGER = Logger.getLogger("Preprocessing");

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	private static final int PADDING = 10;

	private Canvas canvas;
	private NeighbourhoodGraph previewGraph;
	private MetricSpace<Point2D> preprocessedSpace;

	private JTextField fileInput, epsilonInput, alphaInput;
	private JButton browseButton, previewButton, continueButton;
	private JFileChooser fileChooser;
	private JLabel statusLabel;

	public PreprocessingFrame() throws IOException {
		super("Preprocessing");

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getTopPanel(), BorderLayout.NORTH);
		getContentPane().add(getCanvasPanel(), BorderLayout.CENTER);
		getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
		pack();

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
		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fileChooser.showOpenDialog(PreprocessingFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					fileInput.setText(fileChooser.getSelectedFile().getPath());
				}
			}
		});
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
		canvas = new PreprocessingCanvas(PADDING);
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
				try {
					String file = fileInput.getText();
					double epsilon = Double.parseDouble(epsilonInput.getText());
					double alpha = Double.parseDouble(alphaInput.getText());

					if (file.endsWith(".gpx"))
						previewGraph = GPSMetricSpace.preview(file, epsilon, alpha);
					else
						previewGraph = ImageMetricSpace.preview(file, epsilon, alpha);
					canvas.repaint();
				} catch (IOException e) {
					// TODO handle the exception more elegantly?
					throw new RuntimeException(e);
				}
			}
		});
		bottomPanel.add(previewButton, BorderLayout.WEST);

		statusLabel = new JLabel("", SwingConstants.CENTER);
		statusLabel.setForeground(Color.WHITE);
		statusLabel.setFont(new Font(null, Font.PLAIN, 18));
		bottomPanel.add(statusLabel, BorderLayout.CENTER);

		continueButton = new JButton("Continue");
		continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (previewGraph != null)
					preprocessedSpace = new NeighbourhoodGraph(previewGraph);
				else {
					try {
						String file = fileInput.getText();
						double epsilon = Double.parseDouble(epsilonInput.getText());
						double alpha = Double.parseDouble(alphaInput.getText());

						if (file.endsWith(".gpx"))
							preprocessedSpace = new GPSMetricSpace(file, epsilon, alpha);
						else
							preprocessedSpace = new ImageMetricSpace(file, epsilon, alpha);
					} catch (IOException e) {
						// TODO handle the exception more elegantly?
						throw new RuntimeException(e);
					}
				}
				new MainFrame(preprocessedSpace);
			}
		});
		bottomPanel.add(continueButton, BorderLayout.EAST);

		return bottomPanel;
	}

	private class PreprocessingCanvas extends Canvas {

		private static final long serialVersionUID = -882600185441468062L;

		private static final int POINT_SIZE = 3;

		public PreprocessingCanvas(int padding) {
			super(padding);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
			if (previewGraph != null) {
				setScale(previewGraph);
				for (Point2D vertex : previewGraph) {
					drawPoint(vertex, POINT_SIZE, Color.BLACK, g2);
					for (Edge<Point2D> edge : previewGraph.getNeighbours(vertex)) {
						if (vertex.getX() <= edge.neighbour.getX()) {
							drawEdge(vertex, edge.neighbour, Color.BLACK, g2);
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new PreprocessingFrame();
	}
}
