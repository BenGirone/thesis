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
			hasFullReservation = true;
			
			for (long t = car.timeIn; t < car.timeOut; t += 100) {
				desiredReservations = getCarCollisionsAtTime(t);
				
				if (!car.lane.parentIntersection.reservationMatrix.reserve(desiredReservations, t, car)) {
					hasFullReservation = false;
					
					if (Math.abs(PVector.dot(car.velocity, car.lane.directionVector)) > Physics.calcSpeed(3.0f))
					{
						car.changeSpeed(-3.0f);
					}
					else
						car.lane.halt(car);
					
					break;
				}
			}
		}
		
		while(Time.current() < car.timeIn || car.isHalted()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			improveReservation();
		}
		
		while(Time.current() < car.timeOut) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		car.velocity = PVector.mult(car.lane.directionVector, car.lane.speedLimit);
	}
	
	public boolean improveReservation() {
		PVector potentialVelocity = car.velocity.copy();
		long potentialTimeIn;
		long potentialTimeOut;
		while (Math.abs(PVector.dot(potentialVelocity, car.lane.directionVector)) < car.lane.speedLimit && Time.current() < car.timeIn) {
			potentialVelocity.add(PVector.mult(car.lane.directionVector, Physics.calcSpeed(3.0f)));
			potentialTimeIn = Physics.arrivalTime((((car.lane.length)/2.0f - car.lane.parentIntersection.reservationMatrix.size/2.0f) + Car.carLength) - car.distanceTraveled, PVector.dot(potentialVelocity, car.lane.directionVector));
			potentialTimeOut = (potentialTimeIn + Physics.timeToTravelDistance(car.lane.parentIntersection.reservationMatrix.size, PVector.dot(potentialVelocity, car.lane.directionVector)));
			
			if (car.carInFront != null)
				if (potentialTimeOut < car.carInFront.timeOut - 1000)
					return false;
			
			Vector<Vector<ReservationSection>> fullList = new Vector<Vector<ReservationSection>>();
			Vector<ReservationSection> desiredReservations;
			Vector<Long> desiredTimes = new Vector<Long>();
			
			for (long t = potentialTimeIn; t < potentialTimeOut; t += 100) {
				desiredReservations = getCarCollisionsAtTime(t, potentialVelocity);
				
				if (!car.lane.parentIntersection.reservationMatrix.canReserve(desiredReservations, t, car)) {
					desiredTimes.clear();
					fullList.clear();
					continue;
				}
				
				desiredTimes.addElement(t);
				fullList.addElement(desiredReservations);
			}
			
			if (car.lane.parentIntersection.reservationMatrix.fullReserve(fullList, desiredTimes, car))
				car.changeVelocity(potentialVelocity);
		}
		
		return false;
	}
	
	private Vector<ReservationSection> getCarCollisionsAtTime(long time, PVector velocity) {
		Vector<ReservationSection> collisions = new Vector<ReservationSection>(); 
		
		PVector posAtTime = Physics.positionAtTime(car.position, velocity, time);
		
		for (int i = 0; i < car.lane.reservationSections.size(); i++) {
			if (car.willNeedSection(posAtTime, car.lane.reservationSections.get(i))) {
				collisions.addElement(car.lane.reservationSections.get(i));
			}
		}
		return collisions;
	}
	
	private Vector<ReservationSection> getCarCollisionsAtTime(long time) {
		return getCarCollisionsAtTime(time, car.velocity);
	}
}
