package model;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import view.Platform;
import java.util.ArrayList;

public class Character extends Pane {

    public static final int WIDTH = 1500; //960;
    public static final int HEIGHT = 600;
    public static final int GROUND = 300;
    public static final int CHARACTER_WIDTH = 100;
    public static final int CHARACTER_HEIGHT = 200;
    public int cur_width;
    public int cur_height;

    private Image idleImg;
    private Image moveForwardImg;
    private Image moveBackwardImg;
    private Image attackImg;
    private Image superAttackImg;
    private Image kneelDownImg;
    private Image powerAttackImg;
    private Image collapsedImg;

    private AnimatedSprite idleView;
    private AnimatedSprite moveForwardView;
    private AnimatedSprite moveBackwardView;
    private AnimatedSprite attackView;
    private AnimatedSprite superAttackView;

    private AnimatedSprite kneelDownView;
    private AnimatedSprite currentImageView;
    private AnimatedSprite powerAttackView;
    private AnimatedSprite collapsedView;

    private int x;
    private int y;
    private int startX;
    private int startY;
    private int velocity = 10;

    private int score = 0;

    private Direction direction;
    ArrayList<Character2> toKill;

    boolean isAttack = false;
    boolean isSuperAttack = false;
    boolean isKneelDown = false;
    boolean isPowerAttack = false;
    boolean isCollapsed = false;

    boolean isMoveLeft = false;
    boolean isMoveRight = false;


    public Character(int x, int y) {
        this.startX = x;
        this.startY = y;
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.toKill = new ArrayList<>();

        this.idleImg = new Image(getClass().getResourceAsStream("/idlCharacter1.png"));
        this.moveForwardImg = new Image(getClass().getResourceAsStream("/moveForwardCharacter1.png"));
        this.moveBackwardImg = new Image(getClass().getResourceAsStream("/moveForwardCharacter1.png"));
        this.attackImg = new Image(getClass().getResourceAsStream("/attackByKickingCharacter1.png"));
        this.superAttackImg = new Image(getClass().getResourceAsStream("/superAttackCharacter1.png"));
        this.kneelDownImg  = new Image(getClass().getResourceAsStream("/kneelDown.png"));
        this.powerAttackImg  = new Image(getClass().getResourceAsStream("/powerAttackCharacter1.png"));
        this.collapsedImg  = new Image(getClass().getResourceAsStream("/kneelDown.png"));

        this.idleView = new AnimatedSprite(idleImg, 4, 4, (int) 35.5, 90);
        this.moveForwardView = new AnimatedSprite(moveForwardImg, 5, 5, (int)43.2, 90);
        this.moveBackwardView = new AnimatedSprite(moveBackwardImg, 5, 5, (int)43.2, 90);
        this.attackView = new AnimatedSprite(attackImg, 5, 5,  85, 90);
        this.superAttackView = new AnimatedSprite(superAttackImg, 21, 3, 960, 191);
        this.kneelDownView = new AnimatedSprite(kneelDownImg,1,1,50,90);
        this.powerAttackView = new AnimatedSprite(powerAttackImg,5,5,(int) 131.8,90);
        this.currentImageView = new AnimatedSprite(collapsedImg,1,1,50,90);

        this.idleView.setFitWidth(100);
        this.idleView.setFitHeight(200);
        this.moveBackwardView.setFitHeight(200);
        this.moveBackwardView.setFitWidth(100);
        this.moveForwardView.setFitWidth(100);
        this.moveForwardView.setFitHeight(200);
        this.kneelDownView.setFitHeight(200);
        this.kneelDownView.setFitWidth(100);
        this.powerAttackView.setFitWidth(100);
        this.powerAttackView.setFitHeight(200);
        this.currentImageView.setFitHeight(200);
        this.currentImageView.setFitWidth(100);

        setCurrentImageView(idleView);
    }

    public void attack() {
        isSuperAttack = false;
        isSuperAttack = false;
        isKneelDown = false;
        isCollapsed = false;
        isAttack = true;
    }

    public void superAttack() {
        isSuperAttack = false;
        isAttack = false;
        isCollapsed = false;
        isSuperAttack = true;
        isKneelDown = false;
    }

    public void KneelDown() {
        isSuperAttack = false;
        isAttack = false;
        isSuperAttack = false;
        isCollapsed = false;
        isKneelDown = true;
    }

    public void powerAttack() {
        isSuperAttack = false;
        isAttack = false;
        isKneelDown = false;
        isCollapsed = false;
        isPowerAttack = true;
    }

    public void stopAttack() {
        if (isAttack || isSuperAttack || isPowerAttack) {
            javafx.application.Platform.runLater(() -> {
                setCurrentImageView(idleView);
            });
        }
        for (Character2 e : toKill) {
            e.respawn(this);
            if (isSuperAttack) {
                score = score+10;
            }
            if (isPowerAttack){
                score = score + 5;
            }
            score++;
        }

        toKill.clear();
        isAttack = false;
        isSuperAttack = false;
        isKneelDown = false;
        isCollapsed = false;
        isPowerAttack = false;

    }


    public void checkReachGameBorder() {
        if (x <= 0) {
            x = 0;
        } else if (x + cur_width >= Platform.WIDTH) {
            x = Platform.WIDTH - 200;
        }

        if (y <= Platform.HEIGHT-300) {
            y = Platform.HEIGHT-300 ;
        } else if (y >= Platform.HEIGHT - 200) {
            y = Platform.HEIGHT - 200;
        }
    }

    public void executePowerAttack() {
        if (isPowerAttack) {
            if (currentImageView != powerAttackView){
                javafx.application.Platform.runLater(() -> {
                    powerAttackView.setFitHeight(240);
                    powerAttackView.setFitWidth(200);
                    setCurrentImageView(powerAttackView);
                });
            }
        }
    }


    public void executeAttack() {
        if (isAttack) {
            if (currentImageView != attackView) {
                javafx.application.Platform.runLater(() -> {
                    attackView.setFitHeight(200);
                    attackView.setFitWidth(200);
                    setCurrentImageView(attackView);
                });
            }
        }
    }



    public void collided(Character2 c) {
        if (isAttack || isPowerAttack ) {
            if(!toKill.contains(c)) toKill.add(c);
        }

    }


    public void move() {
        if (direction.equals(Direction.UP) || direction.equals(Direction.UPLEFT) || direction.equals(Direction.UPRIGHT)) {
          y = y - velocity;
        }

        if(direction.equals(Direction.DOWN)||direction.equals(Direction.DOWNLEFT)||direction.equals(Direction.DOWNRIGHT)) {
            y = y + velocity;
        }

        if (direction.equals(Direction.LEFT) || direction.equals(Direction.UPLEFT) || direction.equals(Direction.DOWNLEFT)) {
            x = x - velocity;
            isMoveLeft = true;
            if (! isAttack && ! isSuperAttack && !isKneelDown && !isPowerAttack) {
                if (currentImageView != moveBackwardView) {
                    javafx.application.Platform.runLater(() -> {
                        setCurrentImageView(moveBackwardView);
                    });
                }
            }
        }

        if(direction.equals(Direction.RIGHT)||direction.equals(Direction.UPRIGHT)||direction.equals(Direction.DOWNRIGHT)) {
            x = x + velocity;
            isMoveRight = true;
            if (! isAttack && ! isSuperAttack && !isKneelDown && !isPowerAttack) {
                if (currentImageView != moveForwardView) {
                    javafx.application.Platform.runLater(() -> {
                        setCurrentImageView(moveForwardView);
                    });
                }
            }
        }

        if(direction.equals(Direction.NONE)) {
            if (!isAttack && !isSuperAttack && !isKneelDown && !isPowerAttack && this.currentImageView != idleView ) {
                javafx.application.Platform.runLater(() -> {
                    setCurrentImageView(idleView);
                });
            }
        }

        setTranslateX(x);
        setTranslateY(y);
    }

    public void repaint(ArrayList<Character2> characterList2) throws InterruptedException {
        move();
        executeAttack();
        //executeSuperAttack(characterList2);
        //executeKneelDown();
        executePowerAttack();
    }

    public AnimatedSprite getCurrentImageView() { return currentImageView; }

    public void setCurrentImageView(AnimatedSprite nextCurrentImageView) {

        this.currentImageView = nextCurrentImageView;
        cur_width = nextCurrentImageView.width;
        cur_height = nextCurrentImageView.height;

        this.getChildren().clear();
        this.getChildren().addAll(this.currentImageView);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getScore() {
        return score;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void respawn(Character2 character2) {
        this.setTranslateX(50);
        this.setTranslateY( GROUND +((HEIGHT-GROUND)/2)-50);
    }

    public Direction getDirection() {
        return direction;
    }
}
