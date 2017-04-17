package agent;

public class Agents {
	
	// make use of static factory design pattern
	// using static methods to create an object
	
	public static Ash makeAsh(int x, int y){
		return new Ash( x, y);
	}  
	
	public static Zombie makeZombie(int x, int y){
		return new Zombie( x, y);
	}  
	
	public static Human makeHuman(int x, int y){
		return new Human( x, y);
	}  

}
