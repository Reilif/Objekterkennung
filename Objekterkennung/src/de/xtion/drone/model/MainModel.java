package de.xtion.drone.model;

import de.xtion.drone.model.util.Model;

public class MainModel extends Model<MainModel.MainModelEvents> {
	private final EdgeModel  edgeModel;
	private final ColorModel colorModel;

	public MainModel() {
		super(MainModelEvents.values());
		edgeModel = new EdgeModel();
		colorModel = new ColorModel();
	}

	public EdgeModel getEdgeModel() {
		return edgeModel;
	}

	public ColorModel getColorModel() {
		return colorModel;
	}

	public enum MainModelEvents {

	}
}
