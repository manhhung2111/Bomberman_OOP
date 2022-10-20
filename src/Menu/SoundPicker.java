package Menu;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SoundPicker extends VBox {
    private ImageView circleImage;
    private ImageView soundImage;

    private  String circleChoosen = "menu/circle_choosen.png";
    private  String circleNotChoosen = "menu/grey_circle.png";

    private boolean isCircleChoosen;
    private Sound sound;

    public SoundPicker(Sound sound) {
        circleImage = new ImageView(circleChoosen);
        soundImage = new ImageView(new Image(sound.getUrlSound()));
        this.sound = sound;
        isCircleChoosen = true;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().addAll(circleImage, soundImage);
    }

    public Sound getSound(){
        return sound;
    }

    public boolean isCircleChoosen() {
        return isCircleChoosen;
    }

    public void setCircleChoosen(boolean isCircleChoosen) {
        this.isCircleChoosen = isCircleChoosen;
        String imageToSet = this.isCircleChoosen? circleChoosen : circleNotChoosen;
        circleImage.setImage(new Image(imageToSet));
    }
}
