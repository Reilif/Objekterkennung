package de.xtion.drone;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import de.xtion.drone.interfaces.DrohnenController;
import de.xtion.drone.interfaces.Navdata;
import de.xtion.drone.interfaces.Navdata.Direction2D;
import de.xtion.drone.interfaces.OBJController;
import de.yadrone.base.ARDrone;
import de.yadrone.base.command.VideoChannel;
import de.yadrone.base.command.VideoCodec;
import de.yadrone.base.video.ImageListener;

public class ARDroneController implements DrohnenController, ImageListener {

	private final class FlyThread extends Thread {
		@Override
		public void run() {
			super.run();
			while (run) {
				if(drone != null){
					if(flying){
						fly();
					}
				}
				
				try {
					Thread.sleep(HOVER_SLEEP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static final int FPS_CONFIG = 15; // Werte zwischen 15 und 30

	private static final int HOVER_SLEEP = 500;

	private static final long LAUNCH_SLEEP = 1000;

	private ARDrone drone;

	private ArrayList<OBJController> objControllers = new ArrayList<OBJController>();

	private boolean flying = true;

	protected boolean run;

	private Thread flyThread;

	private Direction2D nextMove = Direction2D.NOP;

	private Direction2D lastMove = Direction2D.NOP;

	private int timer = 0;

	public ARDroneController() {
	}

	protected void fly() {
		timer ++;
		if(timer == 5){
			nextMove = Direction2D.NOP;
			System.out.println("Hover");
			timer = 0;
		}
		
		if(lastMove == nextMove ){
			lastMove = nextMove;
			if(nextMove == Direction2D.NOP){
				drone.hover();
				System.out.println("Hover");
				return;
			}
			return;
		}
		
		
		
		lastMove = nextMove;
		switch (nextMove) {
		case DOWN:
			drone.getCommandManager().down(30);
			break;
		case LEFT:
			drone.getCommandManager().goLeft(5);
			break;
		case NOP:
			drone.getCommandManager().hover();
			System.out.println("Hover");
			break;
		case RIGHT:
			drone.getCommandManager().goRight(5);
			break;
		case UP:
			drone.getCommandManager().up(30);
			break;
		case TURN_RIGHT:
			drone.getCommandManager().spinRight(20);
			break;
		case TURN_LEFT:
			drone.getCommandManager().spinLeft(20);
			break;
		default:
			land();
			break;
		}
		
		nextMove = Direction2D.NOP;
	}

	@Override
	public void addOBJController(OBJController contr) {
		objControllers.add(contr);
	}

	@Override
	public void setNavdata(Navdata data) {
		if (data instanceof Direction2D) {
			Direction2D direction2d = (Direction2D) data;
			nextMove = direction2d;
		}
	}

	public boolean connectToDrone() {
		ARDrone arDrone = getDrone();
		arDrone.start();
		
		boolean b = true;
//		boolean b = arDrone.getCommandManager().isConnected() && arDrone.getNavDataManager().isConnected() && arDrone.getVideoManager().isConnected();
		if(b){
			arDrone.getVideoManager().addImageListener(this);
			arDrone.getCommandManager().setVideoCodec(VideoCodec.MP4_360P);
			arDrone.getCommandManager().setNavDataDemo(false);
			arDrone.getCommandManager().setVideoChannel(VideoChannel.VERT);
//			arDrone.getCommandManager().setVideoOnUsb(true);
//			arDrone.getCommandManager().setVideoCodecFps(FPS_CONFIG);
		}
		return b;
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

	private ARDrone getDrone() {
		if (drone == null) {
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
		if (drone != null && drone.getCommandManager().isConnected()) {
			drone.reset();
		}
	}

	@Override
	public void launch() {
		if(drone != null){
			drone.takeOff();
			
			run = true;
			if(flyThread == null || !flyThread.isAlive()){
				flyThread = new FlyThread();
				flyThread.start();
			}else{
				System.out.println("Alter FlyThread läuft noch");
			}
		}
	}

	@Override
	public void land() {
		if(drone != null){
			drone.landing();
			
			run = false;
		}
	}

	@Override
	public void turnLeft() {
		nextMove = Direction2D.TURN_LEFT;
	}

	@Override
	public void turnRight() {
		nextMove = Direction2D.TURN_RIGHT;
		
	}
}
