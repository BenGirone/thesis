package thesis;

import processing.core.PVector;

public class Physics {

	public static long timeToTravelDistance(float distance, float velocity) {
		velocity = (velocity * Globals.framerate)/1000.0f; // ft/ms
		
		return (long) (distance/velocity);
	}
	
	public static long arrivalTime(float distance, float velocity) {
		return (Time.current() + timeToTravelDistance(distance, velocity));
	}
	
	public static PVector positionAtTime(PVector position, PVector velocity, long time) {
		return PVector.add(position, PVector.mult(velocity, Globals.framerate * 0.001f * ((time - Time.current()))));
	}
	
	public static float calcSpeed(float mphSpeed) {
		// mi/h -> ft/sec -> pixels/sec -> pixels/frame
		return (mphSpeed * 1.46666667f * Globals.pixelsPerFoot) / Globals.framerate;
	}
	
	public Physics() {
		// TODO Auto-generated constructor stub
	}

}
