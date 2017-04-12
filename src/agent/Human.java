package agent;

public class Human extends Agent {
//	boolean live;
	Human (int x, int y){
		this.x = x;
		this.y = y;
//		live = true;
	}
	
	public Human(Human h){
		this.x = h.x;
		this.y = h.y;
	}
	
	public void killed(){
//		live = false;
	}

}
