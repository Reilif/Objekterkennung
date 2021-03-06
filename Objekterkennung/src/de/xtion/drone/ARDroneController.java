package de.xtion.drone;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import de.xtion.drone.interfaces.DrohnenController;
import de.xtion.drone.interfaces.Navdata;
import de.xtion.drone.interfaces.Navdata.Direction3D;
import de.xtion.drone.interfaces.OBJController;
import de.yadrone.base.ARDrone;
import de.yadrone.base.command.FlyingMode;
import de.yadrone.base.command.VideoChannel;
import de.yadrone.base.navdata.BatteryListener;
import de.yadrone.base.video.ImageListener;

public class ARDroneController implements DrohnenController, ImageListener {

	private final class FlyThread extends Thread {
		@Override
		public void run() {
			super.run();
			while (run) {
				if (drone != null) {
					if (flying) {
						fly();
					}
				}

				try {
					Thread.sleep(HOVER_SLEEP);
				} catch (InterruptedException e) {

				}
			}
		}
	}

	private static final int FPS_CONFIG = 15; // Werte zwischen 15 und 30

	private static final int HOVER_SLEEP = 300;

	private static final long LAUNCH_SLEEP = 1000;

	private ARDrone drone;

	private ArrayList<OBJController> objControllers = new ArrayList<OBJController>();

	private boolean flying = true;

	protected boolean run;

	private Thread flyThread;

	private Direction3D nextMove = Direction3D.NOP;

	private Direction3D lastMove = Direction3D.NOP;

	private int timer = 0;

	private boolean debug = false;

	public ARDroneController() {
	}

	protected synchronized void fly() {
		timer++;
		if (timer == 5) {
			nextMove = Direction3D.NOP;
			timer = 0;
		}

		if (lastMove == nextMove) {
			if (nextMove == Direction3D.NOP) {
				drone.hover();
				return;
			}
			return;
		}

		lastMove = nextMove;
		if (debug) {
			System.out.println(nextMove);
		} else {
			switch (nextMove) {
			case BACKWARD:
				drone.getCommandManager().backward(5);
				break;
			case LEFT:
				drone.getCommandManager().goLeft(5);
				break;
			case NOP:
				drone.getCommandManager().hover();
				break;
			case RIGHT:
				drone.getCommandManager().goRight(5);
				break;
			case FORWARD:
				drone.getCommandManager().forward(5);
				break;
			case TURN_RIGHT:
				drone.getCommandManager().spinRight(20);
				break;
			case TURN_LEFT:
				drone.getCommandManager().spinLeft(20);
				break;
			case UP:
				drone.getCommandManager().up(20);
				break;
			case DOWN:
				drone.getCommandManager().down(20);
				break;
			default:
				land();
				break;
			}
		}
		nextMove = Direction3D.NOP;
	}

	@Override
	public void addOBJController(OBJController contr) {
		objControllers.add(contr);
	}

	@Override
	public void setNavdata(Navdata data) {
		if (data instanceof Direction3D) {
			Direction3D direction2d = (Direction3D) data;
			nextMove = direction2d;
			fly();
		}
	}

	public boolean connectToDrone() {
		ARDrone arDrone = getDrone();
		arDrone.start();

		boolean b = true;
		if (b) {
			arDrone.getCommandManager().setNavDataDemo(false);
			arDrone.getCommandManager().setVideoChannel(VideoChannel.HORI);
			arDrone.getCommandManager().setFlyingMode(FlyingMode.FREE_FLIGHT);
			arDrone.getNavDataManager().addBatteryListener(
					new BatteryListener() {

						private int percentage;

						@Override
						public void voltageChanged(int vbat_raw) {
						}

						@Override
						public void batteryLevelChanged(int percentage) {
							if (this.percentage != percentage) {
								this.percentage = percentage;
								System.out.println(percentage);
							}

						}
					});
			arDrone.getVideoManager().addImageListener(this);
			// arDrone.getCommandManager().setVideoOnUsb(true);
			// arDrone.getCommandManager().setVideoCodecFps(FPS_CONFIG);
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
		if (drone != null) {
			
			if(!debug){
				drone.takeOff();
			}

			run = true;
			if (flyThread == null || !flyThread.isAlive()) {
				flyThread = new FlyThread();
				flyThread.start();
			} else {
				System.out.println("Alter FlyThread l�uft noch");
			}
		}
	}

	@Override
	public void land() {
		if (drone != null) {
			drone.landing();

			run = false;
		}
	}

	@Override
	public void turnLeft() {
		nextMove = Direction3D.TURN_LEFT;
	}

	@Override
	public void turnRight() {
		nextMove = Direction3D.TURN_RIGHT;

	}

	@Override
	public void up() {
		drone.up();
	}

	@Override
	public void down() {
		drone.down();
	}
}
