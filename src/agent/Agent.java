package agent;

import ui.RootUI;

public class Agent {
	
	// x and y are coordination
	
	int x;
	int y;
	
	// the degree to turn around
	
	double rotate = 0;
	
	// getter and setter
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getRotate() {
		return rotate;
	}

	
	// methods determine if the agent is zombie, human or Ash
	
	public boolean isZombie(){
		return this instanceof Zombie;

	}

	public boolean isHuman(){
		return this instanceof Human;

	}

	public boolean isAsh(){
		return this instanceof Ash;
	}
	

	
	@Override
	public String toString() {
		return "Agent [x=" + x + ", y=" + y + "]";
	}

	public void setDestination(int x, int y){
		if(this.x == x && this.y == y) return;
		rotate = RootUI.getRotation(this.x, this.y, x, y);

		this.x = x;
		this.y = y;
	}

}
