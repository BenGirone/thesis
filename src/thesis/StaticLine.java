package thesis;

import processing.core.PVector;
public class StaticLine implements AnimatedObject{

	private float[] params;
	
	public StaticLine(float[] params) {
		this.params = params;
	}
	
	public StaticLine(PVector p1, PVector p2) {
		this.params = new float[] {p1.x, p1.y, p2.x, p2.y};
	}
	
	@Override
	public void render() {
		Globals.canvas.line(params[0], params[1], params[2], params[3]);
	}

	@Override
	public void simulate() {
		System.err.println("Cannot simulate a static line");
	}
}
