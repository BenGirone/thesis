package thesis;

abstract class Car {
	public float x;
	public float y;

	public void simulate() {
		this.move();
	}

	public abstract void move();

	public abstract boolean offScreen();

	public abstract boolean clearFromStart();

	public abstract void render();
}

class CarNorth extends Car {
	CarNorth() {
		this.x = Globals.globalWidth * 0.55f;
		this.y = Globals.globalWidth + Globals.globalWidth * 0.05f;
	}

	public void move() {
		this.y -= 15;
	}

	public boolean offScreen() {
		return this.y < (-1 * Globals.globalHeight * 0.05f);
	}

	public boolean clearFromStart() {
		return this.y < (Globals.globalHeight - Globals.globalHeight * 0.15f);
	}

	public void render() {
		Globals.canvas.fill(219, 29, 29);
		Globals.canvas.rect(this.x, this.y, Globals.globalWidth * 0.03f, Globals.globalWidth * 0.05f);
	}
}

class CarSouth extends Car {
	CarSouth() {
		this.x = (Globals.globalWidth * 0.45f);
		this.y = (0 - Globals.globalWidth * 0.05f);
	}

	public void move() {
		this.y += 15;
	}

	public boolean offScreen() {
		return this.y > (Globals.globalHeight + Globals.globalHeight * 0.05f);
	}

	public boolean clearFromStart() {
		return this.y > (Globals.globalHeight * 0.15f);
	}

	public void render() {
		Globals.canvas.fill(219, 29, 29);
		Globals.canvas.rect(this.x, this.y, Globals.globalWidth * 0.03f, Globals.globalWidth * 0.05f);
	}
}

class CarEast extends Car {
	CarEast() {
		this.x = (0 - Globals.globalWidth * 0.05f);
		this.y = (Globals.globalWidth * 0.55f);
	}

	public void move() {
		this.x += 15;
	}

	public boolean offScreen() {
		return this.x > (Globals.globalWidth + Globals.globalWidth * 0.05f);
	}

	public boolean clearFromStart() {
		return this.x > (Globals.globalWidth * 0.15f);
	}

	public void render() {
		Globals.canvas.fill(219, 29, 29);
		Globals.canvas.rect(this.x, this.y, Globals.globalWidth * 0.05f, Globals.globalWidth * 0.03f);
	}
}

class CarWest extends Car {
	CarWest() {
		this.x = (Globals.globalWidth + Globals.globalWidth * 0.05f);
		this.y = (Globals.globalWidth * 0.45f);
	}

	public void move() {
		this.x -= 15;
	}

	public boolean offScreen() {
		return this.x < (-1 * Globals.globalWidth * 0.05);
	}

	public boolean clearFromStart() {
		return this.x < (Globals.globalWidth - Globals.globalWidth * 0.15);
	}

	public void render() {
		Globals.canvas.fill(219, 29, 29);
		Globals.canvas.rect(this.x, this.y, Globals.globalWidth * 0.05f, Globals.globalWidth * 0.03f);
	}
}