package thesis;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import processing.core.PApplet;
import processing.core.PVector;

public class ReservationMatrix implements AnimatedObject {

	private PVector position;
	public float size;
	public Vector<Vector<ReservationSection>> spaceTable;
	public Map<ReservationSection, Vector<Long>> spaceTimeTable;
	public Map<Car, Vector<Reservation>> carReservationTable;
	public Vector<Reservation> reservations;
	public final Semaphore lock = new Semaphore(1);

	public ReservationMatrix(Intersection intersection) {
		this.position = intersection.position;
		this.size = Lane.laneWidth*10.0f;
		spaceTimeTable = new Hashtable<ReservationSection, Vector<Long>>();
		carReservationTable = new Hashtable<Car, Vector<Reservation>>();
		
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
			
			if (timeReserved.contains(t)) {
				release(car);
				return false;	
			}
			
			
			this.reservations.addElement(new Reservation(section, t, car));
		}
		
		lock.release();
		
		return true;
	}
	
	public void release(Car car) {
		for (Reservation reservation : this.reservations) {
			if (reservation.owner == car)
				this.reservations.remove(reservation);
		}
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
}
