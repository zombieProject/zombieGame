package montecarlo;

import game.Scene;

public class MonteCarlo {
	
	
//	public static int randomX(){
////		double random = Math.random()*(Scene.RIGHT+1);
//		double random = Scene.ASH_LIMIT - Math.random()*Scene.ASH_LIMIT*2;
//		return (int)Math.floor(random);
//	}
	
//	public static int randomY(){
////		double random = Math.random()*(Scene.DOWN+1);
//		double random = Scene.ASH_LIMIT - Math.random()*Scene.ASH_LIMIT*2;
//		return (int)Math.floor(random);
//	}
//	

	public static Location randomLocation(int ashx, int ashy){
		double randomr = Math.random()*(Scene.SHOOTING_RANGE*1.4);
		double randomAngle = Math.random()*360;
		
		int x = (int) (Math.sin( randomAngle * Math.PI / 180) * randomr)+ashx;
		int y = (int) (Math.sin( randomAngle * Math.PI / 180) * randomr)+ashy;
		
		if(x<Scene.LEFT){
			x = -x;
		}else if(x>Scene.RIGHT){
			x = 2*Scene.RIGHT-x;
		}
		
		if(y<Scene.TOP){
			y = -y;
		}else if(x>Scene.DOWN){
			y = 2*Scene.DOWN-y;
		}
		
		return new Location(x,y);
	}

public static void main(String[] args) {

}
}
