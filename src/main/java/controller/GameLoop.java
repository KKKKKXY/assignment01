package controller;

import javafx.scene.control.Alert;

import javafx.scene.input.KeyCode;
import model.*;
import model.Character;
import view.Platform;


public class GameLoop implements Runnable {

    private Platform platform;
    private int frameRate;
    private float interval;
    private boolean running;

    public GameLoop(Platform platform) {
        this.platform = platform;
        frameRate = 10;
        interval = 1000.0f / frameRate;
        running = true;
    }

    private void update(Character mainCharacter) {

        mainCharacter.getCurrentImageView().tick();


        if (platform.getKeys().isPressed(KeyCode.LEFT)) {
            mainCharacter.setDirection(Direction.LEFT);
        }

        if (platform.getKeys().isPressed(KeyCode.RIGHT)) {
            mainCharacter.setDirection(Direction.RIGHT);
        }

        if (platform.getKeys().isPressed(KeyCode.UP)) {
            mainCharacter.setDirection(Direction.UP);
        }

        if (platform.getKeys().isPressed(KeyCode.DOWN)) {
            mainCharacter.setDirection(Direction.DOWN);
        }

        if (platform.getKeys().isPressed(KeyCode.UP) && platform.getKeys().isPressed(KeyCode.RIGHT)) {
            mainCharacter.setDirection(Direction.UPRIGHT);
        }

        if (platform.getKeys().isPressed(KeyCode.DOWN) && platform.getKeys().isPressed(KeyCode.RIGHT)) {
            mainCharacter.setDirection(Direction.DOWNRIGHT);
        }

        if (platform.getKeys().isPressed(KeyCode.UP) && platform.getKeys().isPressed(KeyCode.LEFT)) {
            mainCharacter.setDirection(Direction.UPLEFT);
        }

        if (platform.getKeys().isPressed(KeyCode.DOWN) && platform.getKeys().isPressed(KeyCode.LEFT)) {
            mainCharacter.setDirection(Direction.DOWNLEFT);
        }

        if (!platform.getKeys().isPressed(KeyCode.LEFT) && !platform.getKeys().isPressed(KeyCode.RIGHT) && !platform.getKeys().isPressed(KeyCode.UP) && !platform.getKeys().isPressed(KeyCode.DOWN)) {
            mainCharacter.setDirection(Direction.NONE);
        }

        if (mainCharacter.getCurrentImageView().isLooped()) {
            mainCharacter.stopAttack();
        }

        if (platform.getKeys().isPressed(KeyCode.SPACE)) {
            mainCharacter.attack();
        }

       // if (platform.getKeys().isPressed(KeyCode.CONTROL)) {
         //   mainCharacter.superAttack();
        //}
        //if (platform.getKeys().isPressed(KeyCode.ENTER)) {
          //  mainCharacter.KneelDown();
        // }
        if (platform.getKeys().isPressed(KeyCode.P)) {
            mainCharacter.powerAttack();
        }
    }

    // second character update method
    private void update2(Character2 mainCharacter) {

        mainCharacter.getCurrentImageView().tick();

        if (platform.getKeys().isPressed(KeyCode.A)) {
            mainCharacter.setDirection(Direction.A);
            //left
        }

        if (platform.getKeys().isPressed(KeyCode.D)) {
            mainCharacter.setDirection(Direction.D);
            //right
        }

        if (platform.getKeys().isPressed(KeyCode.W)) {
            mainCharacter.setDirection(Direction.W);
            //up
        }

        if (platform.getKeys().isPressed(KeyCode.S)) {
            mainCharacter.setDirection(Direction.S);
            //down
        }

        if (platform.getKeys().isPressed(KeyCode.W) && platform.getKeys().isPressed(KeyCode.D)) {
            mainCharacter.setDirection(Direction.UPRIGHT);
        }

        if (platform.getKeys().isPressed(KeyCode.S) && platform.getKeys().isPressed(KeyCode.RIGHT)) {
            mainCharacter.setDirection(Direction.DOWNRIGHT);
        }

        if (platform.getKeys().isPressed(KeyCode.W) && platform.getKeys().isPressed(KeyCode.A)) {
            mainCharacter.setDirection(Direction.UPLEFT);
        }

        if (platform.getKeys().isPressed(KeyCode.S) && platform.getKeys().isPressed(KeyCode.A)) {
            mainCharacter.setDirection(Direction.DOWNLEFT);
        }

        if (!platform.getKeys().isPressed(KeyCode.A) && !platform.getKeys().isPressed(KeyCode.D) && !platform.getKeys().isPressed(KeyCode.W) && !platform.getKeys().isPressed(KeyCode.S)) {
            mainCharacter.setDirection(Direction.NONE);
        }

        if (mainCharacter.getCurrentImageView().isLooped()) {
            mainCharacter.stopAttack();
        }

        if (platform.getKeys().isPressed(KeyCode.F)) {
            mainCharacter.attack();
        }

        //if (platform.getKeys().isPressed(KeyCode.G)) {
          //  mainCharacter.superAttack();
        //}
        if (platform.getKeys().isPressed(KeyCode.R)) {
            mainCharacter.KneelDown();
        }
        if (platform.getKeys().isPressed(KeyCode.E)) {
            mainCharacter.kneelDownAttack();
        }
        //if (platform.getKeys().isPressed(KeyCode.T)) {
          //  mainCharacter.rotateAttack();
        //}
    }
    private void updateScore(Character mainCharacter) {

        javafx.application.Platform.runLater(() -> {
                //score.setPoint(mainCharacter.getScore());
                //lifeBar.setLifeBar(mainCharacter.getScore());
            if (mainCharacter.getScore() >= 51 ) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("The Winner is KEN MASTER\nCongratulation...!");
                a.show();
                running = false;
                //Timer timer = new Timer();
                //timer.stopClock();
                mainCharacter.stopAttack();

            }

        });
    }
    private void updateScore(Character2 mainCharacter) {
        javafx.application.Platform.runLater(() -> {
            //score2.setPoint(mainCharacter.getScore2());
            //lifeBar.setLifeBar(mainCharacter.getScore2());
            if (mainCharacter.getScore2() >= 50 ) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("The Winner is AKUMU\nCongratulation...!");
                a.show();
                running = false;
               // Timer timer = new Timer();
                //timer.stopClock();
                //mainCharacter.stopAttack();

            }
        });
    }
    @Override
    public void run() {
        while (running) {

            float time = System.currentTimeMillis();

            update(platform.getMainCharacter());
            updateScore(platform.getMainCharacter());

            update2(platform.getAnotherCharacter());
            updateScore(platform.getAnotherCharacter());

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
