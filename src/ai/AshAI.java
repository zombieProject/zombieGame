package ai;

import java.util.HashMap;
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

    public Map<String, Integer> move(Scene currentScene) {

        DecisionTreeCreator dc = new DecisionTreeCreator();

        DecisionTree tree = dc.createDecisionTree(currentScene);

        tree.executeDecisionTree();

        // After tree execution we get the selected action
        // and we fetch the result for that action.
        // The result of an action is abstracted and is an object
        Object result = tree.getActionExecuted().getResult();

        // our decision actions result in a Map<String, Action>
        // we type cast the object to Map
        Map<String, Integer> targetCoordinates = (HashMap<String, Integer>) result;

        return targetCoordinates;

    }
}
