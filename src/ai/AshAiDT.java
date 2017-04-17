package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.Agent;
import agent.Human;
import agent.Zombie;
import ai.DecisionTree.DecisionTree;
import ai.DecisionTree.DecisionTreeCreator;
import game.Scene;
import game.SceneAPI;

public class AshAiDT implements AshAi {

    public String move(Scene currentScene) {

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

        return targetCoordinates.get("x") + " " + targetCoordinates.get("y");
//
//        SceneAPI sceneAPI = new SceneAPI(currentScene);
////        Map<String, Integer> target = sceneAPI.moveToClosestHuman();
////        if (sceneAPI.distance(
////                sceneAPI.getAsh().getX(),
////                sceneAPI.getAsh().getX(),
////                target.get("x"), target.get("y")) > 15000) {
////            return  sceneAPI.moveToClosestZombie();
////        } else {
////            return sceneAPI.moveToClosestHuman();
////        }
//
////        List<Human> humans = new ArrayList<>();
////
////        Map<String, Integer> target = new HashMap<>();
////        for(Map.Entry<Integer, Human> humanEntry: sceneAPI.getHumanlist().entrySet()) {
////
////            Human h = humanEntry.getValue();
////
////            double distance = sceneAPI.distance(
////                    sceneAPI.getAsh().getX(), sceneAPI.getAsh().getY(), h.getX(), h.getY());
////
////            if(distance < sceneAPI.SHOOTING_RANGE) {
////                humans.add(h);
////
////            }
////
////        }
////
////        if(humans.size() > 0) {
////
////            target.put("x", humans.get(0).getX());
////            target.put("y", humans.get(0).getY());
////        } else {
////
////
////            target = sceneAPI.moveToClosestZombie();
////
////        }
//
//
////        Map<String, Integer> target = sceneAPI.moveToClosestHuman();
////        String targetMove = target.get("x") + " " + target.get("y");
////        //return targetMove;
////        //return target;
//
//
//        List<Human> humans = new ArrayList<>();
//        Human targetHuman = null;
//        Agent agent = null;
//
//        for (Human h: sceneAPI.getHumanlist().values()) {
//            humans.add(h);
//        }
//
//        int ashX = sceneAPI.getAsh().getX();
//        int ashY = sceneAPI.getAsh().getY();
//
//        humans.sort((o1, o2) -> {
//            return (int) (sceneAPI.distance(ashX, ashY, o1.getX(), o1.getY()) -
//                    sceneAPI.distance(ashX, ashY, o2.getX(), o2.getY()));
//        });
//
//        List<Human> humansToSave = new ArrayList<>();
//
//        humans.forEach((h)-> {
//            boolean danger = false;
//            double distanceFromAsh = sceneAPI.distance(ashX, ashY, h.getX(), h.getY());
//            for(Zombie z: sceneAPI.getZombielist().values()) {
//                double distance = sceneAPI.distance(h.getX(), h.getY(), z.getX(), z.getY());
//                // zombie is very close to this human so
//                // chances are that we might loose this human
//                // we need to save another human
//                if(distance < 600 && distanceFromAsh > 3000) {
//                    danger = true;
//                    break;
//                }
//            }
//            if(! danger) {
//                humansToSave.add(h);
//            }
//        });
//
//
//
//        if(humansToSave.size() > 0) {
//
//            Zombie closestZombie = sceneAPI.moveToClosestZombie();
//            Human closestHuman =  humansToSave.get(0);
//
//            if(sceneAPI.distance(closestHuman.getX(), closestHuman.getY(), ashX, ashY) <
//                    sceneAPI.distance(closestZombie.getX(), closestZombie.getY(), ashX, ashY)) {
//                agent = closestHuman;
//            } else {
//                agent = closestZombie;
//            }
//
//        }
//        else {
//            agent = sceneAPI.moveToClosestZombie();
//            //targetHuman = humans.get(0);
//        }
//
//        return agent.getX() + " " + agent.getY();
//    }
    }
}
