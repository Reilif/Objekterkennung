package de.xtion.drone;

import javax.swing.JFrame;

import de.xtion.drone.interfaces.DrohnenController;
import de.xtion.drone.interfaces.Navdata;
import de.xtion.drone.interfaces.OBJController;
import de.yadrone.base.ARDrone;

public class ARDroneController implements DrohnenController {

	private ARDrone drone;

	@Override
	public void addOBJController(OBJController contr) {
		
	}

	@Override
	public void setNavdata(Navdata data) {

	}

	public boolean connectToDrone() {
		ARDrone arDrone = getDrone();
		arDrone.start();
		return arDrone.getCommandManager().isConnected();
	}

	public void showLiveCam() {
		JFrame jFrame = new JFrame();
		jFrame.setSize(400, 400);
		
	}

	public void stop() {
		
	}
	
	private ARDrone getDrone(){
		if(drone == null){
			drone = new ARDrone();
		}
		return drone;
	}

}
