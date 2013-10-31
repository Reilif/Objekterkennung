package de.xtion.drone.interfaces;

import javax.swing.JLabel;

public interface DrohnenController {
	
	public void addOBJController(OBJController contr);
	public void setNavdata(Navdata data);
	public void stop();
	public void showLiveCam(JLabel monitor0);
	public boolean connectToDrone();
	public void resetDrone();
}
