package de.xtion.drone.interfaces;

public interface Navdata {

	public enum Direction3D implements Navdata{
		UP, LEFT, RIGHT, DOWN, NOP, TURN_LEFT, TURN_RIGHT, FORWARD, BACKWARD;
	}
}
