package de.xtion.drone.model;

import de.xtion.drone.model.util.Model;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

/**
 * The Model holds all information about the CircleDetection.
 * <p/>
 * Model for the GUI. The class provides all GUI objects with information and
 * events - it's pretty "blown up" because of the getter and setter and an ugly
 * workaround of integer restriction in sliders - nothing special happens here.
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
		// The sliderMultiplier is used for the GUI - Sliders only accept
		// integer values but since openCV woks with doubles and is in need to
		// have small changes this will be used to shift the decimal places.
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
	 * @return The return value is the result of the CircleDetection
	 */
	public BufferedImage getCircleImage() {
		return circleImage;
	}

	/**
	 * @param edgeImage The value represents the new BufferedImage from the
	 *                  circle detection
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

	/**
	 * @param withMultiplier The value determines if a multiplier shall be used
	 *
	 * @return The return value is the maximum destiny that will be used by the
	 * CircleDetection
	 */
	public double getDensityMax(boolean withMultiplier) {
		if(withMultiplier) {
			return densityMax * sliderMultiplier;
		}
		return densityMax;
	}

	public double getDensityMin() {
		return getDensityMin(false);
	}

	/**
	 * @param withMultiplier The value determines if a multiplier shall be used
	 *
	 * @return The return value is the minimum destiny that will be used by the
	 * CircleDetection
	 */
	public double getDensityMin(boolean withMultiplier) {
		if(withMultiplier) {
			return densityMin * sliderMultiplier;
		}
		return densityMin;
	}

	public double getDensity() {
		return getDensity(false);
	}

	/**
	 * @param withMultiplier The value determines if a multiplier shall be used
	 *
	 * @return The return value is the destiny that will be used by the
	 * CircleDetection
	 */
	public double getDensity(boolean withMultiplier) {
		if(withMultiplier) {
			return density * sliderMultiplier;
		}
		return density;
	}

	public void setDensity(double density) {
		setDensity(density, false);
	}

	/**
	 * @param density        The value determines the new destiny that shall be
	 *                       used by the Circle Detection
	 * @param withMultiplier The value determines if a multiplier was previously
	 *                       used
	 */
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

	/**
	 * Enum list used for the events of the CircleModel
	 */
	public enum CircleModelEvent {
		CIRCLE_IMG, CIRCLES, DENSITY, CIRCLE_MAX_SIZE, CIRCLE_MIN_SIZE
	}
}
