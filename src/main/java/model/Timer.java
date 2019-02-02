package model;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.time.LocalDateTime;
public class Timer extends Pane {
    private int second;
    private int minute = 0;
    private int hour=0;
    private Label timer;
    Timeline clock;

    public Timer(){}

    public Timer(int x, int y){
        timer = new Label("0");
        setTranslateX(x);
        setTranslateY(y);
        timer.setFont(Font.font("Verdana", FontWeight.BOLD,50));
        timer.setTextFill(Color.web("#FFF"));
        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            int secondFact = LocalDateTime.now().getSecond();
            second++;

            if (second==59){
                this.second = 0;
                minute++;
            }
            timer.setText(Integer.toString(minute)+" : "+Integer.toString(second));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        getChildren().addAll(timer);
    }

    public void stopClock() {
        this.timer.setText("0 : 0");
    }

}
