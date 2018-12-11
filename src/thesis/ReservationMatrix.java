package thesis;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import processing.core.PApplet;
import processing.core.PVector;

public class ReservationMatrix implements AnimatedObject {
	
	class ReservationKey {
		public ReservationSection section;
		public long time;

		ReservationKey(ReservationSection section, long time) {
			this.section = section;
			this.time = time;
		}
	}
	
	private PVector position;
	public float size;
	public Vector<Vector<ReservationSection>> spaceTable;
	public Map<ReservationSection, Vector<Long>> spaceTimeTable;
	private Map<Car, Vector<Reservation>> carReservationTable;
	public Map<ReservationKey, Car> reservationKeyCarTable;
	public Vector<Reservation> reservations;
	public final Semaphore lock = new Semaphore(1);

	public ReservationMatrix(Intersection intersection) {
		this.position = intersection.position;
		this.size = Lane.laneWidth*10.0f;
		spaceTimeTable = new Hashtable<ReservationSection, Vector<Long>>();
		carReservationTable = new Hashtable<Car, Vector<Reservation>>();
		reservationKeyCarTable = new Hashtable<ReservationKey, Car>();
		
		reservations = new Vector<Reservation>();
		
		this.spaceTable = new Vector<Vector<ReservationSection>>();
		Vector<ReservationSection> currentRow;
		for (float i = (position.x - (size/2.0f - Globals.pixelsPerFoot*2.5f)); i < position.x + this.size/2.0f; i+=Globals.pixelsPerFoot*5) {
			currentRow = new Vector<ReservationSection>();
			for (float j = (position.y - (size/2.0f - Globals.pixelsPerFoot*2.5f)); j < position.y + this.size/2.0f; j+=Globals.pixelsPerFoot*5) {
				currentRow.addElement(new ReservationSection(new PVector(i, j), Globals.pixelsPerFoot*5));
				spaceTimeTable.put(currentRow.lastElement(), new Vector<Long>());
			}
			spaceTable.addElement(currentRow);
		}
	}
	
	public boolean reserve(Vector<ReservationSection> sections, long t, Car car) {
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Vector<Long> timeReserved;
		for (ReservationSection section : sections) {
			
			timeReserved = spaceTimeTable.get(section);
			
			if (timeReserved.contains(t/100) && reservationKeyCarTable.get(new ReservationKey(section, t/100)) != car) {
				if (carReservationTable.containsKey(car))
					release(car);
				//System.out.println("Reservation taken");
				lock.release();
				return false;
			}
			
			Reservation newReservation = new Reservation(section, t/100, car);
			
			if (!carReservationTable.containsKey(car))
				carReservationTable.put(car, new Vector<Reservation>());
			
			carReservationTable.get(car).addElement(newReservation);
			spaceTimeTable.get(section).addElement(t/100);
			reservationKeyCarTable.put(new ReservationKey(section, t/100), car);
			this.reservations.addElement(newReservation);
		}
		
		lock.release();
		
		return true;
	}
	
	public void release(Car car) {
		for (Reservation reservation : this.carReservationTable.get(car)) {
				this.reservations.remove(reservation);
				spaceTimeTable.get(reservation.section).remove(reservation.time);
				reservationKeyCarTable.remove(new ReservationKey(reservation.section, reservation.time));
		}
		
		carReservationTable.get(car).clear();
	}

	@Override
	public void render() {
		Globals.canvas.rectMode(PApplet.CENTER);
		Globals.canvas.stroke(255);
		Globals.canvas.noFill();
		Globals.canvas.rect(this.position.x, this.position.y, this.size, this.size);
		for (int i = 0; i < spaceTable.size(); i++) {
			for (int j = 0; j < spaceTable.get(i).size(); j++) {
				spaceTable.get(i).get(j).render();
			}
		}
	}

	@Override
	public void simulate() {
		// TODO Auto-generated method stub
		
	}

	public boolean canReserve(Vector<ReservationSection> sections, long t, Car car) {
		for (ReservationSection section : sections)
			if (spaceTimeTable.get(section).contains(t/100) && reservationKeyCarTable.get(new ReservationKey(section, t/100)) != car)
				return false;
		return true;
	}

	public boolean fullReserve(Vector<Vector<ReservationSection>> fullList, Vector<Long> desiredTimes, Car car) {
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < fullList.size(); i++) {
			if (canReserve(fullList.get(i), desiredTimes.get(i), car)) {
				lock.release();
				return false;
			}
		}
		
		if (carReservationTable.containsKey(car))
			release(car);
		
		for (int i = 0; i < fullList.size(); i++) {
			for (ReservationSection section : fullList.get(i)) {
				Reservation newReservation = new Reservation(section, desiredTimes.get(i)/100, car);
				
				if (!carReservationTable.containsKey(car))
					carReservationTable.put(car, new Vector<Reservation>());
				
				carReservationTable.get(car).addElement(newReservation);
				spaceTimeTable.get(section).addElement(desiredTimes.get(i)/100);
				reservationKeyCarTable.put(new ReservationKey(section, desiredTimes.get(i)/100), car);
				this.reservations.addElement(newReservation);
			}
		}
		
		lock.release();
		return true;
	}
}
