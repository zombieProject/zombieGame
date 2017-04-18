package ai;

import ai.maxmin.FinalStrategy;
import ai.maxmin.MaxMinSearch;
import game.Scene;

public class AshAiMM implements AshAi {
    private MaxMinSearch maxMinSearch;

    public AshAiMM(){
        maxMinSearch = new MaxMinSearch(new FinalStrategy());
    }

    @Override
    public String move(Scene s) {
        return maxMinSearch.searchMove(3, s);
    }
}
