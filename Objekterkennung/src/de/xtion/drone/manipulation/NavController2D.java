package de.xtion.drone.manipulation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import de.xtion.drone.interfaces.DrohnenController;
import de.xtion.drone.interfaces.NavController;
import de.xtion.drone.interfaces.Navdata.Direction2D;
import de.xtion.drone.interfaces.PositionData;
import de.xtion.drone.interfaces.PositionData.PositionData2D;

public class NavController2D implements NavController {

	private PositionData2D lastState = null;

	private Set<DrohnenController> listener = Collections
			.synchronizedSet(new HashSet<DrohnenController>());

	private boolean befehlEinmal = false; // Wenn dieses Flag gesetzt wird, wird
											// jeder Befehl nur EINMALIG und
											// nicht wiederholend ausgeführt.
	@Override
	public void addDrohnenController(DrohnenController contr) {
		listener.add(contr);
	}

	public void removeDrohnenController(DrohnenController contr) {
		listener.remove(contr);
	}

	@Override
	public void setPosData(PositionData data) {
		if (data instanceof PositionData2D) {
			PositionData2D positionData2D = (PositionData2D) data;

			if (lastState == positionData2D && befehlEinmal)
				return;

			lastState = positionData2D;
			switch (positionData2D) {
			case HIGHER:
				fireNavData(Direction2D.UP);
				System.out.println("Hoch");
				break;
			case LEFT:
				fireNavData(Direction2D.LEFT);
//				fireNavData(Direction2D.NOP);
				System.out.println("links");
				break;
			case LOWER:
				fireNavData(Direction2D.DOWN);
				System.out.println("Runter");
				break;
			case RIGHT:
				fireNavData(Direction2D.RIGHT);
//				fireNavData(Direction2D.NOP);
				System.out.println("rechts");
				break;
			case NOP:
				fireNavData(Direction2D.NOP);
				System.out.println("Hover");
				break;
			default:
				break;
			}
		}

	}

	private void fireNavData(Direction2D up) {
		for (DrohnenController ctrl : listener) {
			ctrl.setNavdata(up);
		}
	}
}
