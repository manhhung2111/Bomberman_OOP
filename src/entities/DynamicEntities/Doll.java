package entities.DynamicEntities;

import static Control.GameManager.*;
import Control.CheckCollision;
import entities.Entity;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.Random;

public class Doll extends Enemy {
    public int getAnimation = 0;
    private int timeAfterDeath = 24;
    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.speed = 1;
        this.direction = "down";
        solidArea = new Rectangle(1,1,30,30);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }


    @Override
    public void update() {
        super.update();
        getAnimation();
        enemyAfterDeath();

    }
    public void getAnimation() {
        if(getAnimation == 60) getAnimation = 0;
        if(this.isDead == false) {
            if(getAnimation <= 20) {
                if(this.direction == "left"){
                    this.setImg(Sprite.doll_left1.getFxImage());
                } else if(this.direction == "right") {
                    this.setImg(Sprite.doll_right1.getFxImage());
                } else if(this.direction == "up" || this.direction == "down"){
                    this.setImg(Sprite.doll_left1.getFxImage());
                }
                getAnimation++;
            }else if(getAnimation <=40){
                if(this.direction == "left"){
                    this.setImg(Sprite.doll_left2.getFxImage());
                } else if(this.direction == "right") {
                    this.setImg(Sprite.doll_right2.getFxImage());
                } else if(this.direction == "up" || this.direction == "down"){
                    this.setImg(Sprite.doll_right2.getFxImage());
                }
                getAnimation++;
            }else if(getAnimation <= 60) {
                if (this.direction == "left") {
                    this.setImg(Sprite.doll_left3.getFxImage());
                } else if (this.direction == "right") {
                    this.setImg(Sprite.doll_right3.getFxImage());
                }  else if(this.direction == "up" || this.direction == "down"){
                    this.setImg(Sprite.doll_left3.getFxImage());
                }
                getAnimation++;
            }
        }
    }

    public void enemyAfterDeath() {
        if (isDead) {
            this.setSpeed(0);
            if (timeAfterDeath >= 18) {
                this.setImg(Sprite.balloom_dead.getFxImage());
                timeAfterDeath--;
            } else if (timeAfterDeath >= 12) {
                this.setImg(Sprite.mob_dead1.getFxImage());
                timeAfterDeath--;
            } else if (timeAfterDeath >= 6) {
                this.setImg(Sprite.mob_dead2.getFxImage());
                timeAfterDeath--;
            } else if(timeAfterDeath >= 0){
                this.setImg(Sprite.mob_dead3.getFxImage());
                timeAfterDeath--;
            }
            else {
                enemyEntities.remove(this);
            }
        }
    }
}

