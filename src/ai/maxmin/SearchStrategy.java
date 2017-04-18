package ai.maxmin;

import game.Scene;

import java.util.List;

public interface SearchStrategy {
    int heuristic(Scene scene, Scene origin);
    List<String> nextMoves(Scene scene, Scene origin);
}
