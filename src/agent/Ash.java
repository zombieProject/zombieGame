package agent;

import ui.RootUI;

public class Ash extends Agent {
	private double rotate = 0;

	public Ash(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Ash(Ash a){
		x = a.x;
		y = a.y;
		rotate = a.rotate;
	}

	public double getRotate() {
		return rotate;
	}

	@Override
	public void setDestination(int x, int y) {
		int oldX = this.x;
		int oldY = this.y;
		super.setDestination(x, y);

		rotate = RootUI.getRotation(oldX, oldY, this.x, this.y);
	}
}
