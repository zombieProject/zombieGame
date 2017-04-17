package game.debug;

import javafx.scene.canvas.GraphicsContext;

@FunctionalInterface
public interface GraphToken {
    void print(GraphicsContext gc);
}
