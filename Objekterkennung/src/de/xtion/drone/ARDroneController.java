package de.xtion.drone;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import de.xtion.drone.interfaces.DrohnenController;
import de.xtion.drone.interfaces.Navdata;
import de.xtion.drone.interfaces.OBJController;
import de.yadrone.base.ARDrone;
import de.yadrone.base.video.ImageListener;

public class ARDroneController implements DrohnenController, ImageListener {

	private ARDrone drone;
	
	private ArrayList<OBJController> objControllers = new ArrayList<OBJController>();
	
	public ARDroneController() {
		
	}

	@Override
	public void addOBJController(OBJController contr) {
		objControllers.add(contr);
	}

	@Override
	public void setNavdata(Navdata data) {

	}

	public boolean connectToDrone() {
		ARDrone arDrone = getDrone();
		arDrone.start();
		
		arDrone.getVideoManager().addImageListener(this);
		return arDrone.getCommandManager().isConnected();
	}

	public void showLiveCam(final JLabel jLabel) {
		
		
		getDrone().getVideoManager().addImageListener(new ImageListener() {
			
			@Override
			public void imageUpdated(BufferedImage image) {
				jLabel.setIcon(new ImageIcon(image));
			}
		});
	}

	public void stop() {
		getDrone().stop();
	}
	
	private ARDrone getDrone(){
		if(drone == null){
			drone = new ARDrone();
		}
		return drone;
	}

	@Override
	public void imageUpdated(BufferedImage image) {
		fireImageChanged(image);
	}

	private void fireImageChanged(BufferedImage image) {
		for (OBJController controller : objControllers) {
			controller.processImage(image);
		}
	}

	public void resetDrone() {
		if(drone != null && drone.getCommandManager().isConnected()){
			drone.reset();
		}
	}

}
