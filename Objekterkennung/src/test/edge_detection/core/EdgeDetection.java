package test.edge_detection.core;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import test.edge_detection.gui.EdgeModel;
import test.edge_detection.objects.gui.ModelEvent;
import test.edge_detection.objects.gui.ModelEventListener;

import java.awt.image.BufferedImage;

/**
 * The class processes an Bufferd image obtained form the webcam and sets the BufferedImage in the Model to a black &
 * white image that shows edges of objects
 */
public class EdgeDetection implements Runnable {
	private EdgeModel model;
	private Mat       tempOne;
	private Mat       tempTwo;
	private Size      blurRadius;

	/**
	 * @param m The parameter represents the EdgeModel the object gets information for the image processing from and writes
	 *          the processed image back to
	 */
	public EdgeDetection(EdgeModel m) {
		model = m;
		tempOne = new Mat();
		tempTwo = new Mat();
		blurRadius = new Size(m.getCannyRadius(), m.getCannyRadius());

		//Listener
		model.addModelEventListener(EdgeModel.EdgeModelEvent.CAN_RADIUS, new ModelEventListener() {
			@Override public void actionPerformed(ModelEvent event) {
				blurRadius.height = model.getCannyRadius();
				blurRadius.width = model.getCannyRadius();
			}
		});
	}

	/**
	 * Method that will be executed by a Thread
	 */
	@Override public void run() {
		VideoCapture capture = new VideoCapture(0);
		Mat camFrame = new Mat();

		if(capture.isOpened()) {
			do {
				Thread.yield(); //Waits until the captured frame is not null
				capture.read(camFrame);
			} while(camFrame.empty());

			model.setVideoWidth(camFrame.width());   //tells the model which size the video frames will have
			model.setVideoHeight(camFrame.height());

			while(true) {
				capture.read(camFrame);
				if(!camFrame.empty()) {
					model.setEdgeImage(matToBufferedImage(edgeDetect(camFrame)));
				} else {
					System.err.println(" --(!) No captured frame -- Break!");
					break;
				}
			}
		} else {
			System.err.println("Camera is not open!");
		}
	}

	/**
	 *
	 * @param matrix The parameter determines the Matrix that will be converted to a Buffered Image
	 * @return The return value is a BufferedImage with the information of the matrix
	 */
	public static BufferedImage matToBufferedImage(Mat matrix) {
		int cols = matrix.cols();
		int rows = matrix.rows();
		int elemSize = (int) matrix.elemSize();
		byte[] data = new byte[cols * rows * elemSize];
		int type;
		matrix.get(0, 0, data);
		switch(matrix.channels()) {
			case 1:
				type = BufferedImage.TYPE_BYTE_GRAY;
				break;
			case 3:
				type = BufferedImage.TYPE_3BYTE_BGR;
				// bgr to rgb
				byte b;
				for(int i = 0; i < data.length; i = i + 3) {
					b = data[i];
					data[i] = data[i + 2];
					data[i + 2] = b;
				}
				break;
			default:
				return null;
		}
		BufferedImage image2 = new BufferedImage(cols, rows, type);
		image2.getRaster().setDataElements(0, 0, cols, rows, data);
		return image2;
	}

	/**
	 * @param camFrame The parameter determines the matrix to be processed to a black and white matrix that shows edges of objects
	 * @return The return value is a Black and white Matrix where white represents an edge of an object
	 */
	//Source @ http://docs.opencv.org/doc/tutorials/imgproc/imgtrans/canny_detector/canny_detector.html#canny-detector
	Mat edgeDetect(Mat camFrame) {
		Imgproc.cvtColor(camFrame, tempOne, Imgproc.COLOR_BGR2GRAY);   //Convert to grey
		Imgproc.blur(tempOne, tempTwo, blurRadius);                    //Blur
		Imgproc.Canny(tempTwo, tempTwo, model.getCannyThresholdOne(), model.getCannyThresholdTwo());//edge detection
		return tempTwo;
	}
}
