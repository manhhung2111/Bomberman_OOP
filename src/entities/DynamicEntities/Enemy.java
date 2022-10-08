package entities.DynamicEntities;

import Control.CheckCollision;
import entities.Entity;
import javafx.scene.image.Image;

import java.util.Random;

public class Enemy extends Entity {
    protected int dir = 1;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
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
            int dir = random.nextInt(4) +1;
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

    @Override
    public void update() {
        moveable();
    }
}
