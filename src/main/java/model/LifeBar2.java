package model;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

public class LifeBar2 extends Pane {

    ProgressBar pbar;
    public LifeBar2(int x, int y) {
        setTranslateX(x);
        setTranslateY(y);
        pbar = new ProgressBar();
        pbar.setScaleX(5);
        pbar.setProgress(100);
        pbar.setMaxSize(100,30);
        pbar.setTranslateX(100);
        getChildren().add(pbar);
    }

    public void setLifeBar(double lifePercentage){
        this.pbar.setProgress(lifePercentage/50);
    }
}
