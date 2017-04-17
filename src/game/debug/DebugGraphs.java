package game.debug;

import game.Scene;
import javafx.scene.paint.Color;
import ui.GameScreen;
import ui.RootUI;

import java.util.HashSet;
import java.util.Iterator;

public class DebugGraphs implements Iterable<GraphToken> {
    private HashSet<GraphToken> set = new HashSet<>();
    private Scene scene;

    public DebugGraphs(Scene scene){
        this.scene = scene;
    }

    @Override
    public Iterator<GraphToken> iterator() {
        return set.iterator();
    }

    public boolean remove(GraphToken token){
        return set.remove(token);
    }

    private GraphToken add(GraphToken token){
        if(scene.isDebugMode()) set.add(token);
        return token;
    }

    public GraphToken addCircle(Color color, double x, double y, double r){
        return add(gc -> {
            gc.setFill(color);
            GameScreen.drawCircle(gc, x, y, r);
        });
    }

    public GraphToken addPoint(Color color, double x, double y){
        return addCircle(color, x, y, 50);
    }

    public GraphToken addText(Color color, double x, double y, String text){
        double finalX = RootUI.scale(x + RootUI.padding);
        double finalY = RootUI.scale(y + RootUI.padding);
        return add(gc -> { gc.setFill(color); gc.fillText(text, finalX, finalY); });
    }

//    public GraphToken addLine(Color color, double x1, double y1, double x2, double y2){
//        double p1x = scale(x1);
//        double p2x = scale(x2);
//        double p1y = scale(y1);
//        double p2y = scale(y2);
//
//        return add(gc -> {  });
//    }

    private double scale(double p){
        return RootUI.scale(p + RootUI.padding);
    }
}
