package ai;

import java.util.List;

import game.Scene;
import montecarlo.MonteCarloSearchTree;

public class AshAiMC implements AshAi{
	Scene scene;
	
	
	@Override
	public String move(List<Integer> input) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String move(Scene s){
		scene = s.sceneCopy();
		MonteCarloSearchTree root = new MonteCarloSearchTree(scene);
		//search depth, sample size
		return root.getMove(3, 100);
		//return root.getMoveByProbability(4, 100);
	}

}
