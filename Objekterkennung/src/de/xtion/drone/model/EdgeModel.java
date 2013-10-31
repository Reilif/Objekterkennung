package de.xtion.drone.model;

import de.xtion.drone.model.util.Model;

import java.awt.image.BufferedImage;

/**
 * Model of the GUI.
 * The class provides all GUI objects with information and events - it's pretty "blown up" because of the getter and
 * setter and an ugly workaround of integer restriction in sliders - nothing special happens here.
 */
public class EdgeModel extends Model<EdgeModel.EdgeModelEvent> {
	private int           videoWidth;
	private int           videoHeight;
	private double        cannyRadius;
	private double        cannyRadiusMin;
	private double        cannyRadiusMax;
	private double        cannyThresholdOne;
	private double        cannyThresholdOneMax;
	private double        cannyThresholdTwo;
	private double        cannyThresholdTwoMax;
	private int           sliderMultiplier;
	private BufferedImage edgeImage;

	/**
	 * Initialisation of all values
	 */
	public EdgeModel() {
		super(EdgeModel.EdgeModelEvent.values());

		sliderMultiplier = 100;

		videoWidth = 200;
		videoHeight = 200;

		cannyRadius = 1;
		cannyRadiusMin = 1;
		cannyRadiusMax = 30;

		cannyThresholdOne = 50;
		cannyThresholdOneMax = 100;

		cannyThresholdTwo = cannyThresholdOne * cannyRadius;
		cannyThresholdTwoMax = cannyThresholdOneMax * cannyRadius;
	}

	/**
	 * @return The return value is the first max threshold for the Canny function
	 */
	public double getCannyThresholdOneMax() {
		return getCannyThresholdOneMax(false);
	}

	/**
	 * @param withMultiplier if a multiplier should be used
	 * @return The return value is the first max threshold for the Canny function (with a multiplier)
	 */
	public double getCannyThresholdOneMax(boolean withMultiplier) {
		if(withMultiplier) {
			return cannyThresholdOneMax * sliderMultiplier;
		}
		return cannyThresholdOneMax;
	}

	/**
	 * @param cannyThresholdOneMax The value represents the new value of the first max threshold for the Canny function
	 */
	private void setCannyThresholdOneMax(double cannyThresholdOneMax) {
		setCannyThresholdOneMax(cannyThresholdOneMax, false);
	}

	/**
	 * @param cannyThresholdOneMax The value represents the new value of the first max threshold for the Canny function
	 * @param withMultiplier       if a multiplier should be used
	 */
	private void setCannyThresholdOneMax(double cannyThresholdOneMax, boolean withMultiplier) {
		if(withMultiplier) {
			cannyThresholdOneMax /= sliderMultiplier;
		}
		this.cannyThresholdOneMax = cannyThresholdOneMax * cannyRadius;
		fireModelEvent(EdgeModelEvent.CAN_THR_ONE_MAX);
	}

	/**
	 * @return The return value is the second max threshold for the Canny function
	 */
	public double getCannyThresholdTwoMax() {
		return getCannyThresholdTwoMax(false);
	}

	/**
	 * @param withMultiplier if a multiplier should be used
	 * @return The return value is the second max threshold for the Canny function (with a multiplier)
	 */
	public double getCannyThresholdTwoMax(boolean withMultiplier) {
		if(withMultiplier) {
			return cannyThresholdTwoMax * sliderMultiplier;
		}
		return cannyThresholdTwoMax;
	}

	/**
	 * @param cannyThresholdTwoMax The value represents the new value of the second max threshold for the Canny function
	 */
	public void setCannyThresholdTwoMax(double cannyThresholdTwoMax) {
		setCannyThresholdOneMax(cannyThresholdTwoMax, false);
	}

	/**
	 * @param cannyThresholdTwoMax The value represents the new value of the second max threshold for the Canny function
	 * @param withMultiplier       if a multiplier should be used
	 */
	public void setCannyThresholdTwoMax(double cannyThresholdTwoMax, boolean withMultiplier) {
		if(withMultiplier) {
			cannyThresholdTwoMax /= sliderMultiplier;
		}
		this.cannyThresholdTwoMax = cannyThresholdTwoMax * cannyRadius;
		fireModelEvent(EdgeModelEvent.CAN_THR_TWO_MAX);
	}

	/**
	 * @return The return value is the first threshold for the Canny function
	 */
	public double getCannyThresholdOne() {
		return getCannyThresholdOne(false);
	}

	/**
	 * @param withMultiplier if a multiplier should be used
	 * @return The return value is the first threshold for the Canny function (with a multiplier)
	 */
	public double getCannyThresholdOne(boolean withMultiplier) {
		if(withMultiplier) {
			return cannyThresholdOne * sliderMultiplier;
		}
		return cannyThresholdOne;
	}

	/**
	 * @param cannyThresholdOne The value represents the new value of the first threshold for the Canny function
	 */
	public void setCannyThresholdOne(double cannyThresholdOne) {
		setCannyThresholdOne(cannyThresholdOne, false);
	}

	/**
	 * @param cannyThresholdOne The value represents the new value of the first threshold for the Canny function
	 * @param withMultiplier    if a multiplier should be used
	 */
	public void setCannyThresholdOne(double cannyThresholdOne, boolean withMultiplier) {
		if(withMultiplier) {
			cannyThresholdOne /= sliderMultiplier;
		}
		this.cannyThresholdOne = cannyThresholdOne;
		fireModelEvent(EdgeModelEvent.CAN_THR_ONE);
	}

	/**
	 * @return The return value is the second threshold for the Canny function
	 */
	public double getCannyThresholdTwo() {
		return getCannyThresholdTwo(false);
	}

	/**
	 * @param withMultiplier if a multiplier should be used
	 * @return The return value is the second threshold for the Canny function (with a multiplier)
	 */
	public double getCannyThresholdTwo(boolean withMultiplier) {
		if(withMultiplier) {
			return cannyThresholdTwo * sliderMultiplier;
		}
		return cannyThresholdTwo;
	}

	/**
	 * @param cannyThresholdTwo The value represents the new value of the second threshold for the Canny function
	 */
	public void setCannyThresholdTwo(double cannyThresholdTwo) {
		setCannyThresholdTwo(cannyThresholdTwo, false);
	}

	/**
	 * @param cannyThresholdTwo The value represents the new value of the second threshold for the Canny function
	 * @param withMultiplier    if a multiplier should be used
	 */
	public void setCannyThresholdTwo(double cannyThresholdTwo, boolean withMultiplier) {
		if(withMultiplier) {
			cannyThresholdTwo /= sliderMultiplier;
		}
		this.cannyThresholdTwo = cannyThresholdTwo;
		fireModelEvent(EdgeModelEvent.CAN_THR_TWO);
	}

	/**
	 * @return The return value is the width of the video frame
	 */
	public int getVideoWidth() {
		return videoWidth;
	}

	/**
	 * @param videoWidth The value represents the new value of the video frame width
	 */
	public void setVideoWidth(int videoWidth) {
		this.videoWidth = videoWidth;
		fireModelEvent(EdgeModelEvent.V_WIDTH);
	}

	/**
	 * @return The return value is the height of the video frame
	 */
	public int getVideoHeight() {
		return videoHeight;
	}

	/**
	 * @param videoHeight The value represents the new value of the video frame height
	 */
	public void setVideoHeight(int videoHeight) {
		this.videoHeight = videoHeight;
		fireModelEvent(EdgeModelEvent.V_HEIGHT);
	}

	/**
	 * @return The return value is the result of the Canny function (edge detection)
	 */
	public BufferedImage getEdgeImage() {
		return edgeImage;
	}

	/**
	 * @param edgeImage The value represents the new BufferedImage from teh edge detection
	 */
	public void setEdgeImage(BufferedImage edgeImage) {
		this.edgeImage = edgeImage;
		fireModelEvent(EdgeModelEvent.EDGE_IMG);
	}

	/**
	 * @return The return value is the blur radius for the Canny function
	 */
	public double getCannyRadius() {
		return getCannyRadius(false);
	}

	/**
	 * @param withMultiplier if a multiplier should be used
	 * @return The return value is the blur radius for the Canny function (with a multiplier)
	 */
	public double getCannyRadius(boolean withMultiplier) {
		if(withMultiplier) {
			return cannyRadius * sliderMultiplier;
		}
		return cannyRadius;
	}

	/**
	 * @param cannyRadius The value represents the new value for the blur radius of the Canny function - the max value
	 *                    for the second threshold is adjusted as well
	 */
	public void setCannyRadius(double cannyRadius) {
		setCannyRadius(cannyRadius, false);
	}

	/**
	 * @param cannyRadius    The value represents the new value for the blur radius of the Canny function - the max value
	 *                       for the second threshold is adjusted as well
	 * @param withMultiplier if a multiplier should be used
	 */
	public void setCannyRadius(double cannyRadius, boolean withMultiplier) {
		if(withMultiplier) {
			cannyRadius /= sliderMultiplier;
		}
		this.cannyRadius = cannyRadius;
		setCannyThresholdTwoMax(cannyThresholdOneMax);
		fireModelEvent(EdgeModelEvent.CAN_RADIUS);
	}

	/**
	 * @return The return value is the min blur radius for the Canny function
	 */
	public double getCannyRadiusMin() {
		return getCannyRadiusMin(false);
	}

	/**
	 * @param withMultiplier if a multiplier should be used
	 * @return The return value is the min blur radius for the Canny function (with a multiplier)
	 */
	public double getCannyRadiusMin(boolean withMultiplier) {
		if(withMultiplier) {
			return cannyRadiusMin * sliderMultiplier;
		}
		return cannyRadiusMin;
	}

	/**
	 * @return The return value is the max blur radius for the Canny function
	 */
	public double getCannyRadiusMax() {
		return getCannyRadiusMax(false);
	}

	/**
	 * @param withMultiplier if a multiplier should be used
	 * @return The return value is the max blur radius (with a multiplier) for the Canny function
	 */
	public double getCannyRadiusMax(boolean withMultiplier) {
		if(withMultiplier) {
			return cannyRadiusMax * sliderMultiplier;
		}
		return cannyRadiusMax;
	}

	/**
	 * Enum list used for the events of the EdgeModel
	 */
	public enum EdgeModelEvent {
		V_WIDTH, V_HEIGHT, EDGE_IMG, CAN_RADIUS, CAN_RADIUS_MAX, CAN_THR_ONE, CAN_THR_ONE_MAX, CAN_THR_TWO,
		CAN_THR_TWO_MAX
	}
}
