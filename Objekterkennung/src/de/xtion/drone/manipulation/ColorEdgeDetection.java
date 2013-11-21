package de.xtion.drone.manipulation;

import de.xtion.drone.gui.WhiteBalances;
import de.xtion.drone.interfaces.NavController;
import de.xtion.drone.interfaces.OBJController;
import de.xtion.drone.model.ColorEdgeModel;
import de.xtion.drone.utils.ImageUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.awt.image.BufferedImage;

public class ColorEdgeDetection implements OBJController {
	private ColorEdgeModel model;

	public ColorEdgeDetection(ColorEdgeModel model) {
		this.model = model;
	}

	@Override public void addNavController(NavController contr) {
		//To change body of implemented methods use File | Settings | File Templates.
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

		Core.inRange(temp, model.getLowerThreshold(), model.getUpperThreshold(), camFrame);
		Core.line(camFrame, new Point(0, model.getTopBorder()), new Point(camFrame.width(), model.getTopBorder()),
		          new Scalar(255));
		Core.line(camFrame, new Point(camFrame.width() - model.getRightBorder(), 0),
		          new Point(camFrame.width() - model.getRightBorder(), camFrame.height()), new Scalar(255));
		Core.line(camFrame, new Point(0, camFrame.height() - model.getBottomBorder()),
		          new Point(camFrame.width(), camFrame.height() - model.getBottomBorder()), new Scalar(255));
		Core.line(camFrame, new Point(model.getLeftBorder(), 0), new Point(model.getLeftBorder(), camFrame.height()),
		          new Scalar(255));
		model.setColorEdgeImage(ImageUtils.matToBufferedImage(camFrame));
	}

	private void calculateTop(Mat mat) {
		Mat piece = mat.submat(0, model.getTopBorder(), 0, mat.width());
		Mat bin = new Mat();
		int pixCount = piece.height() * piece.width();

		Core.inRange(piece, model.getLowerThreshold(), model.getUpperThreshold(), bin);
		Moments moments = Imgproc.moments(bin, true);

		if(100 * moments.get_m00() / pixCount > model.getColorThreshold()) {

			model.setTopX((int) (moments.get_m10() / moments.get_m00()));
			model.setTopY((int) (moments.get_m01() / moments.get_m00()));
		} else {
			model.setTopX(model.getMaxX() / 2);
			model.setTopY(model.getMaxY() / 2);
		}
	}

	private void calculateRight(Mat mat) {
		Mat piece = mat.submat(0, mat.height(), mat.width() - model.getRightBorder(), mat.width());
		Mat bin = new Mat();
		int pixCount = piece.height() * piece.width();

		Core.inRange(piece, model.getLowerThreshold(), model.getUpperThreshold(), bin);
		Moments moments = Imgproc.moments(bin, true);

		if(100 * moments.get_m00() / pixCount > model.getColorThreshold()) {

			model.setRightX(model.getMaxX() - model.getRightBorder() + (int) (moments.get_m10() / moments.get_m00()));
			model.setRightY((int) (moments.get_m01() / moments.get_m00()));
		} else {
			model.setRightX(model.getMaxX() / 2);
			model.setRightY(model.getMaxY() / 2);
		}
	}

	private void calculateBottom(Mat mat) {
		Mat piece = mat.submat(mat.height() - model.getBottomBorder(), mat.height(), 0, mat.width());
		Mat bin = new Mat();
		int pixCount = piece.height() * piece.width();

		Core.inRange(piece, model.getLowerThreshold(), model.getUpperThreshold(), bin);
		Moments moments = Imgproc.moments(bin, true);

		if(100 * moments.get_m00() / pixCount > model.getColorThreshold()) {

			model.setBottomX((int) (moments.get_m10() / moments.get_m00()));
			model.setBottomY(model.getMaxY() - model.getBottomBorder() + (int) (moments.get_m01() / moments.get_m00()));
		} else {
			model.setBottomX(model.getMaxX() / 2);
			model.setBottomY(model.getMaxY() / 2);
		}
	}

	private void calculateLeft(Mat mat) {
		Mat piece = mat.submat(0, mat.height(), 0, model.getLeftBorder());
		Mat bin = new Mat();
		int pixCount = piece.height() * piece.width();

		Core.inRange(piece, model.getLowerThreshold(), model.getUpperThreshold(), bin);
		Moments moments = Imgproc.moments(bin, true);

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
				model.setEdgePosition(ColorEdgeModel.EdgePosition.TOP_RIGHT);
			} else if(model.getTopX() < model.getLeftBorder()) {
				model.setEdgePosition(ColorEdgeModel.EdgePosition.TOP_LEFT);
			} else {
				model.setEdgePosition(ColorEdgeModel.EdgePosition.TOP);
			}
		} else if(model.getRightX() > model.getMaxX() - model.getRightBorder()) {
			if(model.getBottomX() > model.getMaxX() - model.getRightBorder()) {
				model.setEdgePosition(ColorEdgeModel.EdgePosition.RIGHT_BOTTOM);
			} else {
				model.setEdgePosition(ColorEdgeModel.EdgePosition.RIGHT);
			}
		} else if(model.getBottomY() > model.getMaxY() - model.getBottomBorder()) {
			if(model.getBottomX() < model.getLeftBorder()) {
				model.setEdgePosition(ColorEdgeModel.EdgePosition.BOTTOM_LEFT);
			} else {
				model.setEdgePosition(ColorEdgeModel.EdgePosition.BOTTOM);
			}
		} else if(model.getLeftX() < model.getLeftBorder()) {
			model.setEdgePosition(ColorEdgeModel.EdgePosition.LEFT);
		} else {
			model.setEdgePosition(ColorEdgeModel.EdgePosition.CENTER);
		}
	}
}
