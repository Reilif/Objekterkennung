package de.xtion.drone;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

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
							image = ImageUtils.matToBufferedImage(webcamImage);

							if(image != null){
								synchronized (objControllers) {
									for(OBJController o : objControllers) {
										o.processImage(image);
									}
								}
								if(monitor != null && image != null) {
									
									
									int height = image.getHeight();
									int width = image.getWidth();
									
									double div = (double) height / (double) width;
									
									Image scaled;
									if(monitor.getWidth()*div > monitor.getHeight()){
										scaled = image.getScaledInstance(monitor.getWidth(), (int) (monitor.getWidth()*div), BufferedImage.SCALE_FAST);
									}else{
										scaled = image.getScaledInstance((int) (monitor.getHeight()/div), monitor.getHeight(), BufferedImage.SCALE_FAST);
									}
									
									monitor.setIcon(new ImageIcon(scaled));
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
