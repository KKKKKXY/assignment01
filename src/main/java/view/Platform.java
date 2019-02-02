package view;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.*;
import model.Character;

import java.util.ArrayList;

public class Platform extends Pane {

    public static final int WIDTH = 1500; //960;
    public static final int HEIGHT = 600;
    public static final int GROUND = 300;

    private Image platformImg;

    private ArrayList<Character> characterList;
    private ArrayList<Character2> characterList2;
    private Character mainCharacter;
    private Character2 anotherCharacter;
    //private Score score;
    //private Score2 score2;
    //private LifeBar lifeBar;
    //private LifeBar2 lifeBar2;
    //private FighterImage fighterImage;
    //private FighterImage fighterImage2;
    //private Timer timer;

    private Keys keys;

    public Platform() {
        keys = new Keys();
        characterList = new ArrayList<>();
        characterList2 = new ArrayList<>();
        platformImg = new Image(getClass().getResourceAsStream("/background.gif"));
        ImageView backgroundImg = new ImageView(platformImg);
        backgroundImg.setFitHeight(HEIGHT);
        backgroundImg.setFitWidth(WIDTH);
        mainCharacter = new Character(50, GROUND+((HEIGHT-GROUND)/2)-50);
        anotherCharacter = new Character2(1300,GROUND+((HEIGHT-GROUND)/2)-50);

        //buildEnemyList();
        //score = new Score(50,HEIGHT - 50);
        //score2 = new Score2(1350, HEIGHT - 50);

        //lifeBar = new LifeBar(250,10);
        //lifeBar2 = new LifeBar2(950,10);

        //fighterImage = new FighterImage(0,0,new Image(getClass().getResourceAsStream("/Ken_face.gif")));
        //fighterImage2 = new FighterImage(1330,0, new Image(getClass().getResourceAsStream("/akuma.png")));

        //timer = new Timer(675,0);
        characterList.add(mainCharacter);
        characterList2.add(anotherCharacter);
        getChildren().add(backgroundImg);
        //getChildren().add(score);
        //getChildren().add(lifeBar);
        //getChildren().add(score2);
        //getChildren().add(lifeBar2);
        //getChildren().add(fighterImage);
        //getChildren().add(fighterImage2);
        //getChildren().add(timer);
        getChildren().addAll(characterList);
        getChildren().addAll(characterList2);
    }

    public Character getMainCharacter() { return mainCharacter; }

    public Character2 getAnotherCharacter() {return anotherCharacter;}

    public Keys getKeys() { return keys; }

    //public Score getScore() {return score;}

    //public Score2 getScore2() { return score2; }

    //public LifeBar getLifeBar() { return  lifeBar;}

    //public LifeBar2 getLifeBar2() { return  lifeBar2;}

    public ArrayList<Character> getCharacterList() { return  characterList;}

    public ArrayList<Character2> getCharacterList2() { return characterList2; }

}

