package agent;

public class Ash extends Agent {
	
	// Constructors for Ash
	
	public Ash(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Ash(Ash a){
		x = a.x;
		y = a.y;
		rotate = a.rotate;
	}
}
