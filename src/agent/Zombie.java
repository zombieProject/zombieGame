package agent;

public class Zombie extends Agent {

	// Constructors for Zombie
	
	Zombie(int x, int y){
		this.x = x;
		this.y = y;

	}
	
	public Zombie(Zombie z){
		this.x = z.x;
		this.y = z.y;
		this.rotate = z.rotate;
	}
	
/*	public void killed(){

	} */

}
