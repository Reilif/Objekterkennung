package test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.yadrone.base.ARDrone;
import de.yadrone.base.video.ImageListener;

class TutorialVideoListener extends JFrame
{
	private BufferedImage image = null;
	
	public TutorialVideoListener(final ARDrone drone)
	{
		super("YADrone Tutorial");
		
		setSize(640,360);
		
		drone.getVideoManager().addImageListener(new ImageListener() {
			public void imageUpdated(BufferedImage newImage)
			{
				image = newImage;
				SwingUtilities.invokeLater(new Runnable() {
					public void run()
					{
						repaint();
					}
				});
			}
		});
		setVisible(true);
	}
	
	public void paint(Graphics g)
	{
		if (image != null)
			g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
	}
}
public class DronenTest {
	
	
	public static void main(String[] args)
	{
	    ARDrone drone = null;
	    try
	    {
	        drone = new ARDrone();
	        drone.start();
	        
//	        drone.getCommandManager().setLedsAnimation(LEDAnimation.BLINK_ORANGE, 3, 10);
//	        drone.getCommandManager().setSSIDSinglePlayer("Donald");
	        TutorialVideoListener tutorialVideoListener = new TutorialVideoListener(drone);
//	        
//	        drone.getCommandManager().takeOff();
//	        drone.getCommandManager().flatTrim();
//	        
//	        Thread.sleep(1000*10);
//	        drone.getCommandManager().landing();
	    }
	    catch(Exception exc)
	    {
	        exc.printStackTrace();
	        
	        if (drone != null)
	            drone.stop();
	        
	        System.exit(-1);
	    }
	    

	}
	

}
