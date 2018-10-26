package thesis;

import java.util.Vector;

import processing.core.PApplet;
import processing.core.PVector;

public class ReservationMatrix implements AnimatedObject {

	private PVector position;
	private float size;
	private Vector<Vector<Integer>> spaceTable;

	public ReservationMatrix(Intersection intersection) {
		this.position = intersection.position;
		this.size = Lane.laneWidth*10.0f;
		
		this.spaceTable = new Vector<Vector<Integer>>();
		Vector<Integer> currentRow;
		for (float i = (position.x - (size/2.0f - Globals.pixelsPerFoot*2.5f)); i < position.x + this.size/2.0f; i+=Globals.pixelsPerFoot*5) {
			currentRow = new Vector<Integer>();
			spaceTable.addElement(currentRow);
			for (float j = (position.y - (size/2.0f - Globals.pixelsPerFoot*2.5f)); j < position.y + this.size/2.0f; j+=Globals.pixelsPerFoot*5) {
				currentRow.addElement(0);
			}
		}
	}

	@Override
	public void render() {
		Globals.canvas.rectMode(PApplet.CENTER);
		Globals.canvas.stroke(255);
		Globals.canvas.noFill();
		Globals.canvas.rect(this.position.x, this.position.y, this.size, this.size);
		for (float i = (position.x - (size/2.0f - Globals.pixelsPerFoot*2.5f)); i < position.x + this.size/2.0f; i+=Globals.pixelsPerFoot*5) {
			for (float j = (position.y - (size/2.0f - Globals.pixelsPerFoot*2.5f)); j < position.y + this.size/2.0f; j+=Globals.pixelsPerFoot*5) {
				Globals.canvas.rect(i, j, Globals.pixelsPerFoot*5, Globals.pixelsPerFoot*5);
			}
		}
	}

	@Override
	public void simulate() {
		// TODO Auto-generated method stub
		
	}

}
