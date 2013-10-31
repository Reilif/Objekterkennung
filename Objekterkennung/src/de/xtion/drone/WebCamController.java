package de.xtion.drone;

import de.xtion.drone.interfaces.DrohnenController;
import de.xtion.drone.interfaces.Navdata;
import de.xtion.drone.interfaces.OBJController;
import de.xtion.drone.utils.ImageUtils;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WebCamController implements DrohnenController {
	private ArrayList<OBJController> objControllers = new ArrayList<OBJController>();
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
							image = ImageUtils.matToBufferedImage(webcamImage);

							if(monitor != null && image != null) {
								monitor.setIcon(new ImageIcon(image));

								for(OBJController o : objControllers) {
									o.processImage(image);
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
}
