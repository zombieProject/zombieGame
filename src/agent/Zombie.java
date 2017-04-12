package agent;

public class Zombie extends Agent {
//	boolean live;
	Zombie(int x, int y){
		this.x = x;
		this.y = y;
//		live = true;
	}
	
	public Zombie(Zombie z){
		this.x = z.x;
		this.y = z.y;
	}
	
	public void killed(){
//		live = false;
	}

}
