package agent;

import java.util.Random;

public class Human extends Agent {
	private static final Random random = new Random();

	// Constructors for Human
	
	Human (int x, int y){
		this.x = x;
		this.y = y;
		rotate = random.nextInt(360);
	}
	
	public Human(Human h){
		this.x = h.x;
		this.y = h.y;
		this.rotate = h.rotate;
	}
	
/*	public void killed(){
		
	}*/

}
