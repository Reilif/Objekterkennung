package de.xtion.drone.model;

import de.xtion.drone.model.util.Model;

public class MainModel extends Model<MainModel.MainModelEvents> {
	private final EdgeModel      edgeModel;
	private final ColorModel     colorModel;
	private final CircleModel    circleModel;
	private final ColorEdgeModel colorEdgeModel;
    private final ImgProcessingModel imgProcModel;

	public MainModel() {
		super(MainModelEvents.values());
		edgeModel = new EdgeModel();
		colorModel = new ColorModel();
		circleModel = new CircleModel();
		colorEdgeModel = new ColorEdgeModel();
        imgProcModel = new ImgProcessingModel();
	}

	public EdgeModel getEdgeModel() {
		return edgeModel;
	}

	public ColorModel getColorModel() {
		return colorModel;
	}

	public CircleModel getCircleModel() {
		return circleModel;
	}

	public ColorEdgeModel getColorEdgeModel() {
		return colorEdgeModel;
	}

    public ImgProcessingModel getImgProcModel(){
        return imgProcModel;
    }

	public enum MainModelEvents {

	}
}
