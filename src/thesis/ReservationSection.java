package thesis;

import java.util.Vector;

public class ReservationSection {

	public static Vector<Float> reservedTime = new Vector<Float>(6000);
	static {
		reservedTime.set(0, 0.0f);
		for (int i = 1; i < reservedTime.size(); i++) {
			reservedTime.set(i, reservedTime.get(i - 1) + 0.01f);
		}
	}
	
	public ReservationSection() {
		
	}
	
	public Boolean isReservedAtTime(float time) {
		return false;
	}
	
}
