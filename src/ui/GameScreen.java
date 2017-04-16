package ui;

import agent.Agent;
import game.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.util.HashMap;

import static ui.RootUI.padding;
import static ui.RootUI.radius;
import static ui.RootUI.scale;

public class GameScreen {
    private static final double pad = scale(padding);

    private Group group;
    private Canvas canvas;

    // cached Images for showing
    private HashMap<Integer, ImageView> zombieViews;
    private HashMap<Integer, ImageView> humanViews;
    private ImageView ashView;

    public GameScreen(){
        canvas = new Canvas(scale(Scene.RIGHT + RootUI.padding * 2), scale(Scene.DOWN + RootUI.padding * 2));
        group = new Group(canvas);
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        group.setAutoSizeChildren(false);
        group.resize(canvas.getWidth(), canvas.getHeight());

        // initiate views
        zombieViews = new HashMap<>();
        humanViews = new HashMap<>();
        ashView = new ImageView(RootUI.ashImage);
        group.getChildren().add(ashView);

        scaleHeight(ashView, 800);
    }

    public static void scaleHeight(ImageView imageView, double height){
        height = scale(height);
        double rate = height / imageView.getFitHeight();
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
//        imageView.setFitWidth(imageView.getFitWidth() * rate);
    }

    public void appendToPane(Pane pane){
        pane.getChildren().add(group);
    }

    public static void drawCircle(GraphicsContext gc, double x, double y, double radius){
        double r = scale(radius);
        gc.fillArc(pad + scale(x) - r, pad + scale(y) - r, r * 2, r * 2, 0, 360, ArcType.ROUND);
    }

    public static void drawAgent(GraphicsContext gc, Agent agent){
        drawCircle(gc, agent.getX(), agent.getY(), RootUI.radius);
    }

    public static void placeImageView(ImageView imageView, double x, double y){
        x = scale(x);
        y = scale(y);
        double r = scale(radius);

        imageView.setLayoutX(x + pad - r);
        imageView.setLayoutY(y + pad - r);
    }

    public static void placeImageView(ImageView imageView, Agent agent){
        placeImageView(imageView, agent.getX(), agent.getY());
    }

    public void drawScene(Scene scene){
        double r = scale(radius);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth() + pad * 2, canvas.getHeight() + pad * 2);
        gc.setFill(Color.BLACK);
        gc.fillRect(pad + 0, pad + 0, scale(Scene.RIGHT), scale(Scene.DOWN));

        gc.setFill(new Color(0, 0, 1.0, 0.4));
        drawCircle(gc, scene.getAsh().getX(), scene.getAsh().getY(), Scene.SHOOTING_RANGE);

        gc.setFill(Color.YELLOW);
        drawAgent(gc, scene.getAsh());
        placeImageView(ashView, scene.getAsh());
        ashView.setRotate(scene.getAsh().getRotate());
        gc.setFill(Color.RED);
        for(Agent zombie : scene.getZombielist().values()){
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
