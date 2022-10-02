package entities.DynamicEntities;


import Control.GameManager;
import entities.*;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.awt.*;

import static Control.GameManager.*;


public class Bomber extends Entity {

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        solidArea = new Rectangle();
        solidArea.x = 4;
        solidArea.y =5;
        solidArea.width = 16;
        solidArea.height =24;

    }
    protected Sprite _sprite;


    public void run() {
        if(isUpKeyPressed ==true ||isDownKeyPressed == true || isRightKeyPressed == true || isLeftKeyPressed == true)
        {
            if(isUpKeyPressed == true) {
                direction = "up";
            } else if(isDownKeyPressed == true) {
                direction = "down";

            }else if(isRightKeyPressed == true) {
                direction = "right";

            }else if(isLeftKeyPressed) {
                direction = "left";

            }
            collisionOn = false;
            GameManager.checkTile(this);

            if(collisionOn ==false) {
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

            spriteCounter++;
            if(spriteCounter >10) {
                if(spriteNum ==1) {
                    spriteNum =2;
                } else if(spriteNum ==2){
                    spriteNum =3;
                } else if(spriteNum ==3){
                    spriteNum =1;
                }
                spriteCounter =0;
            }

        }
    }

    public void playerImg(){
            switch(direction) {
                case "up":
                    if (spriteNum == 1) {
                        _sprite = Sprite.player_up;
                        this.img = _sprite.getFxImage();
                    }
                    if (spriteNum == 2) {
                        _sprite = Sprite.player_up_1;
                        this.img = _sprite.getFxImage();
                    }
                    if (spriteNum == 3) {
                        _sprite = Sprite.player_up_2;
                        this.img = _sprite.getFxImage();
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        _sprite = Sprite.player_down;
                        this.img = _sprite.getFxImage();
                    }
                    if (spriteNum == 2) {
                        _sprite = Sprite.player_down_1;
                        this.img = _sprite.getFxImage();
                    }
                    if (spriteNum == 3) {
                        _sprite = Sprite.player_down_2;
                        this.img = _sprite.getFxImage();
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        _sprite = Sprite.player_left;
                        this.img = _sprite.getFxImage();
                    }
                    if (spriteNum == 2) {
                        _sprite = Sprite.player_left_1;
                        this.img = _sprite.getFxImage();
                    }
                    if (spriteNum == 3) {
                        _sprite = Sprite.player_left_2;
                        this.img = _sprite.getFxImage();
                    }
                    break;
                default:
                    if (spriteNum == 1) {
                        _sprite = Sprite.player_right;
                        this.img = _sprite.getFxImage();
                    }
                    if (spriteNum == 2) {
                        _sprite = Sprite.player_right_1;
                        this.img = _sprite.getFxImage();
                    }
                    if (spriteNum == 3) {
                        _sprite = Sprite.player_right_2;
                        this.img = _sprite.getFxImage();
                    }
            }
        }


    @Override
    public void update() {
        playerImg();
        run();
    }

}
