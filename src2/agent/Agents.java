package agent;

public class Agents {
	
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
