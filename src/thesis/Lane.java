package thesis;

import java.util.LinkedList;
import java.util.Queue;

class Lane {
	public char direction;
	public Queue<Car> cars;
	public float speedLimit;
	public float speedMinimum;
	public Car lastCar;

	Lane(char direction) {
		this.direction = direction;
		this.cars = new LinkedList<Car>();
		this.speedLimit = 5.0f;
		this.speedMinimum = 0.75f;
	}

	public void addCar() {
		switch (this.direction) {
		case 'N':
			lastCar = new CarNorth();
			break;
		case 'S':
			lastCar = new CarSouth();
			break;
		case 'E':
			lastCar = new CarEast();
			break;
		case 'W':
			lastCar = new CarWest();
			break;

		default:
			break;
		}

		cars.add(lastCar);
	}

	public void removeCar() {
		this.cars.poll();
	}

	public void simulate() {
		if (this.cars.size() == 0 || this.lastCar.clearFromStart()) {
			this.addCar();
		}

		for (Car car : this.cars) {
			car.simulate();
		}
		
		if (cars.peek().offScreen()) {
			this.removeCar();
		}
	}

	public void renderCars() {
		for (Car car : this.cars) {
			car.render();
		}
	}
}