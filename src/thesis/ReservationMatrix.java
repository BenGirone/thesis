package thesis;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import processing.core.PApplet;
import processing.core.PVector;

public class ReservationMatrix implements AnimatedObject {

	private PVector position;
	public float size;
	private Vector<Vector<PVector>> spaceTable;
	public static Map<String, ArrayList<Integer>> spaceTimeTable;

	public ReservationMatrix(Intersection intersection) {
		this.position = intersection.position;
		this.size = Lane.laneWidth*10.0f;
		spaceTimeTable = new Hashtable<String, ArrayList<Integer>>();
		
		this.spaceTable = new Vector<Vector<PVector>>();
		Vector<PVector> currentRow;
		for (float i = (position.x - (size/2.0f - Globals.pixelsPerFoot*2.5f)); i < position.x + this.size/2.0f; i+=Globals.pixelsPerFoot*5) {
			currentRow = new Vector<PVector>();
			for (float j = (position.y - (size/2.0f - Globals.pixelsPerFoot*2.5f)); j < position.y + this.size/2.0f; j+=Globals.pixelsPerFoot*5) {
				currentRow.addElement(new PVector(i, j));
				spaceTimeTable.put(currentRow.lastElement().toString(), new ArrayList<Integer>());
			}
			spaceTable.addElement(currentRow);
		}
	}
	
	public boolean reserve(PVector position, long time) {
		time = time/100;
		
		return false;
	}

	@Override
	public void render() {
		Globals.canvas.rectMode(PApplet.CENTER);
		Globals.canvas.stroke(255);
		Globals.canvas.noFill();
		Globals.canvas.rect(this.position.x, this.position.y, this.size, this.size);
		for (int i = 0; i < spaceTable.size(); i++) {
			for (int j = 0; j < spaceTable.get(i).size(); j++) {
				Globals.canvas.rect(spaceTable.get(i).get(j).x, spaceTable.get(i).get(j).y, Globals.pixelsPerFoot*5, Globals.pixelsPerFoot*5);
			}
		}
	}

	@Override
	public void simulate() {
		// TODO Auto-generated method stub
		
	}

}
