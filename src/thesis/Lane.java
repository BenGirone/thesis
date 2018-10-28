package thesis;

import java.util.Hashtable;
import java.util.Map;
import java.util.PriorityQueue;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

class Lane implements AnimatedObject {
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
	
	private StaticLine line1;
	private StaticLine line2;
	
	Lane(char direction, PVector position, float length, boolean initiateTraffic, Intersection parentIntersection) {
		this.position = position;
		this.initiateTraffic = initiateTraffic;
		this.length = length;
		this.direction = direction;
		this.cars = new PriorityQueue<Car>(new CarComparator());
		this.speedLimit = calcSpeed(35.0f);
		this.speedMinimum = calcSpeed(15.0f);
		this.directionVector = directionCharToVector.get(direction);
		this.startPos = position.copy().sub(directionVector.copy().mult(length/2.0f));
		this.endPos = startPos.copy().add(directionVector.copy().mult(length));
		this.parentIntersection = parentIntersection;
		/*System.out.println(direction);
		System.out.println(startPos);
		System.out.println(endPos);*/
		
		PVector left = Globals.roundVector(directionVector.copy().rotate(PApplet.HALF_PI));
		
		this.line1 = new StaticLine(
				left.copy().mult(Globals.pixelsPerFoot).add(parentIntersection.position).sub(directionVector.copy().mult(laneWidth*2.0f)),
				left.copy().mult(Globals.pixelsPerFoot).add(parentIntersection.position).sub(directionVector.copy().mult((length)/2.0f))
		);
		
		this.line2 = new StaticLine(
				left.copy().mult(Globals.pixelsPerFoot).add(parentIntersection.position).add(directionVector.copy().mult(laneWidth*2.0f)),
				left.copy().mult(Globals.pixelsPerFoot).add(parentIntersection.position).add(directionVector.copy().mult((length)/2.0f))
		);
	}
	
	public float calcSpeed(float mphSpeed) {
		// mi/h -> ft/sec -> pixels/sec -> pixels/frames
		return (mphSpeed * 1.46666667f * Globals.pixelsPerFoot) / Globals.framerate;
	}

	public void addCar() {
		lastCar = new Car(this);

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
		
		Globals.canvas.text(direction, position.x, position.y);
	}
}