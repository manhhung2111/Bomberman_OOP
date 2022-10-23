package entities.DynamicEntities;

import Control.CheckCollision;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

import static Control.GameManager.enemyEntities;

public class Kondoria extends Enemy{
    private int getAnimation = 0;
    private int timeAfterDeath = 24;

    public Kondoria(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.direction = ((new Random()).nextBoolean())? "up" : "down";
    }

    @Override
    public void moveable() {
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
            }
        }
        if (collisionOn || collisionBomb ) {
            this.direction = (Objects.equals(this.direction, "up"))? "down" : "up";
        }
    }

    public void getAnimation() {
        if(getAnimation == 60) getAnimation = 0;
        if(!this.isDead) {
            if(getAnimation <= 20) {
                if(Objects.equals(this.direction, "up")){
                    this.setImg(Sprite.kondoria_right1.getFxImage());
                } else if(Objects.equals(this.direction, "down")) {
                    this.setImg(Sprite.kondoria_left1.getFxImage());
                }
                getAnimation++;
            }else if(getAnimation <=40){
                if(Objects.equals(this.direction, "up")){
                    this.setImg(Sprite.kondoria_right2.getFxImage());
                } else if(Objects.equals(this.direction, "down")) {
                    this.setImg(Sprite.kondoria_left2.getFxImage());
                }
                getAnimation++;
            }else if(getAnimation <= 60) {
                if(Objects.equals(this.direction, "up")){
                    this.setImg(Sprite.kondoria_right3.getFxImage());
                } else if(Objects.equals(this.direction, "down")) {
                    this.setImg(Sprite.kondoria_left3.getFxImage());
                }
                getAnimation++;
            }
        }
    }

    public void enemyAfterDeath() {
        if (isDead) {
            this.setSpeed(0);
            if (timeAfterDeath >= 18) {
                this.setImg(Sprite.kondoria_dead.getFxImage());
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
