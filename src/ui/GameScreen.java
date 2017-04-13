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
        canvas = new Canvas(scale(Scene.RIGHT + RootUI.padding * 2), scale(Scene.DOWN + RootUI.padding * 2));
    }

    public void appendToPane(Pane pane){
        pane.getChildren().add(canvas);
    }

    public static void drawCircle(GraphicsContext gc, double x, double y, double radius){
        double r = scale(radius);
        double pad = scale(RootUI.padding);
        gc.fillArc(pad + scale(x) - r, pad + scale(y) - r, r * 2, r * 2, 0, 360, ArcType.ROUND);
    }

    public static void drawAgent(GraphicsContext gc, Agent agent){
        drawCircle(gc, agent.getX(), agent.getY(), RootUI.radius);
    }

    public void drawScene(Scene scene){
        double r = scale(radius);
        double pad = scale(RootUI.padding);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth() + pad * 2, canvas.getHeight() + pad * 2);
        gc.setFill(Color.BLACK);
        gc.fillRect(pad + 0, pad + 0, scale(Scene.RIGHT), scale(Scene.DOWN));

        gc.setFill(new Color(0, 0, 1.0, 0.4));
        drawCircle(gc, scene.getAsh().getX(), scene.getAsh().getY(), Scene.SHOOTING_RANGE);

        gc.setFill(Color.YELLOW);
        drawAgent(gc, scene.getAsh());
        gc.setFill(Color.RED);
        for(Agent zombie : scene.getZombieNextlist().values()){
            drawAgent(gc, zombie);
        }
        gc.setFill(Color.GREEN);
        for(Agent human : scene.getHumanlist().values()){
            drawAgent(gc, human);
        }
    }

    public double getWidth(){
        return canvas.getWidth();
    }

    public double getHeight(){
        return canvas.getHeight();
    }
}
