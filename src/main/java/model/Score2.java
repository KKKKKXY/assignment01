package model;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Score2 extends Pane {

    Label point;

    public Score2(int x, int y) {
        point = new Label("0");
        setTranslateX(x);
        setTranslateY(y);

    }
    public void setPoint(int score) {
        this.point.setText(Integer.toString(score));
    }
}