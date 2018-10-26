package thesis;

public class StaticRect implements AnimatedObject {
	
	private float[] params;
	
	public StaticRect(float[] params) {
		if (params.length < 8) {
			this.params = new float[] {0,0,0,0,0,0,0,0};
			System.arraycopy(params, 0, this.params, 0, params.length);
		} else {
			this.params = params;
		}
	}

	@Override
	public void render() {
		Globals.canvas.rect(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
	}

	@Override
	public void simulate() {
		System.err.println("Cannot simulate a static rect");
	}

}
