package entities.Item;

import entities.DynamicEntities.Bomber;
import entities.Entity;
import javafx.scene.image.Image;
import static Control.GameManager.*;


public class SpeedItem extends Item {

    public SpeedItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (isEaten) {
            Bomber.currentSpeed++;
            StaticEntities.remove(this);
            isEaten = false;
        }
    }
}
