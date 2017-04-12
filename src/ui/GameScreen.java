package ui;

import agent.Agent;
import game.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import static ui.RootUI.radius;
import static ui.RootUI.scale;

public class GameScreen {
    private Canvas canvas;

    public GameScreen(){
        canvas = new Canvas(scale(Scene.RIGHT), scale(Scene.DOWN));
    }

    public void appendToPane(Pane pane){
        pane.getChildren().add(canvas);
    }

    public static void drawCircle(GraphicsContext gc, Agent agent){
        double r = scale(radius);
        gc.fillArc(scale(agent.getX()), scale(agent.getY()), r, r, 0, 360, ArcType.ROUND);
    }

    public void drawScene(Scene scene){
        double r = scale(radius);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.YELLOW);
        drawCircle(gc, scene.getAsh());
        gc.setFill(Color.RED);
        for(Agent zombie : scene.getZombielist().values()){
            drawCircle(gc, zombie);
        }
        gc.setFill(Color.GREEN);
        for(Agent human : scene.getHumanlist().values()){
            drawCircle(gc, human);
        }

        gc.fill();
    }
}
