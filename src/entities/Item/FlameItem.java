package entities.Item;

import entities.DynamicEntities.Bomber;
import entities.Entity;
import javafx.scene.image.Image;

import static Control.GameManager.StaticEntities;

public class FlameItem extends Item {

    public FlameItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if(isEaten) {
            Bomber.flamePowerUp++;
            StaticEntities.remove(this);
        }
    }
}
