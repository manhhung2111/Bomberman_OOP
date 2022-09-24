package entities.DynamicEntities;

import entities.Entity;
import graphics.Sprite;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

public class Bomber extends Entity {

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    /*public void setDefaultValues() {
        x = 100;
        y= 100;
        speed = 4;
        direction = "right";
    }*/
    @Override
    public void update() {
        /*(switch(direction) {
            case "up":
                this.img = Sprite.player_up.getFxImage();
                break;
            case "down":
                this.img = Sprite.player_down.getFxImage();
                break;
            case "right":
                this.img = Sprite.player_right.getFxImage();
                break;
            case "left":
                this.img = Sprite.player_left.getFxImage();
        }*/
    }
    /*public void getBomberImage() {
        try {
            up = ImageIO.read(getClass().getResourceAsStream("res/sprites/player_up"));
        }catch(IOException e) {
            e.printStackTrace();
        }

    }*/
    public void moveLeft() {
        direction = "left";
        this.x -= Entity.jumpPixel;
    }

    public void moveRight(){
        direction = "right";
        this.x += Entity.jumpPixel;
    }

    public void moveUp() {
        direction = "up";
        this.y -= Entity.jumpPixel;
    }

    public void moveDown() {
        direction = "down";
        this.y += Entity.jumpPixel;
    }

    public void stay(){
        this.y = this.y;
        this.x = this.x;
    }
    /*public Image render(){
        Image player = null;

        return img;
    }
    public void setImg() {
        this.img = render();
    }*/

    public boolean isCollide(Entity entity){
        // Sides of player
//        int left_player = this.x;
//        int right_player = this.x + Sprite.DEFAULT_SIZE;
//        int top_player = this.y;
//        int bottom_player = this.y + Sprite.DEFAULT_SIZE;
//
//        // Sides of Static Entity
//        int left_entity = entity.getX();
//        int right_entity = entity.getX() + Sprite.DEFAULT_SIZE;
//        int top_entity = entity.getY();
//        int bottom_entity = entity.getY() + Sprite.DEFAULT_SIZE;
//
//        if(bottom_player <= top_entity) return false;
//        if(top_player >= bottom_entity) return false;
//        if(right_player <= left_entity) return false;
//        if(left_player >= right_entity) return false;

        Rectangle player_ = new Rectangle(x,y,10, Sprite.DEFAULT_SIZE);
        Rectangle entity_ = new Rectangle(entity.getX(), entity.getY(),Sprite.DEFAULT_SIZE, Sprite.DEFAULT_SIZE);
        return player_.intersects(entity_);
    }
}
