
package ui;

import game.Scene;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class RootUI extends Application {
    public static final int scaleFactor = 20;
    public static final double menuHeight = 500;
    public static final double radius = 400;
    public static final double padding = 1000;

    // images for game
    public static final Image ashImage = new Image(readFile("image/ash.png"));
    public static final Image humanImage = new Image(readFile("image/human.png"));
    public static final Image zombieImage = new Image(readFile("image/zombie.png"));

    private static RootUI instance;
    private static CountDownLatch latch = new CountDownLatch(1);
    private static AtomicReference<List<Scene>> tempScenes = new AtomicReference<>();

    public static final double scale(double coordinate){
        return coordinate / scaleFactor;
    }

    public static void createGUI(List<Scene> scenes){
        tempScenes.set(scenes);
        launch();
    }

    public static RootUI getInstance() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return instance;
    }

    private static void setInstance(RootUI instance) {
        RootUI.instance = instance;
        latch.countDown();
    }

    public static InputStream readFile(String filePath){
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static double getRotation(int oldX, int oldY, int newX, int newY){
        int deltaX = newX - oldX;
        int deltaY = newY - oldY;
        return Math.toDegrees(Math.atan2(deltaY, deltaX)) + 90;
    }

    private GameScreen gameScreen;
    private ControlBar controlBar;
    private List<Scene> scenes;
    private int index = -1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        scenes = tempScenes.getAndSet(null);
        setInstance(this);
        primaryStage.setTitle("code vs zombies");
        VBox vBox = new VBox();

        gameScreen = new GameScreen();
        gameScreen.appendToPane(vBox);

        controlBar = new ControlBar();
        controlBar.appendToPane(vBox);

        primaryStage.show();

        nextScene();
        controlBar.setCallback(this::nextScene, this::prevScene, this::play);

        double totalHeight = gameScreen.getHeight() + scale(menuHeight + padding);
        javafx.scene.Scene scene = new javafx.scene.Scene(vBox);
        primaryStage.setScene(scene);
    }

    public boolean nextScene(){
        if(index >= scenes.size() - 1) return false;
        index++;
        Scene scene = scenes.get(index);
        gameScreen.drawScene(scene);
        controlBar.setProcess(index, scenes.size());
        return true;
    }

    public boolean prevScene(){
        if(index <= 0) return true;
        index--;
        Scene scene = scenes.get(index);
        gameScreen.drawScene(scene);
        controlBar.setProcess(index, scenes.size());
        return true;
    }

    public void play(){
        final long[] prevTime = {System.currentTimeMillis()};
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                now = System.currentTimeMillis();
                if(now - prevTime[0] > 400L){
                    prevTime[0] = now;
                    if(!nextScene()){
                        this.stop();
                    }
                }
            }
        }.start();
    }
}
