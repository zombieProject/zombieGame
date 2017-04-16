package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.Human;
import ai.DecisionTree.DecisionTree;
import ai.DecisionTree.DecisionTreeCreator;
import game.Scene;
import game.SceneAPI;

public class AshAiDT implements AshAi {

    public String move(Scene currentScene) {

//        DecisionTreeCreator dc = new DecisionTreeCreator();
//
//        DecisionTree tree = dc.createDecisionTree(currentScene);
//
//        tree.executeDecisionTree();
//
//        // After tree execution we get the selected action
//        // and we fetch the result for that action.
//        // The result of an action is abstracted and is an object
//        Object result = tree.getActionExecuted().getResult();
//
//        // our decision actions result in a Map<String, Action>
//        // we type cast the object to Map
//        Map<String, Integer> targetCoordinates = (HashMap<String, Integer>) result;
//
//        return targetCoordinates;

        SceneAPI sceneAPI = new SceneAPI(currentScene.getFilePath());
//        Map<String, Integer> target = sceneAPI.moveToClosestHuman();
//        if (sceneAPI.distance(
//                sceneAPI.getAsh().getX(),
//                sceneAPI.getAsh().getX(),
//                target.get("x"), target.get("y")) > 15000) {
//            return  sceneAPI.moveToClosestZombie();
//        } else {
//            return sceneAPI.moveToClosestHuman();
//        }

//        List<Human> humans = new ArrayList<>();
//
//        Map<String, Integer> target = new HashMap<>();
//        for(Map.Entry<Integer, Human> humanEntry: sceneAPI.getHumanlist().entrySet()) {
//
//            Human h = humanEntry.getValue();
//
//            double distance = sceneAPI.distance(
//                    sceneAPI.getAsh().getX(), sceneAPI.getAsh().getY(), h.getX(), h.getY());
//
//            if(distance < sceneAPI.SHOOTING_RANGE) {
//                humans.add(h);
//
//            }
//
//        }
//
//        if(humans.size() > 0) {
//
//            target.put("x", humans.get(0).getX());
//            target.put("y", humans.get(0).getY());
//        } else {
//
//
//            target = sceneAPI.moveToClosestZombie();
//
//        }


        Map<String, Integer> target = sceneAPI.moveToClosestHuman();
        String targetMove = target.get("x") + " " + target.get("y");
        return targetMove;
        //return target;

    }
}
