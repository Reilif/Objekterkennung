package de.xtion.drone.motioncontroller;

import java.io.IOException;

import com.codeminders.hidapi.ClassPathLibraryLoader;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

import de.xtion.drone.interfaces.DrohnenController;
import de.xtion.drone.launcher.Launcher;

public class MoveController implements Runnable{

	private static final long READ_UPDATE_DELAY_MS = 50L;
	private static final int BUFSIZE = 2048;
	private static final int VID = 0x8888;
	private static final int PID = 0x0308;
	private DrohnenController drone;
	private Launcher launcher;

	
	static {
		ClassPathLibraryLoader.loadNativeHIDLibrary();
	}

	
	public MoveController(DrohnenController drohne, Launcher launcher) {
		this.drone = drohne;
		this.launcher = launcher;
		
		new Thread(this).start();
	}
	/**
	 * Static function to read an input report to a HID device.
	 */
	private void readDevice(int vid, int pid) {
		HIDDevice dev;
		try {
			HIDManager hid_mgr = HIDManager.getInstance();
			dev = hid_mgr.openById(vid, pid, null);
			if (dev == null) {
				return;
			}else{
			}
			try {
				byte[] buf = new byte[BUFSIZE];
				dev.enableBlocking();
				while (true) {
					int n = dev.read(buf);
//					dev.sendFeatureReport(new byte[]{0x00,0x00,0x00,0x02,0x00,127,0x00,0x00,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});
					byte b = buf[0];

					if ((b & (0x1 << 0)) != 0) {
						System.out.println("Btn Dreieck gedrückt");
						drone.turnRight();
					} else if ((b & (0x1 << 1)) != 0) {
						System.out.println("Btn Kreis gedrückt");
						launcher.starteObjektsteuerung();
					} else if ((b & (0x1 << 2)) != 0) {
						System.out.println("Btn Kreuz gedrückt");
					} else if ((b & (0x1 << 3)) != 0) {
						System.out.println("Btn Quadrat gedrückt");
						drone.turnLeft();
					} else if ((b & (0x1 << 4)) != 0) {
						System.out.println("Btn Vorne gedrückt");
						drone.launch();
						
					} else if ((b & (0x1 << 5)) != 0) {
						System.out.println("Btn Hinten gedrückt");
						drone.land();
					} 

						try {
							Thread.sleep(READ_UPDATE_DELAY_MS);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
			} finally {
				dev.close();
				hid_mgr.release();
				System.gc();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		readDevice(VID, PID);
	}

}
