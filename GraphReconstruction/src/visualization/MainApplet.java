package visualization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import visualization.AddGPSPointController;
import visualization.EditPointController;
import visualization.Model;
import visualization.RemoveGPSController;

public class MainApplet extends JApplet {
	Model model;
	private MyPanel panel;
	private JScrollPane scrollPane;
	private JList list_data;
	private JButton btn_load_data;
	private JButton btn_save_data;
	private JButton btn_delete_data;
	private JLabel label_x;
	private JTextField text_xValue;
	private JLabel label_y;
	private JTextField text_yValue;
	private JButton btn_add_data;
	private JRadioButton rdbtn_CartesianPlot;
	private JRadioButton rdbtn_ColumnPlot;
	private String selectedRow;
	private JButton btn_edit_data;

	public MainApplet() {

		panel = new MyPanel();

		scrollPane = new JScrollPane();

		model = new Model();

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
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		btn_delete_data = new JButton("Delete Selected GPS");
		btn_delete_data.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RemoveGPSController(MainApplet.this.model)
						.removePoint(MainApplet.this);
			}
		});

		btn_add_data = new JButton("Add GPS");
		btn_add_data.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddGPSPointController(MainApplet.this.model)
						.addPoint(MainApplet.this);
			}
		});

		btn_edit_data = new JButton("Update Selected Data");
		btn_edit_data.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditPointController(MainApplet.this.model)
						.updatePoint(MainApplet.this);
			}
		});

		label_x = new JLabel("x:");

		text_xValue = new JTextField();
		text_xValue.setColumns(10);

		label_y = new JLabel("y:");

		text_yValue = new JTextField();
		text_yValue.setColumns(10);

		JCheckBox chckbx_trend_line = new JCheckBox("Show Trend Line");

		JCheckBox chckbx_grid = new JCheckBox("Show Grid");

		JCheckBox chckbx_x_axes = new JCheckBox("Show X Axes");

		JCheckBox chckbx_y_axes = new JCheckBox("Show Y Axes");

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(6)
																		.addComponent(
																				btn_add_data,
																				GroupLayout.PREFERRED_SIZE,
																				107,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addGroup(
																																groupLayout
																																		.createSequentialGroup()
																																		.addGap(134)
																																		.addComponent(
																																				rdbtn_CartesianPlot))
																														.addGroup(
																																groupLayout
																																		.createSequentialGroup()
																																		.addGap(6)
																																		.addComponent(
																																				btn_edit_data,
																																				GroupLayout.PREFERRED_SIZE,
																																				158,
																																				GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				btn_delete_data,
																																				GroupLayout.PREFERRED_SIZE,
																																				158,
																																				GroupLayout.PREFERRED_SIZE)))
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												btn_load_data)
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addGroup(
																																groupLayout
																																		.createSequentialGroup()
																																		.addGap(32)
																																		.addComponent(
																																				rdbtn_ColumnPlot,
																																				GroupLayout.PREFERRED_SIZE,
																																				119,
																																				GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																groupLayout
																																		.createSequentialGroup()
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				btn_save_data))))
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(29)
																										.addComponent(
																												chckbx_trend_line)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												chckbx_grid,
																												GroupLayout.PREFERRED_SIZE,
																												103,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												chckbx_x_axes,
																												GroupLayout.PREFERRED_SIZE,
																												112,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												chckbx_y_axes,
																												GroupLayout.PREFERRED_SIZE,
																												135,
																												GroupLayout.PREFERRED_SIZE))))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addComponent(
																												label_x,
																												GroupLayout.PREFERRED_SIZE,
																												20,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												text_xValue,
																												GroupLayout.PREFERRED_SIZE,
																												65,
																												GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								scrollPane,
																								Alignment.TRAILING,
																								GroupLayout.PREFERRED_SIZE,
																								91,
																								GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addComponent(
																												label_y,
																												GroupLayout.PREFERRED_SIZE,
																												20,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												text_yValue,
																												GroupLayout.PREFERRED_SIZE,
																												65,
																												GroupLayout.PREFERRED_SIZE)))
																		.addGap(18)
																		.addComponent(
																				panel,
																				GroupLayout.PREFERRED_SIZE,
																				517,
																				GroupLayout.PREFERRED_SIZE)))
										.addGap(209)));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(19)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																panel,
																GroupLayout.DEFAULT_SIZE,
																354,
																Short.MAX_VALUE)
														.addComponent(
																scrollPane,
																GroupLayout.DEFAULT_SIZE,
																354,
																Short.MAX_VALUE))
										.addGap(12)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																rdbtn_CartesianPlot)
														.addComponent(
																rdbtn_ColumnPlot)
														.addComponent(label_x)
														.addComponent(
																text_xValue,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								chckbx_trend_line)
																						.addComponent(
																								chckbx_grid)
																						.addComponent(
																								chckbx_x_axes)
																						.addComponent(
																								chckbx_y_axes))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								btn_add_data)
																						.addComponent(
																								btn_edit_data)
																						.addComponent(
																								btn_delete_data)
																						.addComponent(
																								btn_load_data)
																						.addComponent(
																								btn_save_data)))
														.addGroup(
																groupLayout
																		.createParallelGroup(
																				Alignment.BASELINE)
																		.addComponent(
																				label_y)
																		.addComponent(
																				text_yValue,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addGap(19)));

		refreshList();
		getContentPane().setLayout(groupLayout);

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
		list_data.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				} else {
					if (!list_data.isSelectionEmpty()) {
						new EditPointController(MainApplet.this.model)
								.setEditable(MainApplet.this);
						return;
					}
				}
			}
		});
	}

	// used for controller
	public JList getDataList() {
		return list_data;
	}

	public JTextField getXValue() {
		return text_xValue;
	}

	public JTextField getYValue() {
		return text_yValue;
	}

	public String getSelectedRow() {
		return list_data.getSelectedValue().toString();
	}
}
