package de.xtion.drone;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import de.xtion.drone.interfaces.NavController;
import de.xtion.drone.interfaces.OBJController;

public class ColorOBJController implements OBJController {

	private static final Scalar YELLOW_UPPER = new Scalar(30, 255, 255);
	private static final Scalar YELLOW_LOWER = new Scalar(20, 100, 100);

	static {
		System.loadLibrary("opencv_java246");
	}

	private ArrayList<NavController> navControllers = new ArrayList<NavController>();
	private Scalar lowerb;
	private Scalar upperb;

	public ColorOBJController() {
		setLowerb(YELLOW_LOWER);
		setUpperb(YELLOW_UPPER);
	}

	@Override
	public void addNavController(NavController contr) {
		navControllers.add(contr);
	}

	@Override
	public void processImage(BufferedImage data) {
		
	}

	private Mat getTherehold(Mat webcamImage) {
		Mat mat = new Mat();
		webcamImage.copyTo(mat);
		Imgproc.cvtColor(webcamImage, mat, Imgproc.COLOR_BGR2HSV);
		
		Mat ret = new Mat();
		mat.copyTo(ret);
		
		Core.inRange(mat, getLowerb(), getUpperb(), ret);
		calculateMoments(ret);
		return ret;
	}

	private void calculateMoments(Mat ret) {
		Moments moments2 = Imgproc.moments(ret, true);
		double m10 = moments2.get_m10();
		double m01 = moments2.get_m01();
		double area = moments2.get_m00();
		
		int x = (int) (m10 / area);
		int y = (int) (m01 / area);
	}

	public Scalar getLowerb() {
		return lowerb;
	}

	public void setLowerb(Scalar lowerb) {
		this.lowerb = lowerb;
	}

	public Scalar getUpperb() {
		return upperb;
	}

	public void setUpperb(Scalar upperb) {
		this.upperb = upperb;
	}
}
