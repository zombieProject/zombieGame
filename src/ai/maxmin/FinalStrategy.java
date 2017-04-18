package ai.maxmin;

import agent.Agent;
import agent.Human;
import game.Scene;

import java.util.ArrayList;
import java.util.List;

public class FinalStrategy implements SearchStrategy {
    public static boolean print = false;

    @Override
    public int heuristic(Scene scene, Scene origin) {
        Human human = NearestHumanStrategy.getNearestHuman(
                scene.getAsh().getX(),
                scene.getAsh().getY(),
                scene.getHumanlist().values()
        );

        int distance;

        if(human == null){
            return -100000;
        }else{
            distance = distance(scene.getAsh().getX(), scene.getAsh().getY(), human);
            distance = Math.max(distance, 1000);
        }

        return (
                scene.getScore() * 100
                + scene.getHumanlist().size() * 1000
                + Math.min(1000, 1000000 / distance)
        );
    }

    @Override
    public List<String> nextMoves(Scene scene, Scene origin) {
        ArrayList<String> result = new ArrayList<>(dxs.length);
        int ax = scene.getAsh().getX();
        int ay = scene.getAsh().getY();
        for(int i = 0; i < dxs.length; i++){
            int x = ax + dxs[i];
            int y = ay + dys[i];
            result.add("" + x + " " + y);
        }
        return result;
    }

    private final int[] dxs = {0, 707, 1000, 707, 0, -707, -1000, -707, 0};
    private final int[] dys = {-1000, -707, 0, 707, 1000, 707, 0, -707, 0};

    public static int distance(int x, int y, Agent a2){
        return (int) Math.sqrt(square(x - a2.getX()) + square(y - a2.getY()));
    }

    public static int square(int x){
        return x * x;
    }
}
