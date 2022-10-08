package entities.DynamicEntities;

import static Control.GameManager.*;
import Control.CheckCollision;
import entities.Entity;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Random;

public class Balloon extends Enemy {
    public Balloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.speed = 1;
        this.direction = "down";
        solidArea = new Rectangle(1,1,30,30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
