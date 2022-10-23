package entities.DestroyableEntities;

import entities.Entity;
import entities.Item.BombItem;
import entities.Item.FlameItem;
import entities.Item.SpeedItem;
import entities.StaticEntities.Portal;
import graphics.Sprite;
import javafx.scene.image.Image;
import static Control.GameManager.*;
import entities.StaticEntities.Grass;

public class Brick extends Entity {
    private boolean isDestroyed = false;
    private int timeAfterExplosion = 20;
    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    @Override
    public void update() {
        if(isDestroyed) {
            if(timeAfterExplosion >= 14){
                this.setImg(Sprite.brick_exploded.getFxImage());
                timeAfterExplosion--;
            } else if (timeAfterExplosion >= 7) {
                this.setImg(Sprite.bomb_exploded1.getFxImage());
                timeAfterExplosion--;
            } else if (timeAfterExplosion > 0) {
                this.setImg(Sprite.brick_exploded2.getFxImage());
                timeAfterExplosion--;
            } else {
                int x = this.getX();
                int y = this.getY();
                StaticEntities.remove(this);
                for(Entity e : StaticEntities){
                    if(x == e.getX() && y == e.getY()){
                        if(e instanceof SpeedItem){
                            map[y / tileSize][x / tileSize] = SPEEDITEM;
                        }else if (e instanceof BombItem){
                            map[y / tileSize][x / tileSize] = BOMBITEM;
                        } else if (e instanceof FlameItem){
                            map[y / tileSize][x / tileSize] = FLAMEITEM;
                        } else if (e instanceof Portal){
                            map[y / tileSize][x / tileSize] = PORTAL;
                        } else{
                            map[y / tileSize][x / tileSize] = GRASS;
                        }
                    }
                }
                isDestroyed = false;
            }
        }
    }
}
