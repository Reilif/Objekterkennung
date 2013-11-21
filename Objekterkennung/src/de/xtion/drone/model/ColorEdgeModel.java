package de.xtion.drone.model;

import de.xtion.drone.model.util.Model;
import org.opencv.core.Scalar;

import java.awt.image.BufferedImage;

public class ColorEdgeModel extends Model<ColorEdgeModel.ColorEdgeModelEvent> {
	public final int           sliderMultiplier;
	private      BufferedImage colorEdgeImage;
	private      Scalar        lowerThreshold;
	private      Scalar        upperThreshold;
	private      EdgePosition  edgePosition;
	private      int           hLower;
	private      int           sLower;
	private      int           vLower;
	private      int           hUpper;
	private      int           sUpper;
	private      int           vUpper;
	private      int           hMin;
	private      int           sMin;
	private      int           vMin;
	private      int           hMax;
	private      int           sMax;
	private      int           vMax;
	private      int           topBorder;
	private      int           topX;
	private      int           topY;
	private      int           rightBorder;
	private      int           rightX;
	private      int           rightY;
	private      int           bottomBorder;
	private      int           bottomX;
	private      int           bottomY;
	private      int           leftBorder;
	private      int           leftX;
	private      int           leftY;
	private      int           maxX;
	private      int           maxY;
	private      double        colorThreshold;
	private      int           colorThresholdMin;
	private      int           colorThresholdMax;

	public ColorEdgeModel() {
		super(ColorEdgeModelEvent.values());
		sliderMultiplier = 10000;

		//value of lower
		hLower = 40;
		sLower = 48;
		vLower = 30;

		//value of upper
		hUpper = 82;
		sUpper = 230;
		vUpper = 255;

		//Min slider values
		hMin = 0;
		sMin = 0;
		vMin = 0;

		//Max slider values
		hMax = 180;
		sMax = 255;
		vMax = 255;

		topBorder = 50;
		topX = 0;
		topY = 0;

		rightBorder = 50;
		rightX = 0;
		rightY = 0;

		bottomBorder = 50;
		bottomX = 0;
		bottomY = 0;

		leftBorder = 50;
		leftX = 0;
		leftY = 0;

		maxX = 800;
		maxY = 800;

		colorThreshold = 5;
		colorThresholdMin = 0;
		colorThresholdMax = 100;

		//Scalars
		lowerThreshold = new Scalar(hLower, sLower, vLower);
		upperThreshold = new Scalar(hUpper, sUpper, vUpper);
	}

	public void setColorThreshold(double colorThreshold, boolean multiplier) {
		if(multiplier) {
			setColorThreshold(colorThreshold / sliderMultiplier);
		} else {
			setColorThreshold(colorThreshold);
		}
	}

	public int getColorThresholdMin(boolean multiplier) {
		if(multiplier) {
			return colorThresholdMin * sliderMultiplier;
		}
		return colorThresholdMin;
	}

	public int getColorThresholdMax(boolean multiplier) {
		if(multiplier) {
			return colorThresholdMax * sliderMultiplier;
		}
		return colorThresholdMax;
	}

	public double getColorThreshold(boolean multiplier) {
		if(multiplier) {
			return colorThreshold * sliderMultiplier;
		}
		return colorThreshold;
	}

	public int getColorThresholdMin() {
		return colorThresholdMin;
	}

	public int getColorThresholdMax() {
		return colorThresholdMax;
	}

	public double getColorThreshold() {
		return colorThreshold;
	}

	public void setColorThreshold(double colorThreshold) {
		this.colorThreshold = colorThreshold;
	}

	public int getTopX() {
		return topX;
	}

	public void setTopX(int topX) {
		this.topX = topX;
	}

	public int getTopY() {
		return topY;
	}

	public void setTopY(int topY) {
		this.topY = topY;
	}

	public int getRightX() {
		return rightX;
	}

	public void setRightX(int rightX) {
		this.rightX = rightX;
	}

	public int getRightY() {
		return rightY;
	}

	public void setRightY(int rightY) {
		this.rightY = rightY;
	}

	public int getBottomX() {
		return bottomX;
	}

	public void setBottomX(int bottomX) {
		this.bottomX = bottomX;
	}

	public int getBottomY() {
		return bottomY;
	}

	public void setBottomY(int bottomY) {
		this.bottomY = bottomY;
	}

	public int getLeftX() {
		return leftX;
	}

	public void setLeftX(int leftX) {
		this.leftX = leftX;
	}

	public int getLeftY() {
		return leftY;
	}

	public void setLeftY(int leftY) {
		this.leftY = leftY;
	}

	public int getTopBorder() {
		return topBorder;
	}

	public void setTopBorder(int topBorder) {
		this.topBorder = topBorder;
	}

	public int getRightBorder() {
		return rightBorder;
	}

	public void setRightBorder(int rightBorder) {
		this.rightBorder = rightBorder;
	}

	public int getLeftBorder() {
		return leftBorder;
	}

	public void setLeftBorder(int leftBorder) {
		this.leftBorder = leftBorder;
	}

	public int getBottomBorder() {
		return bottomBorder;
	}

	public void setBottomBorder(int bottomBorder) {
		this.bottomBorder = bottomBorder;
	}

	public int gethMin() {
		return hMin;
	}

	public int getsMin() {
		return sMin;
	}

	public int getvMin() {
		return vMin;
	}

	public int gethMax() {
		return hMax;
	}

	public int getsMax() {
		return sMax;
	}

	public int getvMax() {
		return vMax;
	}

	public int gethLower() {
		return hLower;
	}

	public void sethLower(int hLower) {
		this.hLower = hLower;
		calculateLowerScalar();
	}

	private void calculateLowerScalar() {
		lowerThreshold.set(new double[]{hLower, sLower, vLower});
		fireModelEvent(ColorEdgeModelEvent.LOWER_THR);
	}

	public int getsLower() {
		return sLower;
	}

	public void setsLower(int sLower) {
		this.sLower = sLower;
		calculateLowerScalar();
	}

	public int getvLower() {
		return vLower;
	}

	public void setvLower(int vLower) {
		this.vLower = vLower;
		calculateLowerScalar();
	}

	public int gethUpper() {
		return hUpper;
	}

	public void sethUpper(int hUpper) {
		this.hUpper = hUpper;
		calculateUpperScalar();
	}

	private void calculateUpperScalar() {
		upperThreshold.set(new double[]{hUpper, sUpper, vUpper});
		fireModelEvent(ColorEdgeModelEvent.UPPER_THR);
	}

	public int getsUpper() {
		return sUpper;
	}

	public void setsUpper(int sUpper) {
		this.sUpper = sUpper;
		calculateUpperScalar();
	}

	public int getvUpper() {
		return vUpper;
	}

	public void setvUpper(int vUpper) {
		this.vUpper = vUpper;
		calculateUpperScalar();
	}

	public Scalar getLowerThreshold() {
		return lowerThreshold;
	}

	public void setLowerThreshold(Scalar lowerThreshold) {
		this.lowerThreshold = lowerThreshold;
		fireModelEvent(ColorEdgeModelEvent.LOWER_THR);
	}

	public Scalar getUpperThreshold() {
		return upperThreshold;
	}

	public void setUpperThreshold(Scalar upperThreshold) {
		this.upperThreshold = upperThreshold;
		fireModelEvent(ColorEdgeModelEvent.UPPER_THR);
	}

	public BufferedImage getColorEdgeImage() {
		return colorEdgeImage;
	}

	public void setColorEdgeImage(BufferedImage colorEdgeImage) {
		this.colorEdgeImage = colorEdgeImage;
		fireModelEvent(ColorEdgeModelEvent.COLOR_EDGE_IMAGE);
	}

	public EdgePosition getEdgePosition() {
		return edgePosition;
	}

	public void setEdgePosition(EdgePosition edgePosition) {
		this.edgePosition = edgePosition;
		fireModelEvent(ColorEdgeModelEvent.COLOR_EDGE_POS);
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
		fireModelEvent(ColorEdgeModelEvent.MAX_X);
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
		fireModelEvent(ColorEdgeModelEvent.MAX_Y);
	}

	public enum ColorEdgeModelEvent {
		COLOR_EDGE_IMAGE, LOWER_THR, UPPER_THR, COLOR_EDGE_POS, MAX_X, MAX_Y
	}

	public enum EdgePosition {
		TOP, TOP_RIGHT, RIGHT, RIGHT_BOTTOM, BOTTOM, BOTTOM_LEFT, LEFT, TOP_LEFT, CENTER
	}
}
