package entities.DynamicEntities;

import Control.CheckCollision;
import entities.DynamicEntities.Enemy;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

import static Control.GameManager.enemyEntities;

public class Minvo extends Enemy {
    private int getAnimation = 0;
    private int timeAfterDeath = 24;

    public Minvo(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.direction = ((new Random()).nextBoolean())? "left" : "right";
    }

    @Override
    public void moveable() {
        this.collisionOn = false;
        this.collisionBomb = false;
        CheckCollision.checkCollisionBomb(this);
        CheckCollision.checkTile(this);
        if(!collisionOn) {
            switch(direction){
                case "right":
                    x += speed;
                    break;
                case "left":
                    x -= speed;
                    break;
            }
        }
        if (collisionOn || collisionBomb ) {
            this.direction = (Objects.equals(this.direction, "left"))? "right" : "left";
        }
    }

    public void getAnimation() {
        if(getAnimation == 60) getAnimation = 0;
        if(!this.isDead) {
            if(getAnimation <= 20) {
                if(Objects.equals(this.direction, "left")){
                    this.setImg(Sprite.minvo_left1.getFxImage());
                } else if(Objects.equals(this.direction, "right")) {
                    this.setImg(Sprite.minvo_right1.getFxImage());
                }
                getAnimation++;
            }else if(getAnimation <=40){
                if(Objects.equals(this.direction, "left")){
                    this.setImg(Sprite.minvo_left2.getFxImage());
                } else if(Objects.equals(this.direction, "right")) {
                    this.setImg(Sprite.minvo_right2.getFxImage());
                }
                getAnimation++;
            }else if(getAnimation <= 60) {
                if(Objects.equals(this.direction, "left")){
                    this.setImg(Sprite.minvo_left3.getFxImage());
                } else if(Objects.equals(this.direction, "right")) {
                    this.setImg(Sprite.minvo_right3.getFxImage());
                }
                getAnimation++;
            }
        }
    }

    public void enemyAfterDeath() {
        if (isDead) {
            this.setSpeed(0);
            if (timeAfterDeath >= 18) {
                this.setImg(Sprite.minvo_dead.getFxImage());
                timeAfterDeath--;
            } else if (timeAfterDeath >= 12) {
                this.setImg(Sprite.mob_dead1.getFxImage());
                timeAfterDeath--;
            } else if (timeAfterDeath >= 6) {
                this.setImg(Sprite.mob_dead2.getFxImage());
                timeAfterDeath--;
            } else if (timeAfterDeath >= 0) {
                this.setImg(Sprite.mob_dead3.getFxImage());
                timeAfterDeath--;
            } else {
                enemyEntities.remove(this);
            }
        }
    }

    @Override
    public void update() {
        moveable();
        super.checkAlive();
        getAnimation();
        enemyAfterDeath();
    }
}
