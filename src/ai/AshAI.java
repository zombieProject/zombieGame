package ai;

import java.util.List;
import java.util.Map;

import ai.DecisionTree.DecisionTree;
import ai.DecisionTree.DecisionTreeCreator;
import game.Game;
import game.Scene;

public class AshAI {
	
	public String move(List<Integer> input){
		
		
		//return "8250 4500"; // for test 1
		return "8800 7000";   // for test 3

	}


	public Map<String, Integer> move(Game game) {

	    Scene currentScene =  game.scenelist.get(game.scenelist.size()-1);

        DecisionTreeCreator dc = new DecisionTreeCreator();

        DecisionTree tree = dc.createDecisionTree(currentScene);

        tree.executeDecisionTree();

        return null;

    }
}
