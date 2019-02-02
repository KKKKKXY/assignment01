package controller;

import model.Character;
import model.Character2;
import view.Platform;

import java.util.ArrayList;

public class DrawingLoop implements Runnable {

    private Platform platform;
    private int frameRate;
    private float interval;
    private boolean running;

    public DrawingLoop(Platform platform) {
        this.platform = platform;
        frameRate = 60;
        interval = 1000.0f / frameRate; // 1000 ms = 1 second
        running = true;
    }

    private void checkDrawCollisions(Character mainCharacter, ArrayList<Character2> anotherCharacter) throws InterruptedException {
        mainCharacter.checkReachGameBorder();
        for (Character2 e : anotherCharacter) {
            if (mainCharacter.getBoundsInParent().intersects(e.getBoundsInParent())) {
                mainCharacter.collided(e);
            }
        }
    }
    private void checkDrawCollisions2(Character2 anotherCharacter, ArrayList<Character> mainCharacter) throws InterruptedException {
        anotherCharacter.checkReachGameBorder();
        for (Character e : mainCharacter) {
            if (anotherCharacter.getBoundsInParent().intersects(e.getBoundsInParent())) {
                anotherCharacter.collided(e);
            }
        }

    }

    private void paint(Character mainCharacter,ArrayList<Character2> characterList2) throws InterruptedException {
        mainCharacter.repaint(characterList2);
    }

    private void paint2(Character2 mainCharacter, ArrayList<Character> characterList) throws InterruptedException {
        mainCharacter.repaint(characterList);
    }
    @Override
    public void run() {
        while (running) {

            float time = System.currentTimeMillis();

            try {
                checkDrawCollisions(platform.getMainCharacter(),platform.getCharacterList2());
                checkDrawCollisions2(platform.getAnotherCharacter(),platform.getCharacterList());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                paint(platform.getMainCharacter(),platform.getCharacterList2());
                paint2(platform.getAnotherCharacter(),platform.getCharacterList());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            time = System.currentTimeMillis() - time;

            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException ignore) {
                }
            } else {
                try {
                    Thread.sleep((long) (interval - (interval % time)));
                } catch (InterruptedException ignore) {
                }
            }
        }
    }
}
