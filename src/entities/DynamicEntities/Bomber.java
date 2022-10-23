package entities.DynamicEntities;


import Control.CheckCollision;
import Control.GameManager;
import Control.SoundManager;
import static Menu.ViewManager.*;
import Menu.ViewManager;

import entities.Ai.BFS;
import entities.Entity;
import entities.Item.BombItem;
import entities.Item.FlameItem;
import entities.Item.SpeedItem;
import entities.StaticEntities.Portal;
import graphics.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.FileNotFoundException;

import static Control.GameManager.*;


public class Bomber extends Entity {
    public static int flamePowerUp = 0;
    public static int currentBomb = 1;
    protected Sprite _sprite;
    private boolean isAlive;
    private boolean isInside;

    private int timeAfterDeath = 18;
    private boolean isAutomated = false;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        solidArea = new Rectangle(4/2*3, 5/2*3, 16/2*3, 24/2*3);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.speed = 2;
        isInside = true;
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAutomated() {
        return isAutomated;
    }

    public void setAutomated(boolean automated) {
        isAutomated = automated;
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
            collisionBomb = false;
            CheckCollision.checkTile(this);
            checkInsideBomb();
            if(!isInside) {
                CheckCollision.checkCollisionBomb(this);
                isInside = true;
            }
            if (!collisionOn && !collisionBomb) {
                switch (direction) {
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
        CheckCollision.checkCollisionEnemy(this, GameManager.enemyEntities);
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
        int topRight = flameGrid[this.getY() / tileSize][(this.getX() + solidArea.width) / tileSize];
        int botLeft = flameGrid[(this.getY() + solidArea.height) / tileSize ][this.getX() / tileSize];
        int botRight = flameGrid[(this.getY() + solidArea.height) / tileSize][(this.getX() + solidArea.width) / tileSize];
        if (topLeft == FLAME || topRight == FLAME || botLeft == FLAME || botRight == FLAME) isAlive = false;
    }
    public void checkInsideBomb() {
        int topLeft = bombGrid[(this.getY() + solidAreaDefaultY) / tileSize][(this.getX() + solidAreaDefaultX)/ tileSize];
        int topRight = bombGrid[(this.getY() + solidAreaDefaultY) / tileSize][(this.getX() + solidArea.width + solidAreaDefaultX) / tileSize];
        int botLeft = bombGrid[(this.getY() + solidArea.height + solidAreaDefaultY) / tileSize ][(this.getX() + solidAreaDefaultX)  / tileSize];
        int botRight = bombGrid[(this.getY() + solidArea.height + solidAreaDefaultY) / tileSize][(this.getX() + +solidAreaDefaultX +solidArea.width) / tileSize];
        if(!(topLeft == BOMB && topRight == BOMB && botRight == BOMB && botLeft == BOMB)) {
            isInside = false;
        }
    }

    public void checkPowerUp() {
        int topLeft = map[this.getY() / tileSize][this.getX() / tileSize];
        int topRight = map[(this.getY() + solidArea.width) / tileSize][this.getX() / tileSize];
        int botLeft = map[this.getY() / tileSize][(this.getX() + solidArea.height) / tileSize];
        int botRight = map[(this.getY() + solidArea.width) / tileSize][(this.getX() + solidArea.height) / tileSize];

        if(topLeft == SPEEDITEM){
            map[this.getY() / tileSize][this.getX() / tileSize] = GRASS;
            for(Entity e : StaticEntities){
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if(e instanceof SpeedItem && row ==  this.getY() / tileSize && col == this.getX() / tileSize){
                    ((SpeedItem) e).setEaten(true);
                }
            }
        } else if (topRight == SPEEDITEM){
            map[(this.getY() + solidArea.width) / tileSize][this.getX() / tileSize] = GRASS;
            for(Entity e : StaticEntities){
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if(e instanceof SpeedItem && row ==  (this.getY() + solidArea.width) / tileSize && col == this.getX() / tileSize){
                    ((SpeedItem) e).setEaten(true);
                }
            }
        } else if (botLeft == SPEEDITEM){
            map[this.getY() / tileSize][(this.getX() + solidArea.height) / tileSize] = GRASS;
            for(Entity e : StaticEntities){
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if(e instanceof SpeedItem && row ==  this.getY() / tileSize && col == (this.getX() + solidArea.height) / tileSize){
                    ((SpeedItem) e).setEaten(true);
                }
            }
        } else if (botRight == SPEEDITEM){
            map[(this.getY() + solidArea.width) / tileSize][(this.getX() + solidArea.height) / tileSize] = GRASS;
            for(Entity e : StaticEntities){
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if(e instanceof SpeedItem && row ==  (this.getY() + solidArea.width) / tileSize && col == (this.getX() + solidArea.height) / tileSize){
                    ((SpeedItem) e).setEaten(true);
                }
            }
        }

        if(topLeft == BOMBITEM){
            map[this.getY() / tileSize][this.getX() / tileSize] = GRASS;
            for(Entity e : StaticEntities){
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if(e instanceof BombItem && row ==  this.getY() / tileSize && col == this.getX() / tileSize){
                    ((BombItem) e).setEaten(true);
                }
            }
        } else if (topRight == BOMBITEM){
            map[(this.getY() + solidArea.width) / tileSize][this.getX() / tileSize] = GRASS;
            for(Entity e : StaticEntities){
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if(e instanceof BombItem && row ==  (this.getY() + solidArea.width) / tileSize && col == this.getX() / tileSize){
                    ((BombItem) e).setEaten(true);
                }
            }
        } else if (botLeft == BOMBITEM){
            map[this.getY() / tileSize][(this.getX() + solidArea.height) / tileSize] = GRASS;
            for(Entity e : StaticEntities){
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if(e instanceof BombItem && row ==  this.getY() / tileSize && col == (this.getX() + solidArea.height) / tileSize){
                    ((BombItem) e).setEaten(true);
                }
            }
        } else if (botRight == BOMBITEM){
            map[(this.getY() + solidArea.width) / tileSize][(this.getX() + solidArea.height) / tileSize] = GRASS;
            for(Entity e : StaticEntities){
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if(e instanceof BombItem && row ==  (this.getY() + solidArea.width) / tileSize && col == (this.getX() + solidArea.height) / tileSize){
                    ((BombItem) e).setEaten(true);
                }
            }
        }

        if(topLeft == FLAMEITEM){
            map[this.getY() / tileSize][this.getX() / tileSize] = GRASS;
            for(Entity e : StaticEntities){
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if(e instanceof FlameItem && row ==  this.getY() / tileSize && col == this.getX() / tileSize){
                    ((FlameItem) e).setEaten(true);
                }
            }
        } else if (topRight == FLAMEITEM){
            map[(this.getY() + solidArea.width) / tileSize][this.getX() / tileSize] = GRASS;
            for(Entity e : StaticEntities){
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if(e instanceof FlameItem && row ==  (this.getY() + solidArea.width) / tileSize && col == this.getX() / tileSize){
                    ((FlameItem) e).setEaten(true);
                }
            }
        } else if (botLeft == FLAMEITEM){
            map[this.getY() / tileSize][(this.getX() + solidArea.height) / tileSize] = GRASS;
            for(Entity e : StaticEntities){
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if(e instanceof FlameItem && row ==  this.getY() / tileSize && col == (this.getX() + solidArea.height) / tileSize){
                    ((FlameItem) e).setEaten(true);
                }
            }
        } else if (botRight == FLAMEITEM){
            map[(this.getY() + solidArea.width) / tileSize][(this.getX() + solidArea.height) / tileSize] = GRASS;
            for(Entity e : StaticEntities){
                int row = e.getY() / tileSize;
                int col = e.getX() / tileSize;
                if(e instanceof FlameItem && row ==  (this.getY() + solidArea.width) / tileSize && col == (this.getX() + solidArea.height) / tileSize){
                    ((FlameItem) e).setEaten(true);
                }
            }
        }
    }

    public void checkLevelUp(){
        if(enemyEntities.size() > 0) return;
        for(Entity entity : StaticEntities){
            if(entity instanceof Portal && map[player.getY()/tileSize][player.getX()/tileSize] == PORTAL){
                System.out.println("has");
                ((Portal) entity).setPlayerPassed(true);
                break;
            }
        }
    }

    public void playerAfterDeath() throws FileNotFoundException {
        if (!isAlive) {
            SoundManager.playPlayerDeathSound();
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
                gameManager = null;
                player.reset();
                ViewManager.setGameOverScene();
            }
        }
    }


    @Override
    public void update() {
        if (isAlive) {
            playerAnimation();
            run();
            checkPowerUp();
            checkAlive();
            checkLevelUp();
            if(isAutomated) BFS.autoKillEnemies();
        }
        try {
            playerAfterDeath();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void reset(){
        flamePowerUp = 0;
        currentBomb = 1;
        this.speed = 2;
    }
}
