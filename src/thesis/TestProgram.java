package thesis;

import processing.core.PApplet;
import processing.core.PVector;

public class TestProgram extends PApplet {
	
	public static void main(String[] args) {
		System.out.println(ReservationSection.reservedTime.toString());
		PApplet.main("thesis.TestProgram");
	}

	public void settings() {
		size(Globals.globalHeight, Globals.globalWidth);
	}

	public void setup() {
	}

	public void draw() { 
	}

}
