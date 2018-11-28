package thesis;

import processing.core.PVector;

public class ReservationSection implements AnimatedObject, Collidable {
	
	public PVector position;
	private float offset;
	private float size;
	
	public ReservationSection(PVector position, float size) {
		this.position = position;
		this.size = size;
		this.offset = size/2.0f;
	}

	@Override
	public boolean isColliding(Collidable o) {
		return this.X1().x < o.X2().x && this.X2().x > o.X1().x && this.Y1().y < o.Y2().y && this.Y2().y > o.Y1().y;
	}

	@Override
	public PVector X1() {
		return PVector.sub(this.position, new PVector(offset, 0));
	}

	@Override
	public PVector X2() {
		return PVector.add(this.position, new PVector(offset, 0));
	}

	@Override
	public PVector Y1() {
		return PVector.sub(this.position, new PVector(0, offset));
	}

	@Override
	public PVector Y2() {
		return PVector.add(this.position, new PVector(0, offset));
	}

	@Override
	public void render() {
		Globals.canvas.rect(position.x, position.y, size, size);
	}

	@Override
	public void simulate() {
		System.out.println("No Simulation");
	}
}
