package visualization;

import javax.swing.DefaultListModel;

import visualization.MainApplet;
import visualization.Model;

public class RemoveGPSController {
	Model model;

	public RemoveGPSController(Model model) {
		this.model = model;
	}

	public boolean removePoint(MainApplet mainApplet) {
		int[] row = mainApplet.getDataList().getSelectedIndices();
		if (row.length == 0) {
			return false;
		}

		DefaultListModel list = (DefaultListModel) mainApplet.getDataList()
				.getModel();
		for (int idx = row.length - 1; idx >= 0; idx--) {
			list.remove(row[idx]);
			model.removePoint(row[idx]);
		}

		mainApplet.getXValue().setText("");
		mainApplet.getYValue().setText("");

		return true;
	}
}
