package visualization;



import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import visualization.Model;
import visualization.MyPanel;

import javax.swing.JTextField;
import javax.swing.JLabel;

public class MainFrame extends JFrame {

	Model model;
	private JPanel contentPane;
	private MyPanel panel;
	private JScrollPane scrollPane;
	private JList list_data;
	private JButton btn_load_data;
	private JButton btn_save_data;
	private JButton btn_delete_data;
	private JButton btn_add_data;
	private JTextField text_xValue;
	private JTextField text_yValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {

		model = new Model();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		panel = new MyPanel();

		scrollPane = new JScrollPane();

		btn_load_data = new JButton("Load GPS");
		btn_load_data.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model.loadDataset("dataset.txt");
					refreshList();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		btn_save_data = new JButton("Save GPS");
		btn_save_data.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btn_delete_data = new JButton("Delete Selected GPS");
		btn_delete_data.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btn_add_data = new JButton("Add GPS");
		btn_add_data.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		text_xValue = new JTextField();
		text_xValue.setColumns(10);
		
		text_yValue = new JTextField();
		text_yValue.setColumns(10);
		
		JLabel lblX = new JLabel("x:");
		
		JLabel label = new JLabel("y:");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btn_add_data, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(text_yValue, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
								.addComponent(text_xValue, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))))
					.addGap(31)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btn_delete_data, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btn_load_data, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_save_data, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
						.addComponent(panel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(text_xValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblX))
					.addGap(4)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(text_yValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_save_data)
						.addComponent(btn_load_data, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_delete_data)
						.addComponent(btn_add_data))
					.addContainerGap())
		);
		refreshList();
		contentPane.setLayout(gl_contentPane);
	}

	public void refreshList() {
		DefaultListModel defListModel = new DefaultListModel();
		for (String testdata : model.getDataset()) {
			defListModel.addElement(testdata);
		}
		list_data = new JList(defListModel);
		scrollPane.setViewportView(list_data);
		list_data.setFixedCellHeight(30);
		list_data.setFixedCellWidth(80);
		list_data
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

	}
}
