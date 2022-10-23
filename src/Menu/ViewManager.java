package Menu;

import Control.GameManager;
import Control.SoundManager;
import static Control.SoundManager.*;
import com.sun.org.apache.bcel.internal.generic.IADD;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import static Control.GameManager.*;
import static graphics.Sprite.SCALED_SIZE;
import static graphics.Sprite.bomb;

public class ViewManager {
    private AnchorPane mainPane;
    private static Scene mainScene;
    public static Stage mainStage;

    private final static int MENU_BUTTON_START_X = 100;
    private final static int MENU_BUTTON_START_Y = 150;
    private List<CustomMenuButton> menuButtonList = new ArrayList<>();
    List<SoundPicker> soundList;

    private MenuSubsene soundSubScene;
    private MenuSubsene scoresSubScene;
    private MenuSubsene helpSubScene;

    private final String PLAYER_PATH = "menu/player.png";
    private final String BOMB_PATH = "menu/bomb.png";
    private final String ENEMY1_PATH = "menu/enemy1.png";
    private final String ENEMY2_PATH = "menu/enemy2.png";
    private final String PORTAL_PATH = "menu/portal.png";
    private final String POWERUP_PATH = "menu/powerup.png";

    public ViewManager() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH * SCALED_SIZE, HEIGHT * SCALED_SIZE);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScenes();
        createButton();
        createLogo();
        createBackground();
        SoundManager.playBackGroundMusic();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(CustomMenuButton button){
        button.setLayoutX(MENU_BUTTON_START_X);
        button.setLayoutY(MENU_BUTTON_START_Y + menuButtonList.size() * 100);
        menuButtonList.add(button);
        mainPane.getChildren().add(button);
    }

    //region Create Subscenes
    private void createSubScenes(){
        createScoresSubscene();
        createSoundSubscene();
        createHelpSubscene();
    }

    private void createScoresSubscene() {
        scoresSubScene = new MenuSubsene();
        mainPane.getChildren().add(scoresSubScene);
        InfoLabel score = new InfoLabel("     Scores     ");
        score.setLayoutX(115);
        score.setLayoutY(20);
        VBox scoreContainer = new VBox();
        scoreContainer.setLayoutX(130);
        scoreContainer.setLayoutY(120);
        scoreContainer.setPrefHeight(200);
        scoreContainer.setPrefWidth(350);

        Label scoreHeading = new Label("     Name			Score   ");
        scoreHeading.setUnderline(true);
        Label score1 = new Label("Player 1		  100");
        Label score2 = new Label("Player 2		  100");
        Label score3 = new Label("Player 3		  100");
        scoreHeading.setFont(Font.font("Verdana",20));
        score1.setFont(Font.font("Verdana",20));
        score2.setFont(Font.font("Verdana",20));
        score3.setFont(Font.font("Verdana",20));
        scoreContainer.setBackground(new Background(new BackgroundFill(Color.MEDIUMAQUAMARINE, new CornerRadii(20), new Insets(-20,-20,-20,-20))));
        scoreContainer.getChildren().addAll(scoreHeading, score1, score2, score3);

        scoresSubScene.getPane().getChildren().addAll(score, scoreContainer);//, score1, score2, score3);
    }

    private void createSoundSubscene() {
        soundSubScene = new MenuSubsene();
        mainPane.getChildren().add(soundSubScene);
        InfoLabel chooseSoundLabel = new InfoLabel("Sound Options");
        chooseSoundLabel.setLayoutX(100);
        chooseSoundLabel.setLayoutY(20);
        soundSubScene.getPane().getChildren().add(chooseSoundLabel);
        soundSubScene.getPane().getChildren().add(createSoundsToChoose());
    }

    private void createHelpSubscene() {
        helpSubScene = new MenuSubsene();
        mainPane.getChildren().addAll(helpSubScene);
        InfoLabel help = new InfoLabel("        Help     ");
        help.setLayoutX(120);
        help.setLayoutY(20);
        GridPane helpGrid = new GridPane();
        helpGrid.setLayoutX(100);
        helpGrid.setLayoutY(100);
        helpGrid.setHgap(20);
        helpGrid.setVgap(20);

        ImageView player = new ImageView(new Image(PLAYER_PATH, 48, 48, true, true));
        ImageView bomb = new ImageView(new Image(BOMB_PATH, 48, 48, true, true));
        ImageView enemy1 = new ImageView(new Image(ENEMY1_PATH, 48, 48, true, true));
        ImageView enemy2 = new ImageView(new Image(ENEMY2_PATH, 32, 32, true, true));
        ImageView portal = new ImageView(new Image(PORTAL_PATH, 48, 48, true, true));
        ImageView powerUp = new ImageView(new Image(POWERUP_PATH, 32, 32, true, true));

        Label playerHelp = new Label("This is your player. \nControl it with arrow keys.");
        Label bombHelp = new Label("Press SpaceBar to deploy the bomb. \nYour Bomb can eliminate the enemies and bricks.");
        Label enemyHelp = new Label("Enemies can move randomly. \nBe careful not to touch them.");
        Label itemHelp = new Label("Kill all enemies to up level." +
                                        "\nItems increases your speed, flame length,...");
        helpGrid.add(player, 0, 0);
        helpGrid.add(playerHelp, 1, 0);
        helpGrid.add(bomb, 0, 1);
        helpGrid.add(bombHelp, 1, 1);
        helpGrid.add(enemy1, 0, 2);
        helpGrid.add(enemy2, 2, 2);
        helpGrid.add(enemyHelp, 1, 2);
        helpGrid.add(portal, 0, 3);
        helpGrid.add(itemHelp, 1, 3);
        helpGrid.add(powerUp, 2, 3);

        helpSubScene.getPane().getChildren().addAll(help, helpGrid);
    }

    private @NotNull HBox createSoundsToChoose(){
        HBox box = new HBox();
        box.setSpacing(20);
        soundList = new ArrayList<>();
        for(Sound sound : Sound.values()){
            SoundPicker soundPicker = new SoundPicker(sound);
            box.getChildren().add(soundPicker);
            soundPicker.setOnMouseClicked(event -> {
                if(sound.name().equals("EffectSound")) {
                    isPlayEffectSound = !isPlayEffectSound;
                }else{
                    try {
                        isPlayBackgroundMusic = !isPlayBackgroundMusic;
                        SoundManager.playBackGroundMusic();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        throw new RuntimeException(e);
                    }
                }

                soundPicker.setCircleChoosen(!soundPicker.isCircleChoosen());
            });
        }
        box.setLayoutX(100);
        box.setLayoutY(100);

        return  box;
    }

    //endregion

    //region Create Menu Buttons
    private void createButton() throws FileNotFoundException {
        createStartButton();
        createScoreButton();
        createHelpButton();
        createSoundButton();
        createExitButton();
    }

    private void createStartButton() throws FileNotFoundException {
        CustomMenuButton startButton  = new CustomMenuButton("START");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(gameManager == null) gameManager = new GameManager();
                    mainStage.setScene(gameManager.getScene());
                    gameManager.start();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void createScoreButton() throws FileNotFoundException {
        CustomMenuButton scoresButton  = new CustomMenuButton("SCORES");
        addMenuButton(scoresButton);

        scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!helpSubScene.isHidden()) helpSubScene.moveSubScene();
                if(!soundSubScene.isHidden()) soundSubScene.moveSubScene();
                scoresSubScene.moveSubScene();
             }
        });
    }

    private void createHelpButton() throws FileNotFoundException {
        CustomMenuButton helpButton  = new CustomMenuButton("HELP");
        addMenuButton(helpButton);
        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!scoresSubScene.isHidden()) scoresSubScene.moveSubScene();
                if(!soundSubScene.isHidden()) soundSubScene.moveSubScene();
                helpSubScene.moveSubScene();
            }
        });
    }

    private void createSoundButton() throws FileNotFoundException {
        CustomMenuButton soundButton  = new CustomMenuButton("SOUND");
        addMenuButton(soundButton);

        soundButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!scoresSubScene.isHidden()) scoresSubScene.moveSubScene();
                if(!helpSubScene.isHidden()) helpSubScene.moveSubScene();
                soundSubScene.moveSubScene();
            }
        });
    }
    private void createExitButton() throws FileNotFoundException {
        CustomMenuButton exitButton  = new CustomMenuButton("EXIT");
        addMenuButton(exitButton);
        exitButton.setOnMousePressed(event -> {
            System.exit(0);
        });
    }

    //endregion

    private void createBackground(){
        Image backgroundImage = new Image("/menu/new_background.jpg", WIDTH * SCALED_SIZE, HEIGHT * SCALED_SIZE, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null );
        mainPane.setBackground(new Background(background));

        ImageView bomberman = new ImageView(new Image("/menu/bomberman.png"));
        bomberman.setTranslateX(900);
        bomberman.setTranslateY(150);
        mainPane.getChildren().add(bomberman);
    }

    private void createLogo(){
        ImageView logo = new ImageView(new Image("menu/bomberman_logo.png", 500, 150, false, true));
        logo.setLayoutX(400);
        logo.setLayoutY(20);

        logo.setOnMouseEntered(event -> {
            logo.setEffect(new DropShadow());
        });

        logo.setOnMouseExited(event -> {
            logo.setEffect(null);
        });

        mainPane.getChildren().add(logo);
    }


    public static void setGameOverScene() throws FileNotFoundException {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, WIDTH * tileSize, HEIGHT * tileSize);

        Image backgroundImage = new Image("/menu/new_background.jpg", WIDTH * SCALED_SIZE, HEIGHT * SCALED_SIZE, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null );
        anchorPane.setBackground(new Background(background));

        ImageView gameOver = new ImageView(new Image("/menu/gameOver.png"));
        gameOver.setTranslateX(150);
        gameOver.setTranslateY(30);
        anchorPane.getChildren().add(gameOver);

        CustomMenuButton exitButton  = new CustomMenuButton("EXIT");
        exitButton.setLayoutX(300);
        exitButton.setLayoutY(400);
        anchorPane.getChildren().add(exitButton);
        exitButton.setOnMousePressed(event -> {
            System.exit(0);
        });

        CustomMenuButton replayButton  = new CustomMenuButton("REPLAY");
        replayButton.setLayoutX(500);
        replayButton.setLayoutY(400);
        anchorPane.getChildren().add(replayButton);
        replayButton.setOnMousePressed(event -> {
            mainStage.setScene(mainScene);
        });

        mainStage.setScene(scene);
    }

    public static void setWinnerScene() throws FileNotFoundException {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, WIDTH * tileSize, HEIGHT * tileSize);

        Image backgroundImage = new Image("/menu/new_background.jpg", WIDTH * SCALED_SIZE, HEIGHT * SCALED_SIZE, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null );
        anchorPane.setBackground(new Background(background));

        ImageView winner = new ImageView(new Image("/menu/winner.png"));
        winner.setTranslateX(150);
        winner.setTranslateY(30);
        anchorPane.getChildren().add(winner);

        CustomMenuButton exitButton  = new CustomMenuButton("EXIT");
        exitButton.setLayoutX(150);
        exitButton.setLayoutY(500);
        anchorPane.getChildren().add(exitButton);
        exitButton.setOnMousePressed(event -> {
            System.exit(0);
        });

        CustomMenuButton replayButton  = new CustomMenuButton("REPLAY");
        replayButton.setLayoutX(400);
        replayButton.setLayoutY(500);
        anchorPane.getChildren().add(replayButton);
        replayButton.setOnMousePressed(event -> {
            mainStage.setScene(mainScene);
        });

        mainStage.setScene(scene);
    }


}
