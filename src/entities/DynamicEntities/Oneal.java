package entities.DynamicEntities;

import entities.Entity;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.awt.*;

import static Control.GameManager.enemyEntities;

public class Oneal extends Enemy {
    public int getAnimation = 0;
    private int speedchange = 1;
    private int count = 1;
    private int timeAfterDeath = 24;
    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.speed = 1;
        this.direction = "down";
        solidArea = new Rectangle(1,1,30,30);
    }
    public void update() {
        super.update();
        getAnimation();
        enemyAfterDeath();

    }
    public void getAnimation() {
        if(getAnimation == 60) {
            getAnimation = 0;
        }
        if(speedchange % 240 == 0 && count %2 ==1) {
            this.setSpeed(2);
            speedchange =1;
            count++;
        } else if (speedchange % 240 ==0 && count % 2 ==0){
            this.setSpeed(1);
            speedchange = 1;
            count++;
        }
        if(getAnimation <= 20) {
            if(this.direction == "left"){
                this.setImg(Sprite.oneal_left1.getFxImage());
            } else if(this.direction == "right") {
                this.setImg(Sprite.oneal_right1.getFxImage());
            } else if(this.direction == "up" || this.direction == "down"){
                this.setImg(Sprite.oneal_left1.getFxImage());
            }
            getAnimation++;
        }else if(getAnimation <=40){
            if(this.direction == "left"){
                this.setImg(Sprite.oneal_left2.getFxImage());
            } else if(this.direction == "right") {
                this.setImg(Sprite.oneal_right2.getFxImage());
            } else if(this.direction == "up" || this.direction == "down"){
                this.setImg(Sprite.oneal_right2.getFxImage());
            }
            getAnimation++;
        }else if(getAnimation <= 60) {
            if (this.direction == "left") {
                this.setImg(Sprite.oneal_left3.getFxImage());
            } else if (this.direction == "right") {
                this.setImg(Sprite.oneal_right3.getFxImage());
            }  else if(this.direction == "up" || this.direction == "down"){
                this.setImg(Sprite.oneal_left3.getFxImage());
            }
            getAnimation++;
        }
        speedchange++;
    }
    public void enemyAfterDeath() {
        if (isDead) {
            this.setSpeed(0);
            if (timeAfterDeath >= 18) {
                this.setImg(Sprite.oneal_dead.getFxImage());
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
