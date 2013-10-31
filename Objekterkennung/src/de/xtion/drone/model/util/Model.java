package de.xtion.drone.model.util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Model is an abstract class that provides child classes with methods to fire Events based on a enum array. That way
 * no more add/fire/remove Methods need to be written.
 *
 * @param <T> T is the class of the used enum Objects
 */
public abstract class Model<T extends Enum<T>> {
	//Event listeners
	final HashMap<T, ArrayList<ModelEventListener>> listener;

	/**
	 * @param eventNames The parameter eventNames represents an array of enum Objects that are used for the add/fire/remove
	 *                   methods
	 */
	public Model(T[] eventNames) {
		listener = new HashMap<T, ArrayList<ModelEventListener>>();
		for(T eventName : eventNames) {
			listener.put(eventName, new ArrayList<ModelEventListener>());
		}
	}

	/**
	 * @param listener the value determines the listener to be added
	 */
	public void addModelEventListener(T eventName, ModelEventListener listener) {
		this.listener.get(eventName).add(listener);
	}

	/**
	 * The method will fire a ModelEvent
	 *
	 * @param eventName the value determines the "event" to be fired
	 */
	protected void fireModelEvent(T eventName) {
		ModelEvent event = new ModelEvent(this);
		for(ModelEventListener l : listener.get(eventName)) {
			l.actionPerformed(event);
		}
	}

	/**
	 * @param listener the value determines the listener to be removed
	 */
	public void removeModelEventListener(T eventName, ModelEventListener listener) {
		this.listener.remove(eventName).remove(listener);
	}
}
