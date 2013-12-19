package de.xtion.drone.manipulation;

import de.xtion.drone.interfaces.NavController;
import de.xtion.drone.interfaces.OBJController;
import de.xtion.drone.model.EdgeModel;
import de.xtion.drone.model.util.ModelEvent;
import de.xtion.drone.model.util.ModelEventListener;
import de.xtion.drone.utils.ImageUtils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;

/**
 * The class processes an Bufferd image obtained form the webcam and sets the
 * BufferedImage in the Model to a black & white image that shows edges of
 * objects
 */
public class EdgeDetection implements OBJController {
	private EdgeModel model;
	private Mat       tempOne;
	private Mat       tempTwo;
	private Size      blurRadius;

	/**
	 * @param m The parameter represents the EdgeModel the object gets
	 *          information for the image processing from and writes the
	 *          processed image back to
	 */
	public EdgeDetection(EdgeModel m) {
		model = m;
		tempOne = new Mat();
		tempTwo = new Mat();
		blurRadius = new Size(m.getCannyRadius(), m.getCannyRadius());

		//Listener
		model.addModelEventListener(EdgeModel.EdgeModelEvent.CAN_RADIUS,
		                            new ModelEventListener() {
			                            @Override public void actionPerformed(
					                            ModelEvent event) {
				                            blurRadius.height =
						                            model.getCannyRadius();
				                            blurRadius.width =
						                            model.getCannyRadius();
			                            }
		                            });
	}

	/**
	 * @param data The parameter determines the matrix to be processed to a
	 *             black and white matrix that shows edges of objects
	 *
	 * @return The return value is a Black and white Matrix where white
	 * represents an edge of an object
	 */
	//Source @ http://docs.opencv.org/doc/tutorials/imgproc/imgtrans/canny_detector/canny_detector.html#canny-detector
	@Override public void processImage(BufferedImage data) {
		Mat camFrame = ImageUtils.bufferedImageToMat(data);
		calculate(camFrame, true);
	}

	public void calculate(Mat camFrame, boolean cvtColor) {
		if(cvtColor) {
			Imgproc.cvtColor(camFrame, tempOne,
			                 Imgproc.COLOR_BGR2GRAY);   //Convert to grey
		}
		Imgproc.blur(tempOne, tempTwo, blurRadius);                    //Blur
		Imgproc.Canny(tempTwo, tempTwo, model.getCannyThresholdOne(),
		              model.getCannyThresholdTwo());//edge detection

		model.setEdgeMat(tempTwo);
		model.setEdgeImage(ImageUtils.matToBufferedImage(tempTwo));
	}

	@Override public void addNavController(NavController contr) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
