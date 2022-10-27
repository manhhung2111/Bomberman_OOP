package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import graphics.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected int x;
    protected int y;

    protected Image img;
    public Rectangle solidArea;
    public boolean collisionOn = false;
    public boolean collisionBomb = false;
    protected int speed = 1; //speed of entity
    public int solidAreaDefaultX, solidAreaDefaultY;

    public String direction = ""; //direction of player
    public boolean alive = false;
    public Projectile projectile;

    public static final int FPS =60;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }
    public Entity() {}

    //region Getter and Setter Methods
    public int getFPS() {
        return FPS;
    }
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) { this.speed = speed;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
    //endregion

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();
}
