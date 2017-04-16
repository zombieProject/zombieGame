package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicBoolean;

public class ControlBar {
    private final Text text;
    private final HBox hBox;
    private final Button prevBtn;
    private final Button nextBtn;
    private final Button playBtn;
    private AtomicBoolean isPlaying = new AtomicBoolean(false);

    public ControlBar(){
        hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        prevBtn = new Button("Prev");
        text = new Text("  ? / ?  ");
        nextBtn = new Button("Next");
        playBtn = new Button("Play");
        hBox.getChildren().addAll(prevBtn, text, nextBtn, playBtn);
    }

    public void appendToPane(Pane pane){
        pane.getChildren().add(hBox);
    }

    public void setCallback(Runnable onNext, Runnable onPrev, Runnable onPlay){
        nextBtn.setOnAction(event -> {if(!isPlaying.get()) onNext.run();});
        prevBtn.setOnAction(event -> {if(!isPlaying.get()) onPrev.run();});
        playBtn.setOnAction(event -> {
            isPlaying.set(true);
            onPlay.run();
            isPlaying.set(false);
        });
    }

    public void setProcess(int current, int total){
        text.setText("  " + current + " / " + total + "  ");
    }
}
