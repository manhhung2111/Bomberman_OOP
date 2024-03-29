package entities.Item;

import Control.SoundManager;
import entities.DynamicEntities.Bomber;
import entities.Entity;
import javafx.scene.image.Image;
import static Control.GameManager.*;


public class SpeedItem extends Item {

    public SpeedItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update(Entity entity) {
        if (isEaten) {
            SoundManager.playCollectItemsSound();
            entity.setSpeed(entity.getSpeed() + 1);
            StaticEntities.remove(this);
            isEaten = false;
        }
    }
}
