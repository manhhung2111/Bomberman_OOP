package entities.DestroyableEntities;

import entities.Entity;
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
                map[this.getY() / tileSize][this.getX() / tileSize] = GRASS;
                StaticEntities.add(new Grass(this.getX() / 32, this.getY() / 32, Sprite.grass.getFxImage()));
                StaticEntities.remove(this);
            }
        }
    }
}
