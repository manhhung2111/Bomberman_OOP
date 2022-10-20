package Menu;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import Control.SoundManager;

public class CustomMenuButton extends Button {
    private final String FONT_PATH = "menu/kenvector_future.ttf";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/menu/red_button_pressed.png');";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/menu/red_button.png');";

    public CustomMenuButton(String text) throws FileNotFoundException {
        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();
    }

    private void initializeButtonListeners(){
        setOnMousePressed(event -> {
            setButtonPressedStyle();
            SoundManager.playClickSound();
        });

        setOnMouseReleased(event -> {
            setButtonFreeStyle();
        });

        setOnMouseEntered(event -> {
            setEffect(new DropShadow());
        });


        setOnMouseExited(event ->  {
            setEffect(null);
        });

    }

    private void setButtonFont() throws FileNotFoundException {
        try{
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e){
            setFont(Font.font("Verdana", 23));
        }
    }

    private void setButtonPressedStyle(){
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonFreeStyle(){
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

}
