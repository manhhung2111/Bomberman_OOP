package entities.DynamicEntities;

import entities.Entity;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.awt.*;

public class Bomber extends Entity {

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {

    }

    public void moveLeft() {
        this.x -= Entity.jumpPixel;
    }

    public void moveRight(){
        this.x += Entity.jumpPixel;
    }

    public void moveUp() {
        this.y -= Entity.jumpPixel;
    }

    public void moveDown() {
        this.y += Entity.jumpPixel;
    }

    public void stay(){
        this.y = this.y;
        this.x = this.x;
    }

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
