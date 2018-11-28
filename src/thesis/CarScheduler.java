package thesis;

import java.util.Vector;

import processing.core.PVector;

public class CarScheduler extends Thread {

	Car car;
	public CarScheduler(Car car) {
		this.car = car;
	}
	
	public void run() {
		boolean hasFullReservation = false;
		
		Vector<ReservationSection> desiredReservations = new Vector<ReservationSection>(); 
		
		while (Time.current() < car.timeIn && !hasFullReservation) {
			for (long t = car.timeIn; t < car.timeOut; t += 100) {
				if (!car.lane.parentIntersection.reservationMatrix.reserve(getCarCollisionsAtTime(car.timeIn), car.timeIn)) {
					if (Math.abs(PVector.dot(car.velocity, car.lane.directionVector)) > Physics.calcSpeed(3.0f))
						car.changeSpeed(-3.0f);
					else
						System.err.println("Car is starved!");
					
					break;
				}
			}
		}
		
		while(Time.current() < car.timeOut) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		car.maxspeed = car.lane.speedLimit;
	}
	
	private Vector<ReservationSection> getCarCollisionsAtTime(long time) {
		Vector<ReservationSection> collisions = new Vector<ReservationSection>(); 
		
		PVector posAtTime = Physics.positionAtTime(car.position, car.velocity, time);
		
		for (int i = 0; i < car.lane.reservationSections.size(); i++) {
			if (car.isColliding(car.lane.reservationSections.get(i))) {
				collisions.addElement(car.lane.reservationSections.get(i));
			}
		}
		return collisions;
	}

}
