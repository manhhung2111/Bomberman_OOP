package entities.DynamicEntities;

import static Control.GameManager.*;
import Control.CheckCollision;
import entities.Entity;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Random;

public class Balloon extends Entity {
    public Balloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.speed = 1;
        this.direction = "down";
        solidArea = new Rectangle(1,1,30,30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void update() {
        moveable();
    }
    public void moveable(){
        this.collisionOn = false;
        CheckCollision.checkTile(this);
        if(!collisionOn) {
            switch(direction){
                case "up":
                    y -= speed;
                    break;
                case "down":
                    y += speed;
                    break;
                case "right":
                    x += speed;
                    break;
                case "left":
                    x -= speed;
                    break;
            }
        }
        if (collisionOn ) {
            Random random = new Random();
            dir = random.nextInt(4) +1;
            if (dir== 1) {
                this.direction = "up";
            } else if (dir == 2) {
                this.direction = "down";
            } else if (dir ==3) {
                this.direction = "right";
            } else if (dir == 4) {
                this.direction = "left";
            }
        }
    }

}
