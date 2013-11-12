package de.xtion.drone.interfaces;

public interface Navdata {

	public enum Direction2D implements Navdata{
		UP, LEFT, RIGHT, DOWN, NOP, TURN_LEFT, TURN_RIGHT;
	}
}
