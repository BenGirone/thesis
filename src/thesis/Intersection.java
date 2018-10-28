package thesis;

import processing.core.PConstants;
import processing.core.PVector;

public class Intersection implements AnimatedObject {
	public PVector position;
	float laneLength;
	public Lane laneN;
	public Lane laneS;
	public Lane laneE;
	public Lane laneW;
	
	private StaticRect knoll_1;
	private StaticRect knoll_2;
	private StaticRect knoll_3;
	private StaticRect knoll_4;
	public ReservationMatrix reservationMatrix;

	Intersection(PVector position, float laneLength) {
		this.position = position;
		this.laneLength = laneLength;
		
		laneN = new Lane('N', position.copy().add(new PVector(Lane.laneWidth/2.0f,0)), laneLength, true, this);
		laneS = new Lane('S', position.copy().add(new PVector(-1 * Lane.laneWidth/2.0f,0)), laneLength, true, this);
		laneE = new Lane('E', position.copy().add(new PVector(0,Lane.laneWidth/2.0f)), laneLength, true, this);
		laneW = new Lane('W', position.copy().add(new PVector(0,-1 * Lane.laneWidth/2.0f)), laneLength, true, this);
		
		this.knoll_1 = new StaticRect(new float[] {position.x - laneLength/2.0f, position.y - laneLength/2.0f, position.x - Lane.laneWidth, position.y - Lane.laneWidth, 0, 0, laneLength/7.5f, 0});
		this.knoll_2 = new StaticRect(new float[] {position.x + laneLength/2.0f, position.y - laneLength/2.0f, position.x + Lane.laneWidth, position.y - Lane.laneWidth, 0, 0, 0, laneLength/7.5f});
		this.knoll_3 = new StaticRect(new float[] {position.x - laneLength/2.0f, position.y + laneLength/2.0f, position.x - Lane.laneWidth, position.y + Lane.laneWidth, 0, laneLength/7.5f, 0, 0});
		this.knoll_4 = new StaticRect(new float[] {position.x + laneLength/2.0f, position.y + laneLength/2.0f, position.x + Lane.laneWidth, position.y + Lane.laneWidth, laneLength/7.5f, 0, 0, 0});
	
		this.reservationMatrix = new ReservationMatrix(this);
	}

	@Override
	public void render() {
		Globals.canvas.rectMode(PConstants.CENTER);
		Globals.canvas.fill(65, 65, 65);
		Globals.canvas.rect(position.x, position.y, laneLength, laneLength);
		
		Globals.canvas.fill(24, 120, 19);
		Globals.canvas.rectMode(PConstants.CORNERS);
		
		knoll_1.render();
		knoll_2.render();
		knoll_3.render();
		knoll_4.render();
		
		laneN.render();
		laneS.render();
		laneE.render();
		laneW.render();
		
		reservationMatrix.render();
	}

	@Override
	public void simulate() {
		laneN.simulate();
		laneS.simulate();
		laneE.simulate();
		laneW.simulate();
	}
}
