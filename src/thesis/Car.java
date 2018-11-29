package thesis;

import java.util.Comparator;
import processing.core.PApplet;
import processing.core.PVector;

/*
 * Car Facts:
 * 	Avg Length - 15 feet
 * 	Avg Width - 5.5 feet
 * Road Facts:
 * 	Avg Width - 12 feet
 * 
 */
class Car implements AnimatedObject, Collidable {
	
	private static long nextRank = 0;
	public long rank;
	
	enum turn { NONE, LEFT, RIGHT }
	public static float carWidth = Globals.pixelsPerFoot * 6.0f;
	public static float carLength = Globals.pixelsPerFoot * 15.5f;
	public Lane lane;
	
	public PVector position;
	public PVector velocity;
	public PVector acceleration = new PVector(0,0);
	public float r;
	public float maxforce = 10.0f;    // Maximum steering force
	public float maxspeed = 35.0f;    // Maximum speed
	public float distanceTraveled = 0;
	
	public turn turnType;
	
	public long timeIn;
	public long timeOut;
	
	private PVector x_off;
	private PVector y_off;
	
	private PVector phantomPosition;
	
	Car(Lane lane) {
		this.lane = lane;
		this.position = lane.startPos.copy().sub(lane.directionVector.copy().mult(carLength));
		this.phantomPosition = this.position;
		this.velocity = lane.directionVector.copy().mult(lane.speedLimit);
		this.timeIn = Physics.arrivalTime((((lane.length)/2.0f - lane.parentIntersection.reservationMatrix.size/2.0f) + carLength) - this.distanceTraveled, PVector.dot(velocity, lane.directionVector));
		this.timeOut = (timeIn + Physics.timeToTravelDistance(lane.parentIntersection.reservationMatrix.size, PVector.dot(velocity, lane.directionVector)));

		PVector offset = new PVector(carWidth/2.0f, carLength/2.0f).rotate(velocity.heading() + PApplet.HALF_PI);
		
		x_off = new PVector(Math.abs(offset.x), 0);
		y_off = new PVector(0, Math.abs(offset.y));
		
		setRank();
		
		new CarScheduler(this).start();
	}
	
	private void setRank() {
		this.rank = nextRank;
		nextRank++;
	}
	
	public void changeSpeed(float dv) {
		this.velocity.add(PVector.mult(lane.directionVector, Physics.calcSpeed(dv)));
		this.timeIn = Physics.arrivalTime((((lane.length)/2.0f - lane.parentIntersection.reservationMatrix.size/2.0f) + carLength) - this.distanceTraveled, PVector.dot(velocity, lane.directionVector));
		this.timeOut = (timeIn + Physics.timeToTravelDistance(lane.parentIntersection.reservationMatrix.size, PVector.dot(velocity, lane.directionVector)));
	}

	public void simulate() {
		this.move();
	}

	public void move() {
		velocity.add(acceleration);
		position.add(velocity);
		distanceTraveled += Math.abs(velocity.dot(lane.directionVector));
	}

	public boolean isBeyondIntersection() {
		return position.dist(lane.startPos) >= lane.length;
	}

	public boolean isClearFromStart() {
		return distanceTraveled >= (4*carLength);
	}
	
	public void render() {
		Globals.canvas.rectMode(PApplet.CENTER);
		Globals.canvas.pushMatrix();
		Globals.canvas.translate(this.position.x, this.position.y);
		Globals.canvas.rotate(velocity.heading() + PApplet.HALF_PI);
		Globals.canvas.fill(219, 29, 29);
		if (Time.current() >= timeIn && Time.current() < timeOut)
			Globals.canvas.fill(255);
		Globals.canvas.rect(0, 0, carWidth, carLength);
		Globals.canvas.fill(0);
		Globals.canvas.text(velocity.mag(), 0, 0);
		Globals.canvas.popMatrix();
	}

	@Override
	public boolean isColliding(Collidable o) {
		return this.X1().x < o.X2().x && this.X2().x > o.X1().x && this.Y1().y < o.Y2().y && this.Y2().y > o.Y1().y;
	}

	@Override
	public PVector X1() {
		return PVector.sub(phantomPosition, x_off);
	}

	@Override
	public PVector X2() {
		return PVector.add(phantomPosition, x_off);
	}

	@Override
	public PVector Y1() {
		return PVector.sub(phantomPosition, y_off);
	}

	@Override
	public PVector Y2() {
		return PVector.add(phantomPosition, y_off);
	}

	public boolean willNeedSection(PVector posAtTime, ReservationSection reservationSection) {
		phantomPosition = posAtTime;
		
		boolean result = isColliding(reservationSection);
		
		phantomPosition = position;
		
		return result;
	}
}

class CarComparator implements Comparator<Car> {

	@Override
	public int compare(Car car1, Car car2) {
		if (car1.position.dist(car1.lane.endPos) < car2.position.dist(car2.lane.endPos)) {
			return -1;
		} else {
			return 1;
		}
	}
	
}