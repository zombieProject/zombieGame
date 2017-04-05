package agent;

public class Agent {
	int x;
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

	int y;

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

	public void setDestination(int x,int y){
		this.x = x;
		this.y = y;
	}

}
