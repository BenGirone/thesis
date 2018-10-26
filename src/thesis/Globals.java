package thesis;

import processing.core.PApplet;
import processing.core.PVector;

public class Globals {
	public static PApplet canvas;
	public static int globalWidth = 750;
	public static int globalHeight = 750;
	public static float pixelsPerFoot = 2.00f;
	public static float framerate = 30;
	
	public static PVector roundVector(PVector v) {
		v.x = Math.round(v.x);
		v.y = Math.round(v.y);
		return v;
	}
}
