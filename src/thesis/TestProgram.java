package thesis;

import processing.core.PApplet;
import processing.core.PVector;

public class TestProgram extends PApplet {
	
	public static void main(String[] args) {
		System.out.println((long)12345/(long)100);
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
