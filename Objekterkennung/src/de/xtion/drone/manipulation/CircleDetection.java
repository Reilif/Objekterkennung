package de.xtion.drone.manipulation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import de.xtion.drone.interfaces.NavController;
import de.xtion.drone.interfaces.OBJController;
import de.xtion.drone.interfaces.PositionData;
import de.xtion.drone.interfaces.PositionData.PositionData2D;
import de.xtion.drone.model.CircleModel;
import de.xtion.drone.model.EdgeModel;
import de.xtion.drone.model.EdgeModel.EdgeModelEvent;
import de.xtion.drone.model.util.ModelEvent;
import de.xtion.drone.model.util.ModelEventListener;
import de.xtion.drone.utils.ImageUtils;

/**
 * The class processes an Bufferd image obtained form the webcam and sets the
 * BufferedImage in the Model to a black & white image that shows edges of
 * objects
 */
public class CircleDetection implements OBJController, Runnable{
	private static final int SLEEP_TIME = 100;
	private static final int RAND = 100;
	private CircleModel model;
	private Mat tempOne;
	private Mat tempTwo;
	private Size blurRadius;
	private Mat circles;
	private de.xtion.drone.model.ColorModel colorModel;
	private EdgeModel em;
	
	private Set<NavController> controller = Collections.synchronizedSet(new HashSet<NavController>());
	private BufferedImage image;

	/**
	 * @param m
	 *            The parameter represents the EdgeModel the object gets
	 *            information for the image processing from and writes the
	 *            processed image back to
	 */
	public CircleDetection(CircleModel m, de.xtion.drone.model.ColorModel cm, final EdgeModel em) {
		model = m;
		this.colorModel = cm;
		this.em = em; 
		tempOne = new Mat();
		tempTwo = new Mat();
		circles = new Mat();

		blurRadius = new Size(em.getCannyRadius(), em.getCannyRadius());

		new Thread(this).start();
		// Listener
		em.addModelEventListener(EdgeModelEvent.CAN_RADIUS,
				new ModelEventListener() {
					@Override
					public void actionPerformed(ModelEvent event) {
						blurRadius.height = em.getCannyRadius();
						blurRadius.width = em.getCannyRadius();
					}
				});
	}

	/**
	 *            The parameter determines the matrix to be processed to a black
	 *            and white matrix that shows edges of objects
	 * @return The return value is a Black and white Matrix where white
	 *         represents an edge of an object
	 */
	// Source @
	// http://docs.opencv.org/doc/tutorials/imgproc/imgtrans/canny_detector/canny_detector.html#canny-detector
	@Override
	public void processImage(BufferedImage data) {
		
		image = data;
	}

	private void calculate() {
		if(image != null){
			
			Mat camFrame = ImageUtils.bufferedImageToMat(image);
			image = null;
			
			Imgproc.cvtColor(camFrame, tempOne, Imgproc.COLOR_BGR2HSV);
			Core.inRange(tempOne, colorModel.getLowerThreshold(), colorModel.getUpperThreshold(), tempTwo);
			
			Imgproc.blur(tempTwo, tempTwo, blurRadius); // Blur
//			
//			Mat edges = new Mat();
//			Imgproc.Canny(tempTwo, edges, em.getCannyThresholdOne(),
//					em.getCannyThresholdTwo());// edge detection
			
			Imgproc.HoughCircles(tempTwo, circles, Imgproc.CV_HOUGH_GRADIENT, model.getDensity(),
					tempTwo.cols()/4, em.getCannyThresholdTwo(),em.getCannyThresholdOne(), model.getCircleMinSize(), model.getCircleMaxSize());
			
			model.setCircles(circles);
			
			fireCirclesChanged(camFrame);
			
			BufferedImage deepCopy = ImageUtils.matToBufferedImage(tempTwo);
			Graphics2D g2 = deepCopy.createGraphics();
			g2.setStroke(new BasicStroke(5));
			
			
			for (int i = 0; i < circles.cols(); i++) {
				
				double vCircle[] = circles.get(0,i);
				
				if (vCircle == null)
					continue;
				
				Point pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
				int radius = (int)Math.round(vCircle[2]);
				
				
				g2.setColor(Color.RED);
				g2.fillOval((int)(pt.x - 2), (int)pt.y - 2, 4, 4);
				
				g2.setColor(Color.GREEN);
				g2.drawOval((int)pt.x - radius, (int)pt.y - radius, radius*2, radius*2);
			}
			
			g2.drawLine(0, RAND, deepCopy.getWidth(), RAND);
			g2.drawLine(0, deepCopy.getHeight() - RAND, deepCopy.getWidth(), deepCopy.getHeight() - RAND);
			g2.drawLine(RAND, RAND, RAND, deepCopy.getHeight() - RAND);
			g2.drawLine(deepCopy.getWidth() - RAND, RAND, deepCopy.getWidth() - RAND, deepCopy.getHeight() - RAND);
			
			g2.dispose();
			model.setCircleImage(deepCopy);
		}
	}

	private void fireCirclesChanged(Mat camFrame) {
		if(circles.cols() == 1){
			double vCircle[] = circles.get(0,0);

	        if (vCircle == null) return;
	        
	        Point pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
//	        System.out.println(pt);
	        if(camFrame.height() - RAND < pt.y){
	        	fire(PositionData2D.LOWER);
	        }else if(pt.y < RAND){
	        	fire(PositionData2D.HIGHER);
	        }else if(camFrame.width() - RAND < pt.x){
	        	fire(PositionData2D.RIGHT);
	        }else if(pt.x < RAND){
	        	fire(PositionData2D.LEFT);
	        }else {
	        	fire(PositionData2D.NOP);
	        }
		}
	}

	private void fire(PositionData right) {
		for (NavController nav : controller) {
			nav.setPosData(right);
		}
	}

	@Override
	public void addNavController(NavController contr) {
		controller.add(contr);
	}

	@Override
	public void run() {
		while(true){
			calculate();
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
			}
		}
	}
}
