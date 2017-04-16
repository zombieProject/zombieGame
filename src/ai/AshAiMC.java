package ai;

import java.util.List;

import game.Scene;
import ai.montecarlo.MonteCarloSearchTree;

public class AshAiMC implements AshAi{
	Scene scene;
	public String move(Scene s){
		scene = new Scene(s);
		MonteCarloSearchTree root = new MonteCarloSearchTree(scene);
		//search depth, sample size
		return root.getMove(5, 100);
	}

}
