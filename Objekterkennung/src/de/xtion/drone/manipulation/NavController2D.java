package de.xtion.drone.manipulation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import de.xtion.drone.interfaces.DrohnenController;
import de.xtion.drone.interfaces.NavController;
import de.xtion.drone.interfaces.Navdata.Direction3D;
import de.xtion.drone.interfaces.PositionData;

public class NavController2D implements NavController {

	private PositionData lastState = null;

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
		if (data instanceof QboDirection) {
			QboDirection qboDirection = (QboDirection) data;

			if (lastState == qboDirection && befehlEinmal)
				return;

			lastState = qboDirection;
			switch (qboDirection) {
			case N:
			case NW:
			case NO:
				fireNavData(Direction3D.UP);
				break;
			case W:
			case SW:
				fireNavData(Direction3D.LEFT);
				break;
			case S:
				fireNavData(Direction3D.DOWN);
				break;
			case O:
			case SO:
				fireNavData(Direction3D.RIGHT);
				break;
			case CENTER:
				fireNavData(Direction3D.NOP);
				break;
			default:
				break;
			}
		}
	}

	private void fireNavData(Direction3D up) {
		for (DrohnenController ctrl : listener) {
			ctrl.setNavdata(up);
		}
	}
}
