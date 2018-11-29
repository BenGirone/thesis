package thesis;

public class Reservation {
	
	public ReservationSection section;
	public long time;
	public Car owner;
	
	public Reservation(ReservationSection section, long time, Car owner) {
		this.section = section;
		this.time = time;
		this.owner = owner;
	}

}
