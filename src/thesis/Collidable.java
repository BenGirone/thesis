package thesis;

import processing.core.PVector;

public interface Collidable {
	public boolean isColliding(Collidable o);
	public PVector X1();
	public PVector X2();
	public PVector Y1();
	public PVector Y2();
}
