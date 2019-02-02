package model;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.swing.*;

public class LifeBar extends Pane {
    ProgressBar pbar;
    public LifeBar(int x, int y) {
        setTranslateX(x);
        setTranslateY(y);
        pbar = new ProgressBar();
        pbar.setScaleX(5);
        pbar.setProgress(100);
        pbar.setMaxSize(100,30);
        pbar.setStyle("-fx-background-color: -fx-box-border");
        pbar.setTranslateX(100);
        getChildren().add(pbar);
    }

    public void setLifeBar(double lifePercentage){this.pbar.setProgress(lifePercentage/50);}
}
