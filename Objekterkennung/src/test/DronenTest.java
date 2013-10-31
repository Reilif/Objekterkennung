package test;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import de.yadrone.base.ARDrone;
import de.yadrone.base.navdata.BatteryListener;
import de.yadrone.base.video.ImageListener;

class TutorialVideoListener extends JFrame
{
	private BufferedImage image = null;
	private Panel comp;
	
	public TutorialVideoListener(final ARDrone drone)
	{
		super("YADrone Tutorial");
		
		setSize(640,360);
		
		comp = new Panel();
		add(comp);
		
		drone.getVideoManager().addImageListener(new ImageListener() {
			public void imageUpdated(BufferedImage newImage)
			{
				comp.setImage(Panel.bufferedImageToMat(newImage));
			}
		});
		
		setVisible(true);
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
//	        TutorialVideoListener tutorialVideoListener = new TutorialVideoListener(drone);
//	        
	        drone.getNavDataManager().addBatteryListener(new BatteryListener() {
				
				@Override
				public void voltageChanged(int vbat_raw) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void batteryLevelChanged(int percentage) {
//					System.out.println(percentage+"% geladen" );
				}
			});
	        
//	        drone.getCommandManager().flatTrim();
//	        Thread.sleep(1000*5);
//
	        drone.getCommandManager().takeOff();
	        Thread.sleep(1000*10);
	        drone.getCommandManager().landing();
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
