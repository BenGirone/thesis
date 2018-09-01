package thesis;

import processing.core.PApplet;
import thesis.Lane;

public class Program extends PApplet {

	public static void main(String[] args) {
		PApplet.main("thesis.Program");
	}

	public void settings() {
		size(Globals.globalHeight, Globals.globalWidth);
	}

	void drawRoad() {
		rectMode(CORNER);

		strokeWeight(3);
		stroke(255, 255, 255);
		fill(24, 120, 19);
		rect(0, 0, Globals.globalWidth * 0.4f, Globals.globalWidth * 0.4f);
		rect(0, Globals.globalWidth * 0.6f, Globals.globalWidth * 0.4f, Globals.globalWidth * 0.4f);
		rect(Globals.globalWidth * 0.6f, 0, Globals.globalWidth * 0.4f, Globals.globalWidth * 0.4f);
		rect(Globals.globalWidth * 0.6f, Globals.globalWidth * 0.6f, Globals.globalWidth * 0.4f,
				Globals.globalWidth * 0.4f);

		stroke(240, 237, 79);
		line((Globals.globalWidth * 0.5f) - (Globals.globalWidth * 0.005f), 0,
				(Globals.globalWidth * 0.5f) - (Globals.globalWidth * 0.005f), Globals.globalWidth * 0.4f);
		line((Globals.globalWidth * 0.5f) + (Globals.globalWidth * 0.005f), 0,
				(Globals.globalWidth * 0.5f) + (Globals.globalWidth * 0.005f), Globals.globalWidth * 0.4f);
		line((Globals.globalWidth * 0.5f) - (Globals.globalWidth * 0.005f), Globals.globalWidth,
				(Globals.globalWidth * 0.5f) - (Globals.globalWidth * 0.005f), Globals.globalWidth * 0.6f);
		line((Globals.globalWidth * 0.5f) + (Globals.globalWidth * 0.005f), Globals.globalWidth,
				(Globals.globalWidth * 0.5f) + (Globals.globalWidth * 0.005f), Globals.globalWidth * 0.6f);
		line(0, (Globals.globalWidth * 0.5f) - (Globals.globalWidth * 0.005f), Globals.globalWidth * 0.4f,
				(Globals.globalWidth * 0.5f) - (Globals.globalWidth * 0.005f));
		line(0, (Globals.globalWidth * 0.5f) + (Globals.globalWidth * 0.005f), Globals.globalWidth * 0.4f,
				(Globals.globalWidth * 0.5f) + (Globals.globalWidth * 0.005f));
		line(Globals.globalWidth, (Globals.globalWidth * 0.5f) - (Globals.globalWidth * 0.005f),
				Globals.globalWidth * 0.6f, (Globals.globalWidth * 0.5f) - (Globals.globalWidth * 0.005f));
		line(Globals.globalWidth, (Globals.globalWidth * 0.5f) + (Globals.globalWidth * 0.005f),
				Globals.globalWidth * 0.6f, (Globals.globalWidth * 0.5f) + (Globals.globalWidth * 0.005f));

		rectMode(CENTER);
	}

	public void setup() {
		rectMode(CENTER);
		Globals.canvas = this;
	}

	Lane laneNorth = new Lane('N');
	Lane laneSouth = new Lane('S');
	Lane laneEast = new Lane('E');
	Lane laneWest = new Lane('W');

	public void draw() {
		clear();

		background(65, 65, 65);

		drawRoad();

		fill(255, 255, 255);
		noStroke();

		laneNorth.simulate();
		laneSouth.simulate();
		laneEast.simulate();
		laneWest.simulate();

		laneNorth.renderCars();
		laneSouth.renderCars();
		laneEast.renderCars();
		laneWest.renderCars();
	}

}
