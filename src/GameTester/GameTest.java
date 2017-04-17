package GameTester;

import ai.AshAi;
import game.Game;
import game.Scene;
import org.junit.AfterClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
/**
 * Created by shubhimittal on 4/14/17.
 */
public abstract class GameTest {
    public abstract AshAi createAI();

    private Scene gameScene;
    private String message;
    private static int score;


    private void initGameEnvironment(int episodeNumber) {
        AshAi ai = createAI();
        String testCaseFilePath =  "testcase/testcase" + episodeNumber  + ".txt";
        Scene initialScene = new Scene(testCaseFilePath);
        Game g = new Game(initialScene);
        g.run(ai);
        gameScene = g.scenelist.get(g.scenelist.size()-1);

        message = "Test Scenario " + episodeNumber + " :Score " + gameScene.getScore() + " , Status " + gameScene
                .getStatus();

    }

    @AfterClass
    public static void printScore(){
        System.out.println("Total Score: " + score);
    }

    @Test
    public void gameScenario1(){
        initGameEnvironment(1);
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario2(){
        initGameEnvironment(2);
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario3(){
        initGameEnvironment(3);
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario4(){
        initGameEnvironment(4);
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario5(){
        initGameEnvironment(5);
        score = score + gameScene.getScore();
        System.out.println(gameScene.getScore());
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario6(){
        initGameEnvironment(6);
        score = score + gameScene.getScore();
        System.out.println(gameScene.getScore());
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario7(){
        initGameEnvironment(7);
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario8(){
        initGameEnvironment(8);
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }
    @Test
    public void gameScenario9(){
        initGameEnvironment(9);
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }
    @Test
    public void gameScenario10(){
        initGameEnvironment(10);
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }
    @Test
    public void gameScenario11(){
        initGameEnvironment(11);
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }
    @Test
    public void gameScenario12(){
        initGameEnvironment(12);
        score = score + gameScene.getScore();
        System.out.println(gameScene.getScore());
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario13(){
        initGameEnvironment(13);
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario14(){
        initGameEnvironment(14);
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario15(){
        initGameEnvironment(15);
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario16(){
        initGameEnvironment(16);
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }

}
