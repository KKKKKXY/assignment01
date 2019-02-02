package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedSprite extends ImageView{

    int count, columns, width, height, curXIndex=0, curYIndex=0, curIndex=0;

    public AnimatedSprite(Image image, int count, int columns, int width, int height){
        this.setImage(image);
        this.count = count;
        this.columns = columns;
        this.width = width;
        this.height = height;
        this.setViewport(new Rectangle2D(0, 0, width, height));
    }

    public void tick() {
        curIndex = (curIndex+1)%count;
        curXIndex = curIndex%columns;
        curYIndex = curIndex/columns;
        interpolate();
    }

    protected void interpolate() {
        final int x = curXIndex*width;
        final int y = curYIndex*height;
        this.setViewport(new Rectangle2D(x, y, width, height));
    }

    public boolean isLooped() {
        return curIndex+1 == count;
    }
}
