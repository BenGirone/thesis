package thesis;

public class Physics {

	public static long timeToTravelDistance(float distance, float velocity) {
		velocity = (velocity * Globals.framerate)/1000.0f; // ft/ms
		
		return (long) (distance/velocity);
	}
	
	public static long arrivalTime(float distance, float velocity) {
		return (Time.current() + timeToTravelDistance(distance, velocity)) % 600000;
	}
	
	
	
	public Physics() {
		// TODO Auto-generated constructor stub
	}

}
