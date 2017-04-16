package ai.montecarlo;

import game.Scene;

public class MonteCarlo {
	
	
	public static int randomX(){
//		double random = Math.random()*(Scene.RIGHT+1);
		double random = Scene.ASH_LIMIT - Math.random()*Scene.ASH_LIMIT*2;
		return (int)Math.floor(random);
	}
	
	public static int randomY(){
//		double random = Math.random()*(Scene.DOWN+1);
		double random = Scene.ASH_LIMIT - Math.random()*Scene.ASH_LIMIT*2;
		return (int)Math.floor(random);
	}
	
	

public static void main(String[] args) {

}
}
