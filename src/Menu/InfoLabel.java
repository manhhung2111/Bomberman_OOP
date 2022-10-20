package Menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InfoLabel extends Label {
    public final static String FONT_PATH = "menu/kenvector_future.ttf";

    public final static String BACKGROUND_IMG = "menu/red_small_panel.png";
    public InfoLabel(String text) {
        setPrefHeight(49);
        setPrefWidth(380);
        setText(text);
        setWrapText(true);
        setLabelFont();
        setAlignment(Pos.CENTER);

        BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMG, 380, 49, false, true),
                                                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));

    }

    private void setLabelFont() {
        try{
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 23));
        }catch (FileNotFoundException e){
            setFont(Font.font("Verdana", 23));
        }
    }
}
