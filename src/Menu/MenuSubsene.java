package Menu;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;
import sun.plugin.javascript.navig.Anchor;

public class MenuSubsene extends SubScene {

    private final static String FONT_PATH = "menu/kenvector_future.ttf";
    private final static String BACKGROUND_IMAGE = "menu/red_panel.png";
    private boolean isHidden;
    public MenuSubsene() {
        super(new AnchorPane(), 600, 400);
        prefHeight(400);
        prefWidth(600);
        isHidden = true;
        BackgroundImage image = new BackgroundImage(
                new Image(BACKGROUND_IMAGE, 600, 400, false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                null);
        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));
        setLayoutX(1500);
        setLayoutY(200);
    }

    public void moveSubScene(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(this);
        if(isHidden){
            transition.setToX(-1100);
            isHidden = false;
        }else {
            transition.setToX(0);
            isHidden = true;
        }

        transition.play();
    }

    public boolean isHidden() {
        return isHidden;
    }

    public AnchorPane getPane(){
        return (AnchorPane) this.getRoot();
    }
}
