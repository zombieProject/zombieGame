package game;

import java.util.ArrayList;
import java.util.List;

import agent.Ash;
import agent.Human;
import agent.Zombie;
import ai.AshAI;

public class Game {
//	The game is played in a zone 16000 units wide by 9000 units high. You control a man named Ash, wielding a gun that lets him kill any zombie within a certain range around him.
//
//	Ash works as follows:
//	Ash can be told to move to any point within the game zone by outputting a coordinate X Y. The top-left point is 0 0.
//	Each turn, Ash will move exactly 1000 units towards the target coordinate, or onto the target coordinates if he is less than 1000 units away.
//	If at the end of a turn, a zombie is within 2000 units of Ash, he will shoot that zombie and destroy it. More details on combat further down.
//
//	Other humans will be present in the game zone, but will not move. If zombies kill all of them, you lose the game and score 0 points for the current test case.
//
//	Zombies are placed around the game zone at the start of the game, they must be destroyed to earn points.
//
//	Zombies work as follows:
//	Each turn, every zombie will target the closest human, including Ash, and step 400 units towards them. If the zombie is less than 400 units away, the human is killed and the zombie moves onto their coordinate.
//	Two zombies may occupy the same coordinate.
//
//	The order in which actions happens in between two rounds is:
//	Zombies move towards their targets.
//	Ash moves towards his target.
//	Any zombie within a 2000 unit range around Ash is destroyed.
//	Zombies eat any human they share coordinates with.
//
//	Killing zombies earns you points. The number of points you get per zombie is subject to a few factors.
//
//	Scoring works as follows:
//	A zombie is worth the number of humans still alive squared x10, not including Ash.
//	If several zombies are destroyed during on the same round, the nth zombie killed's worth is multiplied by the (n+2)th number of the Fibonnacci sequence (1, 2, 3, 5, 8, and so on). As a consequence, you should kill the maximum amount of zombies during a same turn.
//
//	Note: You may activate gory mode in the settings panel () if you have the guts for it.
//	 
//	Victory Conditions
//	You destroy every zombie on screen with at least 1 other living human remaining.
//	 
//	Lose Conditions
//	The zombies kill every human other than you.
//	 	Expert Rules
//
//	The coordinate system of the game uses whole numbers only. If Ash or a zombie should land in a non whole coordinate, that coordinate is rounded down.
//
//	For example, if a zombie were to move from X=0, Y=0 towards X=500, Y=500, since it may only travel 400 units in one turn it should land on X=282.843, Y=282.843 but will in fact land on X=282, Y=282.
//
//	To help, each zombie's future coordinates will be sent along side the current coordinates.

	int totalscore;
	List<Scene> scenelist;
	
	public Game(Scene s){
		totalscore = 0;
		scenelist = new ArrayList<Scene>();
		scenelist.add(s);
	}
	
	
	public void run(){
		while(scenelist.get(scenelist.size()-1).getStatus().equals("ongoing"))
		{
			Scene nextscene = new Scene(scenelist.get(scenelist.size()-1));
			nextscene.nextScene();
			scenelist.add(nextscene);
		}
		
		Scene lastscene = scenelist.get(scenelist.size()-1);
		
		System.out.println("RESULT: " + lastscene.getStatus());
		System.out.println("YOR SCORE: " + lastscene.getScore());
		
	}
	
	public static void main(String args[]){
		
		// need to modify for your own location
		Scene initialScene = new Scene("testcase/testcase1.txt");
		Game g = new Game(initialScene);
		g.run();
	}

	

}
