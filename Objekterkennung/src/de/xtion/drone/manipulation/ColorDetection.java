package de.xtion.drone.manipulation;

import java.awt.image.BufferedImage;

import de.xtion.drone.gui.WhiteBalances;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import de.xtion.drone.interfaces.NavController;
import de.xtion.drone.interfaces.OBJController;
import de.xtion.drone.model.ColorModel;
import de.xtion.drone.utils.ImageUtils;

public class ColorDetection implements OBJController {
	private ColorModel model;
	Mat    tempOne;
	Mat    tempTwo;
	double m10;
	double m01;
	double area;

	public ColorDetection(ColorModel model) {
		this.model = model;
		tempOne = new Mat();
		tempTwo = new Mat();
	}

	@Override public void addNavController(NavController contr) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override public void processImage(BufferedImage data) {
		Mat camFrame = ImageUtils.bufferedImageToMat(data);
		calculate(camFrame);
	}

	public void calculate(Mat camFrame) {
		Imgproc.cvtColor(camFrame, tempOne, Imgproc.COLOR_BGR2HSV);

		Core.inRange(tempOne, model.getLowerThreshold(), model.getUpperThreshold(), tempTwo);

		Moments moments2 = Imgproc.moments(tempTwo, true);
		m10 = moments2.get_m10();
		m01 = moments2.get_m01();
		area = moments2.get_m00();

		model.setMassCenterX(m10 / area);
		model.setMassCenterY(m01 / area);
		model.setColorImage(tempTwo);
	}
}
