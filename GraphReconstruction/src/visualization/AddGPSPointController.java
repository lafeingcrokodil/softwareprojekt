package visualization;

import javax.swing.DefaultListModel;

import visualization.MainApplet;
import visualization.Model;

public class AddGPSPointController {
	Model model;

	public AddGPSPointController(Model model) {
		this.model = model;
	}

	public boolean addPoint(MainApplet mainApplet) {
		String pointXValue = mainApplet.getXValue().getText();
		String pointYValue = mainApplet.getYValue().getText();

		mainApplet.getXValue().setText("");
		mainApplet.getYValue().setText("");

		if (pointXValue.length() == 0 || pointYValue.length() == 0) {
			return false;
		}

		model.getPointFromInput(pointXValue, pointYValue);

				DefaultListModel list = (DefaultListModel) mainApplet.getDataList()
				.getModel();
		int idx = list.getSize();
		list.add(idx, model.printPoint());
		return true;
	}
}
