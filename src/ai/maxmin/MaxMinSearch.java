package ai.maxmin;

import game.Scene;

import java.util.List;

public class MaxMinSearch {
    private SearchStrategy strategy;

    public MaxMinSearch(SearchStrategy searchStrategy){
        this.strategy = searchStrategy;
    }

    public int searchHeuristicScore(int depth, Scene scene, Scene origin){
        if(depth <= 0 || !scene.getStatus().equals("ongoing")) return strategy.heuristic(scene, origin);
        int maxScore = Integer.MIN_VALUE;
        List<String> moves = strategy.nextMoves(scene, origin);
        for(String move : moves){
            Scene newScene = new Scene(scene);
            newScene.nextScene(move);
            int value = searchHeuristicScore(depth - 1, newScene, origin);
            if(value > maxScore) maxScore = value;
        }
        return maxScore;
    }

    public String searchMove(int depth, Scene scene){
        int bestScore = Integer.MIN_VALUE;
        String bestMove = null;
        List<String> moves = strategy.nextMoves(scene, scene);
        for(String move : moves) {
            Scene newScene = new Scene(scene);
            newScene.nextScene(move);
            int score = searchHeuristicScore(depth - 1, newScene, scene);
            if(score > bestScore){
                bestScore = score;
                bestMove = move;
                strategy.heuristic(newScene, scene);
            }
        }
        return bestMove;
    }
}
