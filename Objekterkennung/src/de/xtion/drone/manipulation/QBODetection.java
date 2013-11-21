package de.xtion.drone.manipulation;

import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import de.xtion.drone.model.CircleModel;
import de.xtion.drone.model.ColorModel;
import de.xtion.drone.model.EdgeModel;
import de.xtion.drone.utils.ImageUtils;

public class QBODetection {

	private static final int RAND = 100;
	private CircleModel circleM;
	private ColorModel cm;
	private EdgeModel em;

	public QBODetection(ColorModel cm, CircleModel circleM, EdgeModel em) {
		this.cm = cm;
		this.circleM = circleM;
		this.em = em;
	}

	public QboDirection getQBODetect(BufferedImage buf) {
		if (buf != null) {
			Mat camFrame = ImageUtils.bufferedImageToMat(buf);

			Imgproc.cvtColor(camFrame, camFrame, Imgproc.COLOR_BGR2HSV);
			Core.inRange(camFrame, cm.getLowerThreshold(),
					cm.getUpperThreshold(), camFrame);

			Mat circles = new Mat();

			Imgproc.HoughCircles(camFrame, circles, Imgproc.CV_HOUGH_GRADIENT,
					circleM.getDensity(), camFrame.cols() / 4,
					em.getCannyThresholdTwo(), em.getCannyThresholdOne(),
					circleM.getCircleMinSize(), circleM.getCircleMaxSize());

			if (circles.cols() == 1) {
				double vCircle[] = circles.get(0, 0);

				if (vCircle == null)
					return QboDirection.NOF;
				
				Point pt = new Point(Math.round(vCircle[0]),
						Math.round(vCircle[1]));

				if (camFrame.height() - RAND < pt.y) {
					if (camFrame.width() - RAND < pt.x) {
						return QboDirection.SO;
					} else if (pt.x < RAND) {
						return QboDirection.SW;
					}else{
						return QboDirection.S;
					}
				} else if (pt.y < RAND) {
					if (camFrame.width() - RAND < pt.x) {
						return QboDirection.NO;
					} else if (pt.x < RAND) {
						return QboDirection.NW;
					}else{
						return QboDirection.N;
					}
				} else if (camFrame.width() - RAND < pt.x) {
					return QboDirection.O;
				} else if (pt.x < RAND) {
					return QboDirection.W;
				} else {
					return QboDirection.CENTER;
				}
			}
		}
		return QboDirection.NOF;
	}
}
