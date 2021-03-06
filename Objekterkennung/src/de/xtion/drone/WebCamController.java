package de.xtion.drone;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;

import de.xtion.drone.manipulation.ImgProcessing;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import de.xtion.drone.interfaces.DrohnenController;
import de.xtion.drone.interfaces.Navdata;
import de.xtion.drone.interfaces.OBJController;
import de.xtion.drone.utils.ImageUtils;

public class WebCamController implements DrohnenController {
	private List<OBJController> objControllers = Collections.synchronizedList(new ArrayList<OBJController>());
	private JLabel        monitor;
	private BufferedImage image;
	private Thread        video;

	public WebCamController() {
		final Mat webcamImage = new Mat();
		final VideoCapture capture = new VideoCapture(0);

		video = new Thread(new Runnable() {
			@Override public void run() {
				if(capture.isOpened()) {
					while(true) {
						capture.read(webcamImage);
						if(!webcamImage.empty()) {
							image = ImgProcessing.setImgAdjustments(ImageUtils.matToBufferedImage(webcamImage));
                        //    RescaleOp rescaleOp = new RescaleOp(1.0f, 0, null);
                        //    rescaleOp.filter(image, image);

							if(image != null){
								synchronized (objControllers) {
									for(OBJController o : objControllers) {
										o.processImage(image);
									}
								}
							}
						} else {
							System.out.println(" --(!) No captured frame -- Break!");
							break;
						}
					}
				}
			}
		});
		video.start();
	}

	@Override public void addOBJController(OBJController contr) {
		objControllers.add(contr);
	}

	@Override public void setNavdata(Navdata data) {
		System.out.println(data);
	}

	@Override public void stop() {
		video.interrupt();
	}

	@Override public void showLiveCam(JLabel monitor) {
		this.monitor = monitor;
	}

	@Override public boolean connectToDrone() {
		return true;
	}

	@Override public void resetDrone() {
		video.interrupt();

		final Mat webcamImage = new Mat();
		final VideoCapture capture = new VideoCapture(0);

		video = new Thread(new Runnable() {
			@Override public void run() {
				if(capture.isOpened()) {
					while(true) {
						capture.read(webcamImage);
						if(!webcamImage.empty()) {
							image = ImageUtils.matToBufferedImage(webcamImage);
						} else {
							System.out.println(" --(!) No captured frame -- Break!");
							break;
						}
					}
				}
			}
		});
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void land() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void up() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void down() {
		// TODO Auto-generated method stub
		
	}
}
