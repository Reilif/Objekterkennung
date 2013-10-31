package test.edge_detection.objects.gui;

import java.util.EventObject;

/**
 * The ModelEvent is used by the Model
 */
public class ModelEvent extends EventObject {
	/**
	 * @param source the value determines the source of the event
	 */
	public ModelEvent(Object source) {
		super(source);
	}
}
