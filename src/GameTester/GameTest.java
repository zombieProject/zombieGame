package GameTester;

import ai.AshAi;
import ai.AshAiDT;
import ai.AshAiMC;
import game.Game;
import game.Scene;

import org.junit.*;

import static junit.framework.TestCase.assertEquals;
/**
 * Created by shubhimittal on 4/14/17.
 */
public class GameTest {

    private Scene gameScene;
    private String message;
    private static int score;
    private  AshAi ai;


    private void initGameEnvironment(int episodeNumber, AshAi ai) {
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
    public void gameScenario1DT(){
        initGameEnvironment(1,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario2DT(){
        initGameEnvironment(2,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario3DT(){
        initGameEnvironment(3,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario4DT(){
        initGameEnvironment(4,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario5DT(){
        initGameEnvironment(5,  new AshAiDT());
        score = score + gameScene.getScore();
        System.out.println(gameScene.getScore());
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario6DT(){
        initGameEnvironment(6,  new AshAiDT());
        score = score + gameScene.getScore();
        System.out.println(gameScene.getScore());
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario7DT(){
        initGameEnvironment(7,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario8DT(){
        initGameEnvironment(8,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario9DT(){
        initGameEnvironment(9,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario10DT(){
        initGameEnvironment(10,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario11DT(){
        initGameEnvironment(11,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario12DT(){
        initGameEnvironment(12,  new AshAiDT());
        score = score + gameScene.getScore();
        System.out.println(gameScene.getScore());
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario13DT(){
        initGameEnvironment(13,  new AshAiDT());
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario14DT(){
        initGameEnvironment(14,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario15DT(){
        initGameEnvironment(15,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario16DT(){
        initGameEnvironment(16,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario17DT(){
        initGameEnvironment(17,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario18DT(){
        initGameEnvironment(18,  new AshAiDT());
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario19DT(){
        initGameEnvironment(19,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario20DT(){
        initGameEnvironment(20,  new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");
    }@Test
    public void gameScenario21DT() {
        initGameEnvironment(21, new AshAiDT());
        score = score + gameScene.getScore();
        assertEquals(message, gameScene.getStatus(), "pass");

    }

}
