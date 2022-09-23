package entities.Item;

import entities.Entity;
import javafx.scene.image.Image;

public class Item extends Entity {
    private boolean isEaten = false;

    public boolean isEaten() {
        return isEaten;
    }

    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }
}
