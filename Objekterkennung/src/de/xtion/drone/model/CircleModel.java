package de.xtion.drone.model;

import de.xtion.drone.model.util.Model;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

/**
 * Model of the GUI.
 * The class provides all GUI objects with information and events - it's pretty "blown up" because of the getter and
 * setter and an ugly workaround of integer restriction in sliders - nothing special happens here.
 */
public class CircleModel extends Model<CircleModel.CircleModelEvent> {
	private final int           sliderMultiplier;
	private final double        densityMin;
	private final double        densityMax;
	private final int           circleMaxSizeMin;
	private final int           circleMaxSizeMax;
	private final int           circleMinSizeMin;
	private final int           circleMinSizeMax;
	private       BufferedImage circleImage;
	private       Mat           circles;
	private       double        density;
	private       int           circleMaxSize;
	private       int           circleMinSize;

	/**
	 * Initialisation of all values
	 */
	public CircleModel() {
		super(CircleModel.CircleModelEvent.values());
		sliderMultiplier = 100;

		density = 1.5;
		densityMin = 1;
		densityMax = 5;

		circleMinSize = 5;
		circleMinSizeMin = 1;
		circleMinSizeMax = 600;

		circleMaxSize = 500;
		circleMaxSizeMin = 1;
		circleMaxSizeMax = 600;
	}

	/**
	 * @return The return value is the result of the Hough-Transformation function (edge detection)
	 */
	public BufferedImage getCircleImage() {
		return circleImage;
	}

	/**
	 * @param edgeImage The value represents the new BufferedImage from teh edge detection
	 */
	public void setCircleImage(BufferedImage edgeImage) {
		this.circleImage = edgeImage;
		fireModelEvent(CircleModelEvent.CIRCLE_IMG);
	}

	public int getCircleMaxSize() {
		return circleMaxSize;
	}

	public void setCircleMaxSize(int maxSize) {
		circleMaxSize = maxSize;
		fireModelEvent(CircleModelEvent.CIRCLE_MAX_SIZE);
	}

	public int getCircleMinSize() {
		return circleMinSize;
	}

	public void setCircleMinSize(int minSize) {
		circleMinSize = minSize;
		fireModelEvent(CircleModelEvent.CIRCLE_MIN_SIZE);
	}

	public int getCircleMinSizeMax() {
		return circleMinSizeMax;
	}

	public int getCircleMaxSizeMax() {
		return circleMaxSizeMax;
	}

	public int getCircleMinSizeMin() {
		return circleMinSizeMin;
	}

	public int getCircleMaxSizeMin() {
		return circleMaxSizeMin;
	}

	public double getDensityMax() {
		return getDensityMax(true);
	}

	public double getDensityMax(boolean withMultiplier) {
		if(withMultiplier) {
			return densityMax * sliderMultiplier;
		}
		return densityMax;
	}

	public double getDensityMin() {
		return getDensityMin(false);
	}

	public double getDensityMin(boolean withMultiplier) {
		if(withMultiplier) {
			return densityMin * sliderMultiplier;
		}
		return densityMin;
	}

	public double getDensity() {
		return getDensity(false);
	}

	public double getDensity(boolean withMultiplier) {
		if(withMultiplier) {
			return density * sliderMultiplier;
		}
		return density;
	}

	public void setDensity(double density) {
		setDensity(density, false);
	}

	public void setDensity(double density, boolean withMultiplier) {
		if(withMultiplier) {
			this.density = density / sliderMultiplier;
		} else {
			this.density = density;
		}
		fireModelEvent(CircleModelEvent.DENSITY);
	}

	public Mat getCircles() {
		return circles;
	}

	public void setCircles(Mat circles) {
		this.circles = circles;
		fireModelEvent(CircleModelEvent.CIRCLES);
	}

	public int getSliderMultiplier() {
		return sliderMultiplier;
	}

	/**
	 * Enum list used for the events of the EdgeModel
	 */
	public enum CircleModelEvent {
		CIRCLE_IMG, CIRCLES, DENSITY, CIRCLE_MAX_SIZE, CIRCLE_MIN_SIZE
	}
}
