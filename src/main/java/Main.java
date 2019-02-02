import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Platform;
import controller.DrawingLoop;
import controller.GameLoop;

public class Main extends Application {

    public static void main(String[] args) { launch(args); }
    public Main(){}

    @Override
    public void start(Stage primaryStage) {

        Platform platform = new Platform();
        GameLoop gameLoop = new GameLoop(platform);
        DrawingLoop drawingLoop = new DrawingLoop(platform);

        Scene scene = new Scene(platform,platform.WIDTH,platform.HEIGHT);
        scene.setOnKeyPressed(event-> platform.getKeys().add(event.getCode()));
        scene.setOnKeyReleased(event ->  platform.getKeys().remove(event.getCode()));

        primaryStage.setTitle("STREET FIGHTER");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        (new Thread(gameLoop)).start();
        (new Thread(drawingLoop)).start();

    }

    public void replayGame(Stage primaryStage){
        start(primaryStage);
    }

}
