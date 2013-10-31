package de.xtion.drone.model;

import de.xtion.drone.model.util.Model;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class ColorModel extends Model<ColorModel.ColorModelEvents> {
	private Scalar lowerThreshold;
	private Scalar upperThreshold;
	private double massCenterX;
	private double massCenterY;
	private Mat    colorImage;
	private int    hLower;
	private int    sLower;
	private int    vLower;
	private int    hUpper;
	private int    sUpper;
	private int    vUpper;
	private int    hMin;
	private int    sMin;
	private int    vMin;
	private int    hMax;
	private int    sMax;
	private int    vMax;

	public ColorModel() {
		super(ColorModelEvents.values());

		//Min slider values
		hMin = 0;
		sMin = 0;
		vMin = 0;

		//Max slider values
		hMax = 180;
		sMax = 255;
		vMax = 255;

		//value of lower
		hLower = 0;
		sLower = 56;
		vLower = 101;

		//value of upper
		hUpper = 25;
		sUpper = 122;
		vUpper = 255;

		//Scalars
		lowerThreshold = new Scalar(hLower, sLower, vLower);
		upperThreshold = new Scalar(hUpper, sUpper, vUpper);
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
	}

	public int getsLower() {
		return sLower;
	}

	public void setsLower(int sLower) {
		this.sLower = sLower;
	}

	public int getvLower() {
		return vLower;
	}

	public void setvLower(int vLower) {
		this.vLower = vLower;
	}

	public int gethUpper() {
		return hUpper;
	}

	public void sethUpper(int hUpper) {
		this.hUpper = hUpper;
	}

	public int getsUpper() {
		return sUpper;
	}

	public void setsUpper(int sUpper) {
		this.sUpper = sUpper;
	}

	public int getvUpper() {
		return vUpper;
	}

	public void setvUpper(int vUpper) {
		this.vUpper = vUpper;
	}

	public Scalar getLowerThreshold() {
		return lowerThreshold;
	}

	public void setLowerThreshold(Scalar lowerThreshold) {
		this.lowerThreshold = lowerThreshold;
		fireModelEvent(ColorModelEvents.LOWER_THR);
	}

	public Scalar getUpperThreshold() {
		return upperThreshold;
	}

	public void setUpperThreshold(Scalar upperThreshold) {
		this.upperThreshold = upperThreshold;
		fireModelEvent(ColorModelEvents.UPPER_THR);
	}

	public double getMassCenterX() {
		return massCenterX;
	}

	public void setMassCenterX(double massCenterX) {
		this.massCenterX = massCenterX;
		fireModelEvent(ColorModelEvents.MASS_X);
	}

	public double getMassCenterY() {
		return massCenterY;
	}

	public void setMassCenterY(double massCenterY) {
		this.massCenterY = massCenterY;
		fireModelEvent(ColorModelEvents.MASS_Y);
	}

	public Mat getColorImage() {
		return colorImage;
	}

	public void setColorImage(Mat colorImage) {
		this.colorImage = colorImage;
		fireModelEvent(ColorModelEvents.COLOR_IMAGE);
	}

	public enum ColorModelEvents {
		COLOR_IMAGE, LOWER_THR, UPPER_THR, MASS_X, MASS_Y,
	}
}
