package agent;

public class Zombie extends Agent {
//	boolean live;
	Zombie(int x, int y){
		this.x = x;
		this.y = y;
//		live = true;
	}
	
	public void killed(){
//		live = false;
	}

}
