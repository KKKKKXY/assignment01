package model;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import view.Platform;

import java.util.ArrayList;

public class Character2 extends Pane {
    public int cur_width;
    public int cur_height;

    public static final int CHARACTER_WIDTH = 100;
    public static final int CHARACTER_HEIGHT = 200;
    public static final int WIDTH = 1500; //960;
    public static final int HEIGHT = 600;
    public static final int GROUND = 300;
    private Image idleImg;
    private Image moveForwardImg;
    private Image moveBackwardImg;
    private Image attackImg;
    private Image superAttackImg;
    private Image kneelDownImg;
    private Image kneelDownAttackImg;
    private Image rotateAttackImg;

    private AnimatedSprite idleView;
    private AnimatedSprite moveForwardView;
    private AnimatedSprite moveBackwardView;
    private AnimatedSprite attackView;
    private AnimatedSprite superAttackView;

    private AnimatedSprite kneelDownView;
    private AnimatedSprite currentImageView;
    private AnimatedSprite kneelDownAttackView;
    private AnimatedSprite rotateAttackView;

    private int x;
    private int y;
    private int startX;
    private int startY;
    private int velocity = 10;

    private int score = 0;
    ArrayList<Character> toKill;
    private Direction direction;

    boolean isAttack = false;
    boolean isSuperAttack = false;
    boolean isKneelDown = false;
    boolean isKneelDownAttack = false;
    boolean isRotateAttack = false;
    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    boolean isCollapsed = false;

    public Character2(int x, int y) {
        this.startX = x;
        this.startY = y;
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.toKill = new ArrayList<>();

        this.idleImg = new Image(getClass().getResourceAsStream("/moveBackwardCharacter2.png"));
        this.moveForwardImg = new Image(getClass().getResourceAsStream("/idleCharacter2.png"));
        this.moveBackwardImg = new Image(getClass().getResourceAsStream("/idleCharacter2.png"));
        this.attackImg = new Image(getClass().getResourceAsStream("/attackByPunchingCharacter2.png"));
        this.superAttackImg = new Image(getClass().getResourceAsStream("/superAttackCharacter02.png"));
        this.kneelDownImg  = new Image(getClass().getResourceAsStream("/crunchCharacter2.png"));
        this.kneelDownAttackImg = new Image(getClass().getResourceAsStream("/kneelDownAttackCharacter2.png"));
        this.rotateAttackImg = new Image(getClass().getResourceAsStream("/rotateAttackCharacter2.png"));

        this.idleView = new AnimatedSprite(idleImg, 3, 3, (int) 73, 102);
        this.moveForwardView = new AnimatedSprite(moveForwardImg, 3, 3, (int)73, 102);
        this.moveBackwardView = new AnimatedSprite(moveBackwardImg, 3, 3, (int) 73, 102);
        this.attackView =new AnimatedSprite(attackImg, 4, 4,  (int)104.75, 102);
                //AnimatedSprite(attackImg, 4, 4,  (int)104.75, 102);
        this.superAttackView = new AnimatedSprite(superAttackImg, 21, 3, 960, 191);
        this.kneelDownView = new AnimatedSprite(kneelDownImg,2,2,73,102);
        this.kneelDownAttackView =  new AnimatedSprite(kneelDownAttackImg,8,8,(int) 128.5,102);
        this.rotateAttackView =  new AnimatedSprite(rotateAttackImg,7,7,(int) 104.428571429,108);

        this.idleView.setFitWidth(100);
        this.idleView.setFitHeight(200);
        this.moveBackwardView.setFitHeight(200);
        this.moveBackwardView.setFitWidth(100);
        this.moveForwardView.setFitWidth(100);
        this.moveForwardView.setFitHeight(200);
        this.kneelDownView.setFitHeight(200);
        this.kneelDownView.setFitWidth(100);
        this.kneelDownAttackView.setFitWidth(100);
        this.kneelDownAttackView.setFitHeight(200);
        this.rotateAttackView.setFitWidth(200);
        this.rotateAttackView.setFitHeight(200);

        setCurrentImageView(idleView);
    }

    public void attack() {
        isSuperAttack = false;
        isKneelDown = false;
        isKneelDownAttack = false;
        isRotateAttack = false;
        isAttack = true;
    }

    public void superAttack() {
        isAttack = false;
        isKneelDownAttack = false;
        isRotateAttack = false;
        isKneelDown = false;
        isSuperAttack = true;
    }

    public void KneelDown() {
        isAttack = false;
        isSuperAttack = false;
        isKneelDownAttack = false;
        isRotateAttack = false;
        isKneelDown = true;
    }

    public void rotateAttack() {
        isAttack = false;
        isKneelDownAttack = false;
        isKneelDown = false;
        isSuperAttack = false;
        isRotateAttack = true;
    }

    public void kneelDownAttack() {
        isKneelDown = false;
        isAttack = false;
        isSuperAttack = false;
        isKneelDownAttack = true;
    }
    public void stopAttack() {
        if (isAttack || isSuperAttack || isKneelDownAttack ||isRotateAttack) {
            javafx.application.Platform.runLater(() -> {
                setCurrentImageView(idleView);
            });
        }
        for (Character e : toKill) {
            e.respawn(this);
            if (isSuperAttack) {
                score = score + 10;
            }
            if (isRotateAttack){
                score = score + 5;
            }
            score++;
        }
        toKill.clear();
        isAttack = false;
        isSuperAttack = false;
        isKneelDown = false;
        isKneelDownAttack = false;
        isRotateAttack = false;
        isCollapsed = false;
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


    public void executeKneelDownAttack() {
        if (isKneelDownAttack){
            if (currentImageView != kneelDownAttackView) {
                javafx.application.Platform.runLater(() ->{
                    kneelDownAttackView.setFitHeight(200);
                    kneelDownAttackView.setFitWidth(200);
                    setCurrentImageView(kneelDownAttackView);
                });
            }
        }
    }

    public void executeKneelDown() {
        if (isKneelDown) {
            if (currentImageView != kneelDownView){
                javafx.application.Platform.runLater(() -> {
                    setCurrentImageView(kneelDownView);
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

    public void executeSuperAttack(ArrayList<Character> characterArrayList) throws InterruptedException {

        if (isSuperAttack) {
            setTranslateX(0);
            setTranslateY(Platform.GROUND);

            if (currentImageView != superAttackView) {
                javafx.application.Platform.runLater(() -> {
                    superAttackView.setFitWidth(1600);
                    superAttackView.setFitHeight(250);
                    setCurrentImageView(superAttackView);
                });
            }
            toKill = new ArrayList(characterArrayList);
            x = startX;
            y = startY;
        }
    }

    public void move() {
        if (direction.equals(Direction.W) || direction.equals(Direction.UPLEFT) || direction.equals(Direction.UPRIGHT)) {
            y = y - velocity;
        }

        if(direction.equals(Direction.S)||direction.equals(Direction.DOWNLEFT)||direction.equals(Direction.DOWNRIGHT)) {
            y = y + velocity;
        }

        if (direction.equals(Direction.A) || direction.equals(Direction.UPLEFT) || direction.equals(Direction.DOWNLEFT)) {
            x = x - velocity;
            isMoveRight = true;
            if (! isAttack && ! isSuperAttack && !isKneelDown && !isKneelDownAttack && !isRotateAttack) {
                if (currentImageView != moveBackwardView) {
                    javafx.application.Platform.runLater(() -> {
                        setCurrentImageView(moveBackwardView);
                    });
                }
            }
        }

        if(direction.equals(Direction.D)||direction.equals(Direction.UPRIGHT)||direction.equals(Direction.DOWNRIGHT)) {
            x = x + velocity;
            isMoveLeft = true;
            if (! isAttack && ! isSuperAttack && !isKneelDown && !isKneelDownAttack && !isRotateAttack) {
                if (currentImageView != moveForwardView) {
                    javafx.application.Platform.runLater(() -> {
                        setCurrentImageView(moveForwardView);
                    });
                }
            }
        }

        if(direction.equals(Direction.NONE)) {
            if (!isAttack && !isSuperAttack && !isKneelDown && this.currentImageView != idleView && !isKneelDownAttack && !isRotateAttack) {
                javafx.application.Platform.runLater(() -> {
                    setCurrentImageView(idleView);
                });
            }
        }

        setTranslateX(x);
        setTranslateY(y);
    }

    public void repaint(ArrayList<Character> characterArrayList) throws InterruptedException {
        move();
        executeAttack();
        executeSuperAttack(characterArrayList);
        executeKneelDown();
        executeKneelDownAttack();
        //executeRotateAttack();

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

    public int getScore2() {
        return score;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void respawn(Character character) {
        this.setTranslateX(1300);
        this.setTranslateY( GROUND +((HEIGHT-GROUND)/2)-50);
    }

    public void collided(Character c) {
        if (isAttack || isRotateAttack || isKneelDownAttack  ) {
            if(!toKill.contains(c)) toKill.add(c);
        }

    }

    public Direction getDirection() {
        return direction;
    }
}
