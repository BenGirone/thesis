package thesis;

import processing.core.PApplet;
import processing.core.PVector;

public class Program extends PApplet {
	
	static Intersection intersection = new Intersection(new PVector(Globals.globalHeight/2.0f, Globals.globalHeight/2.0f), Globals.globalHeight);

	public static void main(String[] args) {
		PApplet.main("thesis.Program");
	}

	public void settings() {
		size(Globals.globalHeight, Globals.globalWidth);
	}
	
	public void setup() {
		rectMode(CENTER);
		Globals.canvas = this;
		frameRate(30);
	}

	public void draw() {
		clear();

		intersection.render();
		intersection.simulate();
		
		text(String.format("X: %d\nY: %d\nt: %d", mouseX, mouseY, Time.current()), mouseX, mouseY);
		
		fill(255, 255, 255);
		noStroke();
	}

}
