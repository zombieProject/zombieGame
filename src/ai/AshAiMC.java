package ai;

import game.Scene;
import ai.montecarlo.MonteCarloSearchTree;

public class AshAiMC implements AshAi{
	Scene scene;
	public String move(Scene s){
		scene = new Scene(s);
		MonteCarloSearchTree root = new MonteCarloSearchTree(scene);
		//search depth, sample size
		return root.getMove(3,50);
	}

}
