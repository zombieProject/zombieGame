package GameTester;

import ai.AshAI;
import game.Game;
import game.Scene;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
/**
 * Created by shubhimittal on 4/14/17.
 */
public class GameTest {

    private Scene gameScene;
    private String message;

    private void initGameEnvironment(int episodeNumber) {

        String testCaseFilePath =  "testcase/testcase" + episodeNumber  + ".txt";
        Scene initialScene = new Scene(testCaseFilePath);
        Game g = new Game(initialScene);
        AshAI ashAI = new AshAI();
        g.run(ashAI);
        gameScene = g.scenelist.get(g.scenelist.size()-1);

        message = "Test Scenario " + episodeNumber + " :Score " + gameScene.getScore() + " , Status " + gameScene
                .getStatus();

    }

    @Test
    public void gameScenario1(){
        initGameEnvironment(1);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario2(){
        initGameEnvironment(2);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario3(){
        initGameEnvironment(3);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario4(){
        initGameEnvironment(4);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario5(){
        initGameEnvironment(5);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario6(){
        initGameEnvironment(6);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario7(){
        initGameEnvironment(7);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario8(){
        initGameEnvironment(8);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario9(){
        initGameEnvironment(9);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario10(){
        initGameEnvironment(10);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario11(){
        initGameEnvironment(11);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario12(){
        initGameEnvironment(12);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario13(){
        initGameEnvironment(13);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario14(){
        initGameEnvironment(14);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario15(){
        initGameEnvironment(15);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario16(){
        initGameEnvironment(16);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario17(){
        initGameEnvironment(17);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario18(){
        initGameEnvironment(18);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario19(){
        initGameEnvironment(19);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario20(){
        initGameEnvironment(20);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario21(){
        initGameEnvironment(21);
        assertEquals(message, gameScene.getStatus(), "pass");
    }

}
