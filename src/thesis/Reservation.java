package thesis;

public class Reservation {
	
	public ReservationSection section;
	public long time;
	
	public Reservation(ReservationSection section, long time) {
		this.section = section;
		this.time = time;
	}

}
