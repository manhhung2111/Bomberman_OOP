package entities.DynamicEntities;

import entities.*;
import graphics.Sprite;
import javafx.scene.image.Image;
import static Control.GameManager.*;


public class Bomber extends Entity {

    public Bomber(int x, int y, Image img) {

        super( x, y, img);
    }
    public boolean isRunning = true;
    protected Sprite _sprite;

    public void run() {
        if(isUpKeyPressed ==true ||isDownKeyPressed == true || isRightKeyPressed == true || isLeftKeyPressed == true)
        {
            if(isUpKeyPressed == true) {
                direction = "up";
                y -= speed;
            } else if(isDownKeyPressed == true) {
                direction = "down";
                y += speed;
            }else if(isRightKeyPressed == true) {
                direction = "right";
                x += speed;
            }else if(isLeftKeyPressed) {
                direction = "left";
                x -= speed;
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

    public void stay(){
        this.y = this.y;
        this.x = this.x;
    }
    @Override
    public void update() {
        playerImg();
        run();

    }

}
