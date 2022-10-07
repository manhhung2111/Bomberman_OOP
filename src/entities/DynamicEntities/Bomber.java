package entities.DynamicEntities;


import Control.CheckCollision;
import entities.Entity;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.awt.*;

import static Control.GameManager.*;


public class Bomber extends Entity {
    public static int flamePowerUp = 0;
    public static int currentSpeed;
    public static int currentBomb;
    protected Sprite _sprite;
    private boolean isAlive;

    private int timeAfterDeath = 18;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        solidArea = new Rectangle();
        solidArea.x = 4;
        solidArea.y = 5;
        solidArea.width = 16;
        solidArea.height = 24;
        isAlive = true;
        currentSpeed = initialSpeed;
        currentBomb = 1;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void run() {
        if (isUpKeyPressed || isDownKeyPressed || isRightKeyPressed || isLeftKeyPressed) {
            if (isUpKeyPressed) {
                direction = "up";
            } else if (isDownKeyPressed) {
                direction = "down";

            } else if (isRightKeyPressed) {
                direction = "right";

            } else if (isLeftKeyPressed) {
                direction = "left";

            }
            collisionOn = false;
            CheckCollision.checkTile(this);

            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        y -= currentSpeed;
                        break;
                    case "down":
                        y += currentSpeed;
                        break;
                    case "right":
                        x += currentSpeed;
                        break;
                    case "left":
                        x -= currentSpeed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }
    }

    public void playerAnimation() {
        switch (direction) {
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


    public void checkAlive() {
        int topLeft = flameGrid[this.getY() / tileSize][this.getX() / tileSize];
        int topRight = flameGrid[(this.getY() + solidArea.width) / tileSize][this.getX() / tileSize];
        int botLeft = flameGrid[this.getY() / tileSize][(this.getX() + solidArea.height) / tileSize];
        int botRight = flameGrid[(this.getY() + solidArea.width) / tileSize][(this.getX() + solidArea.height) / tileSize];
        if (topLeft == FLAME || topRight == FLAME || botLeft == FLAME || botRight == FLAME) isAlive = false;
    }

    public void playerAfterDeath() {
        if (!isAlive) {
            if (timeAfterDeath >= 12) {
                this.setImg(Sprite.player_dead1.getFxImage());
                timeAfterDeath--;
            } else if (timeAfterDeath >= 6) {
                this.setImg(Sprite.player_dead2.getFxImage());
                timeAfterDeath--;
            } else if (timeAfterDeath >= 0) {
                this.setImg(Sprite.player_dead3.getFxImage());
                timeAfterDeath--;
            } else {
                DynamicEntities.remove(this);
            }
        }
    }


    @Override
    public void update() {
        if (isAlive) {
            playerAnimation();
            run();
        }
        checkAlive();
        playerAfterDeath();

    }
}
