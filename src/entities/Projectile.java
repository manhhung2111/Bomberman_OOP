package entities;

import Control.CheckCollision;

import java.awt.*;

public abstract class Projectile extends Entity{
    Entity user;
    public int maxLife = 320;

    public int life = maxLife;
    public int projectileSpeed;
    public Projectile() {
        solidArea = new Rectangle(0,2,46,44);
    }

    @Override
    public void update() {

        switch(direction) {
            case "up":
                y -= projectileSpeed;
                break;
            case "down":
                y += projectileSpeed;
                break;
            case "right":
                x += projectileSpeed;
                break;
            case "left":
                x -= projectileSpeed;
                break;
        }
        life--;
        if(life<=0){
            this.alive = false;
        }
        CheckCollision.checkBullet(this);
    }
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.x = worldX;
        this.y = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = maxLife;

    }
}
