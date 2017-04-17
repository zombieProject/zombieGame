package ui;

import agent.Agent;
import game.Scene;
import game.debug.GraphToken;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;

import static ui.RootUI.*;

public class GameScreen {
    private static final double pad = scale(padding);

    private Group group;
    private Canvas canvas;

    // cached Images for showing
    private ArrayList<ImageView> zombieViews;
    private ArrayList<ImageView> humanViews;
    private ImageView ashView;
    private Stage debugStage;
    private Text debugText;

    public GameScreen(){
        canvas = new Canvas(scale(Scene.RIGHT + RootUI.padding * 2), scale(Scene.DOWN + RootUI.padding * 2));
        group = new Group(canvas);
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        group.setAutoSizeChildren(false);
        group.resize(canvas.getWidth(), canvas.getHeight());

        // initiate views
        zombieViews = new ArrayList<>();
        humanViews = new ArrayList<>();
        ashView = new ImageView(RootUI.ashImage);
        group.getChildren().add(ashView);

        scaleHeight(ashView, 800);

        // initial debug stage
        debugText = new Text();
        debugStage = new Stage();
        debugStage.setScene(new javafx.scene.Scene(new TextFlow(debugText)));
    }

    public static void scaleHeight(ImageView imageView, double height){
        height = scale(height);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(height);
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

    public static void placeImageView(ImageView imageView, double x, double y, double rotate){
        x = scale(x);
        y = scale(y);
        double r = scale(radius);

        imageView.setLayoutX(x + pad - r);
        imageView.setLayoutY(y + pad - r);

        imageView.setRotate(rotate);
    }

    public static void placeImageView(ImageView imageView, Agent agent){
        placeImageView(imageView, agent.getX(), agent.getY(), agent.getRotate());
    }

    public void drawScene(Scene scene){
        double r = scale(radius);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth() + pad * 2, canvas.getHeight() + pad * 2);
        gc.setFill(Color.WHITE);
        gc.fillRect(pad + 0, pad + 0, scale(Scene.RIGHT), scale(Scene.DOWN));

        gc.setFill(new Color(0, 0, 1.0, 0.4));
        drawCircle(gc, scene.getAsh().getX(), scene.getAsh().getY(), Scene.SHOOTING_RANGE);

        updateImageViewList(humanViews, scene.getHumanlist().values(), false);
        updateImageViewList(zombieViews, scene.getZombielist().values(), true);

        placeImageView(ashView, scene.getAsh());

        gc.setFill(Color.RED);
        gc.fillText("score: " + scene.getScore(), scale(padding), scale(Scene.DOWN));

        for(GraphToken token : scene.getDebugGraphs()){
            token.print(gc);
        }

        String texts = scene.getDebugText();
        if(texts == null){
            debugStage.hide();
        }else {
            debugText.setText(texts);
            debugStage.sizeToScene();
            debugStage.show();
        }
    }

    public double getWidth(){
        return canvas.getWidth();
    }

    public double getHeight(){
        return canvas.getHeight();
    }

    private void updateImageViewList(ArrayList<ImageView> imageViews, Collection<? extends Agent> agents, boolean isZombie){
        int i = 0;
        for(Agent agent : agents){
            ImageView imageView;

            // first getImageView
            if (imageViews.size() <= i){
                // in this case, crete a new one
                if(isZombie){
                    imageView = new ImageView(RootUI.zombieImage);
                    scaleHeight(imageView, 800);
                }else{
                    imageView = new ImageView(RootUI.humanImage);
                    scaleHeight(imageView, 800);
                }
                group.getChildren().add(imageView);
                imageViews.add(imageView);
            }else{
                // in this case, get one from exist one
                imageView = imageViews.get(i);
            }

            placeImageView(imageView, agent);
            i++;
        }

        // then remove all other unused ones
        while (i < imageViews.size()){
            ImageView view = imageViews.remove(imageViews.size() - 1);
            group.getChildren().remove(view);
        }
    }
}
