import Control.GameManager;

import Menu.ViewManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


import java.io.FileNotFoundException;

public class BombermanGame extends Application {
    public static GameManager gameManager;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewManager viewManager = new ViewManager();
        stage = viewManager.getMainStage();
        //gameManager = new GameManager();
        stage.setTitle("Bomber man");
        //stage.setScene(gameManager.getScene());
        stage.show();
        //gameManager.start();
    }
}
