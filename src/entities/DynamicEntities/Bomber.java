package entities.DynamicEntities;

import entities.*;
import graphics.Sprite;
import javafx.scene.image.Image;
import static Control.GameManager.*;


public class Bomber extends AnimatedEntity {

    public Bomber(int x, int y, Image img) {

        super( x, y, img);
    }
    public boolean isRunning = true;
    protected Sprite _sprite;

    public void run() {
        if(isRightKeyPressed == true || isLeftKeyPressed == true ||isUpKeyPressed == true || isDownKeyPressed ==true )
        {
            switch(direction) {
                case "up":
                    this.y -= Entity.jumpPixel;
                    _sprite = Sprite.movingSprite(Sprite.player_up,Sprite.player_up_1,Sprite.player_up_2, _animate, 100);
                    this.img = _sprite.getFxImage();
                    break;
                case "down":
                    this.y += Entity.jumpPixel;
                    _sprite = Sprite.movingSprite(Sprite.player_down,Sprite.player_down_1,Sprite.player_down_2, _animate, 100);
                    this.img = _sprite.getFxImage();
                    break;
                case "right":
                    this.x += Entity.jumpPixel;
                    _sprite = Sprite.movingSprite(Sprite.player_right,Sprite.player_right_1,Sprite.player_right_2, _animate, 100);
                    this.img = _sprite.getFxImage();
                    break;
                case "left":
                    this.x -= 2;
                    _sprite = Sprite.movingSprite(Sprite.player_left,Sprite.player_left_1,Sprite.player_left_2, _animate, 100);
                    this.img = _sprite.getFxImage();
                    break;
                default:
                    this.y = this.y;
                    this.x = this.x;
            }
        }

    }
    public void moveLeft(){
        this.x -= Entity.jumpPixel;
        _sprite = Sprite.movingSprite(Sprite.player_left,Sprite.player_left_1,Sprite.player_left_2, _animate, 60);
        this.img = _sprite.getFxImage();
    }
    public void moveRight() {
        this.x += Entity.jumpPixel;
        _sprite = Sprite.movingSprite(Sprite.player_right,Sprite.player_right_1,Sprite.player_right_2, _animate, 60);
        this.img = _sprite.getFxImage();
    }
    public void moveUp() {
        this.y -= Entity.jumpPixel;
        _sprite = Sprite.movingSprite(Sprite.player_up,Sprite.player_up_1,Sprite.player_up_2, _animate, 60);
        this.img = _sprite.getFxImage();
    }
    public void moveDown() {
        this.y += Entity.jumpPixel;
        _sprite = Sprite.movingSprite(Sprite.player_down,Sprite.player_down_1,Sprite.player_down_2, _animate, 60);
        this.img = _sprite.getFxImage();
    }

    public void stay(){
        this.y = this.y;
        this.x = this.x;
    }
    @Override
    public void update() {
        animate();
        run();
    }
    /*public Image render(){
        Image player = null;

        return img;
    }
    public void setImg() {
        this.img = render();
    }*/


}
