package entities.DynamicEntities;

import entities.Entity;
import javafx.scene.image.Image;

import java.awt.*;

public class Oneal extends Enemy {
    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.speed = 1;
        this.direction = "down";
        solidArea = new Rectangle(1,1,30,30);
    }
}
