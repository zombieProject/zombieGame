package ai.maxmin;

import agent.Ash;
import agent.Human;
import game.Scene;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NearestHumanStrategy implements SearchStrategy {
    @Override
    public int heuristic(Scene scene, Scene origin) {
        return 1000;
    }

    @Override
    public List<String> nextMoves(Scene scene, Scene origin) {
        Ash ash = scene.getAsh();
        Human target = getNearestHuman(ash.getX(), ash.getY(), scene.getHumanlist().values());
        return Collections.singletonList("" + target.getX() + " " + target.getY());
    }

    public static Human getNearestHuman(int x, int y, Collection<Human> humans){
        Human target = null;
        double maxD = Double.MAX_VALUE;
        for (Human human : humans){
            double d = FinalStrategy.distance(x, y, human);
            if(d < maxD){
                maxD = d;
                target = human;
            }
        }
        return target;
    }
}
