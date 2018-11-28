package thesis;

import processing.core.PApplet;
import processing.core.PVector;

public class TestProgram extends PApplet {
	
	public static void main(String[] args) {
		PVector pos = new PVector(0, 0);
		PVector v = new PVector(Physics.calcSpeed(10.0f), 0);
		
		System.out.println(Physics.positionAtTime(pos, v, (Time.current() + (1000 * 10))).toString());
		PApplet.main("thesis.TestProgram");
	}
	
	public static float calcSpeed(float mphSpeed) {
		// mi/h -> ft/sec -> pixels/sec -> pixels/frames
		return (mphSpeed * 1.46666667f * Globals.pixelsPerFoot) / Globals.framerate;
	}

	public void settings() {
		size(Globals.globalHeight, Globals.globalWidth);
	}

	public void setup() {
	}

	public void draw() { 
	}

}
