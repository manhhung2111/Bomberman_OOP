package entities.Item;

import entities.DynamicEntities.Bomber;
import entities.Entity;
import javafx.scene.image.Image;

import static Control.GameManager.StaticEntities;

public class BombItem extends Item {

    public BombItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if(isEaten){
            Bomber.currentBomb++;
            StaticEntities.remove(this);
            isEaten = false;
        }

    }
}
