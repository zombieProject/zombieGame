package GameTester;

import ai.AshAi;
import ai.AshAiDT;
import ai.AshAiMC;
import game.Game;
import game.Scene;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
/**
 * Created by shubhimittal on 4/14/17.
 */
public class GameTest {

    private Scene gameScene;
    private String message;
    private static int score;
    private  AshAi ai;

    public final static List<Supplier<AshAi>> factories = Arrays.asList(
            () -> new AshAiDT(),
            () -> new AshAiMC()
    );


    private void initGameEnvironment(int episodeNumber, AshAi ai) {
        String testCaseFilePath =  "testcase/testcase" + episodeNumber  + ".txt";
        Scene initialScene = new Scene(testCaseFilePath);
        Game g = new Game(initialScene);
        g.run(ai);
        gameScene = g.scenelist.get(g.scenelist.size()-1);

        message = "Test Scenario " + episodeNumber + " :Score " + gameScene.getScore() + " , Status " + gameScene
                .getStatus();
    }

    @Test
    public void testSteam(){
        ArrayList<Integer> list = new ArrayList<>(5);
        for(int i = 0; i < 5; i++) list.add(i);
        Stream<Integer> multi2 = list.stream().map(i -> i * 2);
        Stream<Integer> add1 = list.stream().map(i -> i + 1);
        List<Integer> list2 = multi2.collect(Collectors.toList());
        List<Integer> list3 = add1.collect(Collectors.toList());
        System.out.println(list2);
        System.out.println(list3);
    }


    @AfterClass
    public static void printScore(){
        System.out.println("Total Score: " + score);
    }

    public static class Pair<X, Y>{
        public final X x;
        public final Y y;

        public Pair(X x, Y y){ this.x = x; this.y = y; }
    }

    public void gameScenarioCommon(int level){
        Stream<AshAi> ashAis = factories.stream().map((s) -> s.get());
        final String testCaseFilePath =  "testcase/testcase" + level  + ".txt";
        ashAis.forEach(ashAi -> {
            Game game = new Game(new Scene(testCaseFilePath));
            game.run(ashAi);
            Scene scene = game.scenelist.get(game.scenelist.size() - 1);
            score += scene.getScore();
            String message = "Test Scenario " + level + " :Score " + scene.getScore()
                     + " AI: " + ashAi.getClass().getName() + " , Status " + scene.getStatus();
            assertEquals(message, scene.getStatus(), "pass");
        });
    }

    @Test
    public void gameScenario1(){
        gameScenarioCommon(1);
    }

    @Test
    public void gameScenario2(){
        gameScenarioCommon(2);
    }

    @Test
    public void gameScenario3(){
        gameScenarioCommon(3);
    }

    @Test
    public void gameScenario4(){
        gameScenarioCommon(4);
    }

    @Test
    public void gameScenario5(){
        gameScenarioCommon(5);
    }

    @Test
    public void gameScenario6(){
        gameScenarioCommon(6);
    }

    @Test
    public void gameScenario7(){
        gameScenarioCommon(7);
    }

    @Test
    public void gameScenario8(){
        gameScenarioCommon(8);
    }

    @Test
    public void gameScenario9(){
        gameScenarioCommon(9);
    }

    @Test
    public void gameScenario10(){
        gameScenarioCommon(10);
    }

    @Test
    public void gameScenario11(){
        gameScenarioCommon(11);
    }

    @Test
    public void gameScenario12(){
        gameScenarioCommon(12);
    }

    @Test
    public void gameScenario13(){
        gameScenarioCommon(13);
    }

    @Test
    public void gameScenario14(){
        gameScenarioCommon(14);
    }

    @Test
    public void gameScenario15(){
        gameScenarioCommon(15);
    }

    @Test
    public void gameScenario16(){
        gameScenarioCommon(16);
    }

    @Test
    public void gameScenario17(){
        gameScenarioCommon(17);
    }

    @Test
    public void gameScenario18(){
        gameScenarioCommon(18);
    }

    @Test
    public void gameScenario19(){
        gameScenarioCommon(19);
    }

    @Test
    public void gameScenario20(){
        gameScenarioCommon(20);
    }

    @Test
    public void gameScenario21(){
        gameScenarioCommon(21);
    }
}
