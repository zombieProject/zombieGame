package ui;

import game.Scene;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class RootUI extends Application {
    public static final int scaleFactor = 15;
    public static final double menuHeight = 500;
    public static final double radius = 800;
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
        double totalHeight = Scene.DOWN + menuHeight;
        javafx.scene.Scene scene = new javafx.scene.Scene(vBox, scale(Scene.RIGHT), scale(totalHeight));
        primaryStage.setScene(scene);

        gameScreen = new GameScreen();
        gameScreen.appendToPane(vBox);

        controlBar = new ControlBar();
        controlBar.appendToPane(vBox);

        primaryStage.show();

        nextScene();

        controlBar.setCallback(this::nextScene, this::prevScene, this::play);
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
        while (nextScene()){
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
