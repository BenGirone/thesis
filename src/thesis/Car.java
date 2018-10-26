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
class Car implements AnimatedObject {
	public static float carWidth = Globals.pixelsPerFoot * 6.0f;
	public static float carLength = Globals.pixelsPerFoot * 15.5f;
	public Lane lane;
	
	public PVector position;
	public PVector velocity;
	public PVector acceleration = new PVector(0,0);
	public float r;
	public float maxforce;    // Maximum steering force
	public float maxspeed;    // Maximum speed
	public float distanceTraveled = 0;
	
	Car(Lane lane) {
		this.lane = lane;
		this.position = lane.startPos.copy().sub(lane.directionVector.copy().mult(carLength));
		this.velocity = lane.directionVector.copy().mult(lane.speedLimit);
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
		Globals.canvas.rect(0, 0, carWidth, carLength);
		Globals.canvas.popMatrix();
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