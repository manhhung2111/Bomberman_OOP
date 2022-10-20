package entities.DynamicEntities;

import Control.CheckCollision;
import entities.Entity;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Random;

import static Control.GameManager.*;
import static Control.GameManager.FLAME;

public class Enemy extends Entity {
    protected int dir = 1;
    protected boolean isDead = false;
    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        solidArea = new Rectangle(1,1,30/2*3,30/2*3);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.speed = 1;
        this.direction = "down";
    }

    public void moveable(){
        this.collisionOn = false;
        this.collisionBomb = false;
        CheckCollision.checkCollisionBomb(this);
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
        if (collisionOn || collisionBomb ) {
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
        checkAlive();

    }
    public void checkAlive() {
        int topLeft = flameGrid[this.getY() / tileSize][this.getX() / tileSize];
        int topRight = flameGrid[(this.getY() + solidArea.width) / tileSize][this.getX() / tileSize];
        int botLeft = flameGrid[this.getY() / tileSize][(this.getX() + solidArea.height) / tileSize];
        int botRight = flameGrid[(this.getY() + solidArea.width) / tileSize][(this.getX() + solidArea.height) / tileSize];
        if (topLeft == FLAME || topRight == FLAME || botLeft == FLAME || botRight == FLAME)  isDead= true;
    }
}
