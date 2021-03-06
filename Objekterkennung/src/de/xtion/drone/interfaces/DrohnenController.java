package de.xtion.drone.interfaces;

import javax.swing.JLabel;

public interface DrohnenController {

	public void addOBJController(OBJController contr);

	public void setNavdata(Navdata data);

	public void stop();

	public void showLiveCam(JLabel monitor0);

	public boolean connectToDrone();

	public void resetDrone();
	
	public void launch();
	
	public void land();

	public void turnLeft();

	public void turnRight();

	public void up();

	public void down();
}
