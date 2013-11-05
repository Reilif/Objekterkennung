package de.xtion.drone.interfaces;

import java.awt.image.BufferedImage;

public interface OBJController {

	public void addNavController(NavController contr);

	public void processImage(BufferedImage data);
}
