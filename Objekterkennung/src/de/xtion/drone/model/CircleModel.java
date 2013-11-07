package de.xtion.drone.model;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

import de.xtion.drone.model.util.Model;

/**
 * Model of the GUI.
 * The class provides all GUI objects with information and events - it's pretty "blown up" because of the getter and
 * setter and an ugly workaround of integer restriction in sliders - nothing special happens here.
 */
public class CircleModel extends Model<CircleModel.CircleModelEvent> {
	private int sliderMultiplier;
	private BufferedImage circleImage;
	private Mat circles;
	private double dp;
	private int circleMaxSize;
	private int circleMinSize;

	/**
	 * Initialisation of all values
	 */
	public CircleModel() {
		super(CircleModel.CircleModelEvent.values());
		sliderMultiplier = 100;
		dp = 2d;
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

	/**
	 * Enum list used for the events of the EdgeModel
	 */
	public enum CircleModelEvent {
		 CIRCLE_IMG, CIRCLES, DP, CIRCLE_MAX_SIZE, CIRCLE_MIN_SIZE
	}

	public void setCircles(Mat circles) {
		this.circles = circles;
		fireModelEvent(CircleModelEvent.CIRCLES);
	}
	
	public Mat getCircles() {
		return circles;
	}

	public double getDp() {
		return dp;
	}
	
	public void setDouble(double dp){
		this.dp = dp;
		fireModelEvent(CircleModelEvent.DP);
	}

	public void setCircleMinSize(int minSize){
		circleMinSize = minSize;
		fireModelEvent(CircleModelEvent.CIRCLE_MIN_SIZE);
	}

	public void setCircleMaxSize(int dp){
		circleMaxSize = dp;
		fireModelEvent(CircleModelEvent.CIRCLE_MAX_SIZE);
	}

	public int getCircleMaxSize() {
		return circleMaxSize;
	}

	public int getCircleMinSize() {
		return circleMinSize;
	}
	
	
}
