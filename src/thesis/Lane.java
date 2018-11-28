package thesis;

import java.util.Hashtable;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Vector;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

class Lane implements AnimatedObject, Collidable{
	public static float laneWidth = Globals.pixelsPerFoot * 12.0f;
	public static Map<Character, PVector> directionCharToVector = new Hashtable<Character, PVector>();
	static {
		directionCharToVector.put('N', new PVector(0,-1));
		directionCharToVector.put('S', new PVector(0,1));
		directionCharToVector.put('E', new PVector(1,0));
		directionCharToVector.put('W', new PVector(-1,0));
	}

	public char direction;
	public PVector position;
	public PriorityQueue<Car> cars;
	public float speedLimit;
	public float speedMinimum;
	public float length;
	public boolean initiateTraffic;
	public Car lastCar;
	public PVector startPos;
	public PVector directionVector;
	public PVector endPos;
	public Intersection parentIntersection;
	public PVector intersectionEntrance;
	
	private StaticLine line1;
	private StaticLine line2;
	
	private PVector x_off;
	private PVector y_off;
	
	public Vector<ReservationSection> reservationSections;
	
	Lane(char direction, PVector position, float length, boolean initiateTraffic, Intersection parentIntersection) {
		this.position = position;
		this.initiateTraffic = initiateTraffic;
		this.length = length;
		this.direction = direction;
		this.cars = new PriorityQueue<Car>(new CarComparator());
		this.speedLimit = Physics.calcSpeed(35.0f);
		this.speedMinimum = Physics.calcSpeed(15.0f);
		this.directionVector = directionCharToVector.get(direction);
		this.startPos = position.copy().sub(directionVector.copy().mult(length/2.0f));
		this.endPos = startPos.copy().add(directionVector.copy().mult(length));
		this.parentIntersection = parentIntersection;
		
		PVector left = Globals.roundVector(directionVector.copy().rotate(PApplet.HALF_PI));
		
		this.line1 = new StaticLine(
			left.copy().mult(Globals.pixelsPerFoot).add(parentIntersection.position).sub(directionVector.copy().mult(laneWidth*2.0f)),
			left.copy().mult(Globals.pixelsPerFoot).add(parentIntersection.position).sub(directionVector.copy().mult((length)/2.0f))
		);
		
		this.line2 = new StaticLine(
			left.copy().mult(Globals.pixelsPerFoot).add(parentIntersection.position).add(directionVector.copy().mult(laneWidth*2.0f)),
			left.copy().mult(Globals.pixelsPerFoot).add(parentIntersection.position).add(directionVector.copy().mult((length)/2.0f))
		);
		
		PVector offset = new PVector(laneWidth/2.0f, length/2.0f).rotate(directionVector.heading() + PApplet.HALF_PI);
		x_off = new PVector(Math.abs(offset.x), 0);
		y_off = new PVector(0, Math.abs(offset.y));
		
		this.reservationSections = new Vector<ReservationSection>();
		for (int i = 0; i < parentIntersection.reservationMatrix.spaceTable.size(); i++) {
			for (int j = 0; j < parentIntersection.reservationMatrix.spaceTable.get(i).size(); j++) {
				if (this.isColliding(parentIntersection.reservationMatrix.spaceTable.get(i).get(j))) {
					this.reservationSections.addElement(parentIntersection.reservationMatrix.spaceTable.get(i).get(j));
				}
			}
		}
		System.out.println(this.reservationSections.size());
		
	}

	public void addCar() {
		PVector newCarSpeed;
		if (Time.current() > lastCar.timeOut)
			newCarSpeed = lastCar.velocity;
		else
			newCarSpeed = PVector.mult(directionVector, speedLimit);
		
		lastCar = new Car(this);
		lastCar.velocity = newCarSpeed;

		cars.add(lastCar);
	}
	
	public void addCar(Car car) {
		cars.add(car);
		
		if (car.position.dist(endPos) > lastCar.position.dist(endPos)) {
			lastCar = car;
		}
	}

	public Car removeCar() {
		return this.cars.poll();
	}

	@Override
	public void simulate() {
		if (this.cars.size() == 0 || this.lastCar.isClearFromStart()) {
			this.addCar();
		}

		for (Car car : this.cars) {
			car.simulate(); 
		}
		
		if (cars.peek().isBeyondIntersection()) {
			//System.out.println("removing car");
			this.removeCar();
		}
	}
	
	public void changeCarSpeed(Car car, float dv) {
		car.changeSpeed(dv);
		
		for (Car otherCar : cars) {
			if (otherCar.rank > car.rank && otherCar.velocity.magSq() > car.velocity.magSq()) {
				otherCar.changeSpeed(dv);
			}
		}
	}
	
	@Override
	public void render() {
		Globals.canvas.rectMode(PConstants.CENTER);
		Globals.canvas.stroke(237, 245, 24);
		Globals.canvas.fill(237, 245, 24);
		
		line1.render();
		line2.render();
		
		Globals.canvas.noStroke();
		
		for (Car car : this.cars) {
			car.render();
		}
		
		/*for(ReservationSection r : reservationSections) {
			Globals.canvas.fill(255);
			Globals.canvas.ellipse(r.position.x, r.position.y, 2.0f, 2.0f);
		}*/
		
		//Globals.canvas.text(direction, position.x, position.y);
	}

	@Override
	public boolean isColliding(Collidable o) {
		return (this.X1().x < o.X2().x && this.X2().x > o.X1().x && this.Y1().y < o.Y2().y && this.Y2().y > o.Y1().y);
	}

	@Override
	public PVector X1() {
		return PVector.sub(position, x_off);
	}

	@Override
	public PVector X2() {
		return PVector.add(position, x_off);
	}

	@Override
	public PVector Y1() {
		return PVector.sub(position, y_off);
	}

	@Override
	public PVector Y2() {
		return PVector.add(position, y_off);
	}
}