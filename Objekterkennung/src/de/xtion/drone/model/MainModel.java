package de.xtion.drone.model;

import de.xtion.drone.model.util.Model;


public class MainModel extends Model<MainModel.MainModelEvents> {
	private EdgeModel edgeModel;

	public MainModel() {
		super(MainModelEvents.values());
		edgeModel = new EdgeModel();
	}

	public EdgeModel getEdgeModel() {
		return edgeModel;
	}

	public enum MainModelEvents {

	}
}
