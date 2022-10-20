package Menu;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameMenuBar {
    private static ImageView gameStatus;
    public static Text level, bomb, time;
    public static int timeLeft = 120;
    public static boolean isPause = false;

    public static void createGameMenuBar(Group root){
        level = new Text("Level: 1");
        level.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        level.setFill(Color.WHITE);
        level.setX(416);
        level.setY(20);

        bomb = new Text("Bombs: 20");
        bomb.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        bomb.setFill(Color.WHITE);
        bomb.setX(512);
        bomb.setY(20);
        time = new Text("Times: 120");
        time.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        time.setFill(Color.WHITE);
        time.setX(608);
        time.setY(20);

        Image pauseGame = new Image("Resource/pause.png");
        gameStatus = new ImageView(pauseGame);
        gameStatus.setX(50);
        gameStatus.setY(20);
        gameStatus.setScaleX(0.5);
        gameStatus.setScaleY(0.5);

        Pane pane = new Pane();
        pane.getChildren().addAll(level, bomb, time, gameStatus);
        pane.setMinSize(1024, 32);
        pane.setMaxSize(1024, 480);
        pane.setStyle("-fx-background-color: #D7E5F0");

        root.getChildren().add(pane);

        gameStatus.setOnMouseClicked(event -> {
            if(isPause){
                gameStatus.setImage(new Image("Resource/pause.png"));
                isPause = false;
            }else{
                gameStatus.setImage(new Image("Resource/resume.png"));
                isPause = true;
            }
        });
    }
}
