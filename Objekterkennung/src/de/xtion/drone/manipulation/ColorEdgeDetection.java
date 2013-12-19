package de.xtion.drone.manipulation;

import de.xtion.drone.interfaces.NavController;
import de.xtion.drone.interfaces.OBJController;
import de.xtion.drone.interfaces.PositionData;
import de.xtion.drone.model.ColorEdgeModel;
import de.xtion.drone.utils.ImageUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The ColorEdgeDetection searches for a certain color range in a Picture and
 * based on the amount the color is present in a certain area of the picture it
 * will set the position of the colored object.
 * <p/>
 * It reacts first to: <ol> <li>NorthEast</li> <li>NorthWest</li> <li>North</li>
 * <li>SouthEast</li> <li>East</li> <li>SouthWest</li> <li>Wast</li>
 * <li>South</li> </ol>
 * <p/>
 * If non of these directions apply, it is assumed that the object is centered
 */
public class ColorEdgeDetection implements OBJController {
	private ColorEdgeModel model;
	private Set<NavController> controller =
			Collections.synchronizedSet(new HashSet<NavController>());

	public ColorEdgeDetection(ColorEdgeModel model) {
		this.model = model;
	}

	@Override
	public void addNavController(NavController contr) {
		controller.add(contr);
	}

	@Override public void processImage(BufferedImage data) {
		Mat temp = new Mat();
		Mat camFrame = ImageUtils.bufferedImageToMat(data);

		Imgproc.cvtColor(camFrame, temp, Imgproc.COLOR_BGR2HSV);

		model.setMaxX(temp.width());
		model.setMaxY(temp.height());

		calculateTop(temp);
		calculateRight(temp);
		calculateBottom(temp);
		calculateLeft(temp);

		checkEdge();

		Core.inRange(temp, model.getLowerThreshold(), model.getUpperThreshold(),
		             camFrame);
		Core.line(camFrame, new Point(0, model.getTopBorder()),
		          new Point(camFrame.width(), model.getTopBorder()),
		          new Scalar(255));
		Core.line(camFrame,
		          new Point(camFrame.width() - model.getRightBorder(), 0),
		          new Point(camFrame.width() - model.getRightBorder(),
		                    camFrame.height()), new Scalar(255));
		Core.line(camFrame,
		          new Point(0, camFrame.height() - model.getBottomBorder()),
		          new Point(camFrame.width(),
		                    camFrame.height() - model.getBottomBorder()),
		          new Scalar(255));
		Core.line(camFrame, new Point(model.getLeftBorder(), 0),
		          new Point(model.getLeftBorder(), camFrame.height()),
		          new Scalar(255));
		model.setColorEdgeImage(ImageUtils.matToBufferedImage(camFrame));
	}

	/**
	 * If the there isn't enough mass in the part of the picture the location of
	 * the position is set to the center of the picture
	 *
	 * @param mat The value determines the Mat to analyse and apply the border
	 *            on
	 */
	private void calculateTop(Mat mat) {
		Mat piece = mat.submat(0, model.getTopBorder(), 0, mat.width());
		Mat bin = new Mat();
		int pixCount = piece.height() * piece.width();

		Core.inRange(piece, model.getLowerThreshold(),
		             model.getUpperThreshold(), bin);
		Moments moments = Imgproc.moments(bin, true);

		// m00 holds the total weight of the white pixel count.
		if(100 * moments.get_m00() / pixCount > model.getColorThreshold()) {
			model.setTopX((int) (moments.get_m10() / moments.get_m00()));
			model.setTopY((int) (moments.get_m01() / moments.get_m00()));
		} else {
			model.setTopX(model.getMaxX() / 2);
			model.setTopY(model.getMaxY() / 2);
		}
	}

	/**
	 * If the there isn't enough mass in the part of the picture the location of
	 * the position is set to the center of the picture
	 *
	 * @param mat The value determines the Mat to analyse and apply the border
	 *            on
	 */
	private void calculateRight(Mat mat) {
		Mat piece = mat.submat(0, mat.height(),
		                       mat.width() - model.getRightBorder(),
		                       mat.width());
		Mat bin = new Mat();
		int pixCount = piece.height() * piece.width();

		Core.inRange(piece, model.getLowerThreshold(),
		             model.getUpperThreshold(), bin);
		Moments moments = Imgproc.moments(bin, true);

		// m00 holds the total weight of the white pixel count.
		if(100 * moments.get_m00() / pixCount > model.getColorThreshold()) {
			model.setRightX(model.getMaxX() - model.getRightBorder() +
			                (int) (moments.get_m10() / moments.get_m00()));
			model.setRightY((int) (moments.get_m01() / moments.get_m00()));
		} else {
			model.setRightX(model.getMaxX() / 2);
			model.setRightY(model.getMaxY() / 2);
		}
	}

	/**
	 * If the there isn't enough mass in the part of the picture the location of
	 * the position is set to the center of the picture
	 *
	 * @param mat The value determines the Mat to analyse and apply the border
	 *            on
	 */
	private void calculateBottom(Mat mat) {
		Mat piece =
				mat.submat(mat.height() - model.getBottomBorder(), mat.height(),
				           0, mat.width());
		Mat bin = new Mat();
		int pixCount = piece.height() * piece.width();

		Core.inRange(piece, model.getLowerThreshold(),
		             model.getUpperThreshold(), bin);
		Moments moments = Imgproc.moments(bin, true);

		// m00 holds the total weight of the white pixel count.
		if(100 * moments.get_m00() / pixCount > model.getColorThreshold()) {
			model.setBottomX((int) (moments.get_m10() / moments.get_m00()));
			model.setBottomY(model.getMaxY() - model.getBottomBorder() +
			                 (int) (moments.get_m01() / moments.get_m00()));
		} else {
			model.setBottomX(model.getMaxX() / 2);
			model.setBottomY(model.getMaxY() / 2);
		}
	}

	/**
	 * If the there isn't enough mass in the part of the picture the location of
	 * the position is set to the center of the picture
	 *
	 * @param mat The value determines the Mat to analyse and apply the border
	 *            on
	 */
	private void calculateLeft(Mat mat) {
		Mat piece = mat.submat(0, mat.height(), 0, model.getLeftBorder());
		Mat bin = new Mat();
		int pixCount = piece.height() * piece.width();

		Core.inRange(piece, model.getLowerThreshold(),
		             model.getUpperThreshold(), bin);
		Moments moments = Imgproc.moments(bin, true);

		// m00 holds the total weight of the white pixel count.
		if(100.0 * moments.get_m00() / pixCount >= model.getColorThreshold()) {
			model.setLeftX((int) (moments.get_m10() / moments.get_m00()));
			model.setLeftY((int) (moments.get_m01() / moments.get_m00()));
		} else {
			model.setLeftX(model.getMaxX() / 2);
			model.setLeftY(model.getMaxY() / 2);
		}
	}

	private void checkEdge() {
		if(model.getTopY() < model.getTopBorder()) {
			if(model.getTopX() > model.getMaxX() - model.getRightBorder()) {
				model.setEdgePosition(QboDirection.NO);
				fire(QboDirection.NO);
			} else if(model.getTopX() < model.getLeftBorder()) {
				model.setEdgePosition(QboDirection.NW);
				fire(QboDirection.NW);
			} else {
				model.setEdgePosition(QboDirection.N);
				fire(QboDirection.N);
			}
		} else if(model.getRightX() >
		          model.getMaxX() - model.getRightBorder()) {
			if(model.getBottomX() > model.getMaxX() - model.getRightBorder()) {
				model.setEdgePosition(QboDirection.SO);
				fire(QboDirection.SO);
			} else {
				model.setEdgePosition(QboDirection.O);
				fire(QboDirection.O);
			}
		} else if(model.getBottomY() >
		          model.getMaxY() - model.getBottomBorder()) {
			if(model.getBottomX() < model.getLeftBorder()) {
				model.setEdgePosition(QboDirection.SW);
				fire(QboDirection.SW);
			} else {
				model.setEdgePosition(QboDirection.S);
				fire(QboDirection.S);
			}
		} else if(model.getLeftX() < model.getLeftBorder()) {
			model.setEdgePosition(QboDirection.W);
			fire(QboDirection.W);
		} else {
			model.setEdgePosition(QboDirection.CENTER);
			fire(QboDirection.CENTER);
		}
	}

	private void fire(PositionData right) {
		for(NavController nav : controller) {
			nav.setPosData(right);
		}
	}
}
