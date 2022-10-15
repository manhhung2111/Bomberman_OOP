package entities.DynamicEntities;


import Control.CheckCollision;
import Control.GameManager;
import entities.Entity;
import entities.Item.BombItem;
import entities.Item.FlameItem;
import entities.Item.SpeedItem;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.awt.*;

import static Control.GameManager.*;


public class Bomber extends Entity {
    public static int flamePowerUp = 0;
    public static double currentSpeed;
    public static int currentBomb;
    protected Sprite _sprite;
    private boolean isAlive;
    private boolean isInside;

    private int timeAfterDeath = 18;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        solidArea = new Rectangle(4, 5, 16, 24);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        isInside = true;
        isAlive = true;
        currentSpeed = speed;
        currentBomb = 1;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
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
            collisionBomb = false;
            isInside = true;
            checkInsideBomb();
            if(!isInside) {
                CheckCollision.checkCollisionBomb(this);
            }
            if (!collisionOn && !collisionBomb) {
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
            CheckCollision.checkCollisionEnemy(this, GameManager.enemyEntities);
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

    public void checkInsideBomb() {
        int topLeft = bombGrid[(this.getY() + solidArea.x )/ tileSize][(this.getX() + solidArea.y) / tileSize];
        int topRight = bombGrid[(this.getY() + solidArea.width + solidArea.x) / tileSize][(this.getX() + solidArea.y) / tileSize];
        int botLeft = bombGrid[(this.getY() + solidArea.x)/ tileSize][(this.getX() + solidArea.height) / tileSize];
        int botRight = bombGrid[(this.getY() + solidArea.width) / tileSize][(this.getX() + solidArea.height) / tileSize];
        System.out.println(topLeft);
        System.out.println(topRight);
        System.out.println(botLeft);
        System.out.println(botRight);
        if (topLeft != BOMB && topRight != BOMB && botLeft != BOMB && botRight != BOMB) {
            isInside =false;
            System.out.println(isInside);
        }

    }

    public void checkPowerUp() {
        int topLeft = map[this.getY() / tileSize][this.getX() / tileSize];
        int topRight = map[(this.getY() + solidArea.width) / tileSize][this.getX() / tileSize];
        int botLeft = map[this.getY() / tileSize][(this.getX() + solidArea.height) / tileSize];
        int botRight = map[(this.getY() + solidArea.width) / tileSize][(this.getX() + solidArea.height) / tileSize];

        if (topLeft == SPEEDITEM) {
            map[this.getY() / tileSize][this.getX() / tileSize] = GRASS;
            for (Entity e : StaticEntities) {
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if (e instanceof SpeedItem && row == this.getY() / tileSize && col == this.getX() / tileSize) {
                    ((SpeedItem) e).setEaten(true);
                }
            }
        } else if (topRight == SPEEDITEM) {
            map[(this.getY() + solidArea.width) / tileSize][this.getX() / tileSize] = GRASS;
            for (Entity e : StaticEntities) {
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if (e instanceof SpeedItem && row == (this.getY() + solidArea.width) / tileSize && col == this.getX() / tileSize) {
                    ((SpeedItem) e).setEaten(true);
                }
            }
        } else if (botLeft == SPEEDITEM) {
            map[this.getY() / tileSize][(this.getX() + solidArea.height) / tileSize] = GRASS;
            for (Entity e : StaticEntities) {
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if (e instanceof SpeedItem && row == this.getY() / tileSize && col == (this.getX() + solidArea.height) / tileSize) {
                    ((SpeedItem) e).setEaten(true);
                }
            }
        } else if (botRight == SPEEDITEM) {
            map[(this.getY() + solidArea.width) / tileSize][(this.getX() + solidArea.height) / tileSize] = GRASS;
            for (Entity e : StaticEntities) {
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if (e instanceof SpeedItem && row == (this.getY() + solidArea.width) / tileSize && col == (this.getX() + solidArea.height) / tileSize) {
                    ((SpeedItem) e).setEaten(true);
                }
            }
        }

        if (topLeft == BOMBITEM) {
            map[this.getY() / tileSize][this.getX() / tileSize] = GRASS;
            for (Entity e : StaticEntities) {
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if (e instanceof BombItem && row == this.getY() / tileSize && col == this.getX() / tileSize) {
                    ((BombItem) e).setEaten(true);
                }
            }
        } else if (topRight == BOMBITEM) {
            map[(this.getY() + solidArea.width) / tileSize][this.getX() / tileSize] = GRASS;
            for (Entity e : StaticEntities) {
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if (e instanceof BombItem && row == (this.getY() + solidArea.width) / tileSize && col == this.getX() / tileSize) {
                    ((BombItem) e).setEaten(true);
                }
            }
        } else if (botLeft == BOMBITEM) {
            map[this.getY() / tileSize][(this.getX() + solidArea.height) / tileSize] = GRASS;
            for (Entity e : StaticEntities) {
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if (e instanceof BombItem && row == this.getY() / tileSize && col == (this.getX() + solidArea.height) / tileSize) {
                    ((BombItem) e).setEaten(true);
                }
            }
        } else if (botRight == BOMBITEM) {
            map[(this.getY() + solidArea.width) / tileSize][(this.getX() + solidArea.height) / tileSize] = GRASS;
            for (Entity e : StaticEntities) {
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if (e instanceof BombItem && row == (this.getY() + solidArea.width) / tileSize && col == (this.getX() + solidArea.height) / tileSize) {
                    ((BombItem) e).setEaten(true);
                }
            }
        }

        if (topLeft == FLAMEITEM) {
            map[this.getY() / tileSize][this.getX() / tileSize] = GRASS;
            for (Entity e : StaticEntities) {
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if (e instanceof FlameItem && row == this.getY() / tileSize && col == this.getX() / tileSize) {
                    ((FlameItem) e).setEaten(true);
                }
            }
        } else if (topRight == FLAMEITEM) {
            map[(this.getY() + solidArea.width) / tileSize][this.getX() / tileSize] = GRASS;
            for (Entity e : StaticEntities) {
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if (e instanceof FlameItem && row == (this.getY() + solidArea.width) / tileSize && col == this.getX() / tileSize) {
                    ((FlameItem) e).setEaten(true);
                }
            }
        } else if (botLeft == FLAMEITEM) {
            map[this.getY() / tileSize][(this.getX() + solidArea.height) / tileSize] = GRASS;
            for (Entity e : StaticEntities) {
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if (e instanceof FlameItem && row == this.getY() / tileSize && col == (this.getX() + solidArea.height) / tileSize) {
                    ((FlameItem) e).setEaten(true);
                }
            }
        } else if (botRight == FLAMEITEM) {
            map[(this.getY() + solidArea.width) / tileSize][(this.getX() + solidArea.height) / tileSize] = GRASS;
            for (Entity e : StaticEntities) {
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if (e instanceof FlameItem && row == (this.getY() + solidArea.width) / tileSize && col == (this.getX() + solidArea.height) / tileSize) {
                    ((FlameItem) e).setEaten(true);
                }
            }
        }

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
        //if (isAlive) {
            playerAnimation();
            run();
            checkPowerUp();
            checkAlive();

        //}
       // playerAfterDeath();

    }
}
