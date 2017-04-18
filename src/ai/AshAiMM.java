package ai;

import ai.maxmin.FinalStrategy;
import ai.maxmin.MaxMinSearch;
import ai.maxmin.NearestHumanStrategy;
import game.Scene;

public class AshAiMM implements AshAi {
    private MaxMinSearch maxMinSearch;

    public AshAiMM(){
        maxMinSearch = new MaxMinSearch(new FinalStrategy());
       // maxMinSearch = new MaxMinSearch(new NearestHumanStrategy());
    }

    @Override
    public String move(Scene s) {
        return maxMinSearch.searchMove(3, s);
    }
}
