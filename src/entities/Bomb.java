package entities;

import Control.CheckCollision;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import static entities.DynamicEntities.Bomber.flamePowerUp;
import static Control.GameManager.*;
public class Bomb extends Entity {
    private double timeToExplode = 120;
    private double timeAfterExplosion = 20;
    private int middleExplosionUp = 0; // Actual explosion radius to the north
    private int middleExplosionDown = 0; // Actual explosion radius to the south
    private int middleExplosionLeft = 0; // Actual explosion radius to the west
    private int middleExplosionRight = 0; // Actual explosion radius to the east

    private Entity explosionEdgeUp = null; // Actual explosion edge to the north
    private Entity explosionEdgeDown = null; // Actual explosion edge to the south
    private Entity explosionEdgeLeft = null; // Actual explosion edge to the west
    private Entity explosionEdgeRight = null; // Actual explosion edge to the east
    private int currentActiveFrame = 0; // Active bomb status (animation)
    private boolean isExploded = false;
    private boolean isCreatedEdge = false;
    private boolean isCreatedMiddle = false;
    private List<Entity> middleVerticalExplosion = new ArrayList<>(); // To store position of vertical explosion of the bomb when exploded
    private List<Entity> middleHorizontalExplosion = new ArrayList<>(); // To store position of horizontal explosion of the bomb when exploded

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void createExplosionEdge() {
        if (!CheckCollision.bombBlockDown(this, 0)) {
            explosionEdgeDown = new Bomb(this.getX() / tileSize, this.getY() / tileSize + 1
                    , Sprite.explosion_vertical_down_last2.getFxImage());
            if (flamePowerUp > 0) {
                for (int i = 1; i <= flamePowerUp; i++) {
                    if (!CheckCollision.bombBlockDown(this, i)) {
                        explosionEdgeDown.setY(explosionEdgeDown.getY() + tileSize);
                        middleExplosionDown++;
                    } else break;
                }
            }
            StaticEntities.add(explosionEdgeDown);
        }


        if (!CheckCollision.bombBlockUp(this, 0)) {
            explosionEdgeUp = new Bomb(this.getX() / tileSize, this.getY() / tileSize - 1
                    , Sprite.explosion_vertical_top_last2.getFxImage());
            if (flamePowerUp > 0) {
                for (int i = 1; i <= flamePowerUp; i++) {
                    if (!CheckCollision.bombBlockUp(this, i)) {
                        explosionEdgeUp.setY(explosionEdgeUp.getY() - tileSize);
                        middleExplosionUp++;
                    } else break;
                }
            }
            StaticEntities.add(explosionEdgeUp);
        }

        if (!CheckCollision.bombBlockLeft(this, 0)) {
            explosionEdgeLeft = new Bomb(this.getX() / tileSize - 1, this.getY() / tileSize
                    , Sprite.explosion_horizontal_left_last2.getFxImage());
            if (flamePowerUp > 0) {
                for (int i = 1; i <= flamePowerUp; i++) {
                    if (!CheckCollision.bombBlockLeft(this, i)) {
                        explosionEdgeLeft.setX(explosionEdgeLeft.getX() - tileSize);
                        middleExplosionLeft++;
                    } else break;
                }
            }
            StaticEntities.add(explosionEdgeLeft);
        }

        if (!CheckCollision.bombBlockRight(this, 0)) {
            explosionEdgeRight = new Bomb(this.getX() / tileSize + 1, this.getY() / tileSize
                    , Sprite.explosion_horizontal_right_last2.getFxImage());
            if (flamePowerUp > 0) {
                for (int i = 1; i <= flamePowerUp; i++) {
                    if (!CheckCollision.bombBlockRight(this, i)) {
                        explosionEdgeRight.setX(explosionEdgeRight.getX() + tileSize);
                        middleExplosionRight++;
                    } else break;
                }
            }
            StaticEntities.add(explosionEdgeRight);
        }
    }

    public void createExplosionMiddle() {
        Entity middle;
        for (int i = 1; i <= middleExplosionDown; i++) {
            middle = new Bomb(this.getX() / tileSize, this.getY() / tileSize + i
                    , Sprite.explosion_vertical2.getFxImage());
            middleVerticalExplosion.add(middle);
        }

        for (int i = 1; i <= middleExplosionUp; i++) {
            middle = new Bomb(this.getX() / tileSize, this.getY() / tileSize - i
                    , Sprite.explosion_vertical2.getFxImage());
            middleVerticalExplosion.add(middle);
        }

        for (int i = 1; i <= middleExplosionLeft; i++) {
            middle = new Bomb(this.getX() / tileSize - i, this.getY() / tileSize
                    , Sprite.explosion_horizontal2.getFxImage());
            middleHorizontalExplosion.add(middle);
        }

        for (int i = 1; i <= middleExplosionRight; i++) {
            middle = new Bomb(this.getX() / tileSize + i, this.getY() / tileSize
                    , Sprite.explosion_horizontal2.getFxImage());
            middleHorizontalExplosion.add(middle);
        }
        StaticEntities.addAll(middleHorizontalExplosion);
        StaticEntities.addAll(middleVerticalExplosion);
    }

    public void bombActiveAnimation() {
        if (!isExploded) {
            if (currentActiveFrame <= 30) {
                this.setImg(Sprite.bomb.getFxImage());
                currentActiveFrame += 3;
            } else if (currentActiveFrame <= 60) {
                this.setImg(Sprite.bomb_1.getFxImage());
                currentActiveFrame += 3;
            } else if (currentActiveFrame <= 90) {
                this.setImg(Sprite.bomb_2.getFxImage());
                currentActiveFrame = 0;
            }
        }
    }

    public void explode() {
        isExploded = true;
        if (!isCreatedEdge) createExplosionEdge();
        if (flamePowerUp > 0 && !isCreatedMiddle) createExplosionMiddle();
        this.setImg(Sprite.bomb_exploded2.getFxImage());
        flameGrid[this.getY() / tileSize][this.getX() / tileSize] = FLAME;
        if (explosionEdgeDown != null)
            flameGrid[explosionEdgeDown.getY() / tileSize][explosionEdgeDown.getX() / tileSize] = FLAME;
        if (explosionEdgeRight != null)
            flameGrid[explosionEdgeRight.getY() / tileSize][explosionEdgeRight.getX() / tileSize] = FLAME;
        if (explosionEdgeUp != null)
            flameGrid[explosionEdgeUp.getY() / tileSize][explosionEdgeUp.getX() / tileSize] = FLAME;
        if (explosionEdgeLeft != null)
            flameGrid[explosionEdgeLeft.getY() / tileSize][explosionEdgeLeft.getX() / tileSize] = FLAME;
        for (Entity e : middleHorizontalExplosion) {
            flameGrid[e.getY() / tileSize][e.getX() / tileSize] = FLAME;
        }
        for (Entity e : middleVerticalExplosion) {
            flameGrid[e.getY() / tileSize][e.getX() / tileSize] = FLAME;
        }
    }


    public void removeAfterExplosion() {
        isExploded = false;
        flameGrid[this.getY() / tileSize][this.getX() / tileSize] = GRASS;
        if (explosionEdgeDown != null) {
            flameGrid[explosionEdgeDown.getY() / tileSize][explosionEdgeDown.getX() / tileSize] = GRASS;
        }
        flameGrid[explosionEdgeDown.getY() / tileSize][explosionEdgeDown.getX() / tileSize] = GRASS;
        if (explosionEdgeRight != null)
            flameGrid[explosionEdgeRight.getY() / tileSize][explosionEdgeRight.getX() / tileSize] = GRASS;
        if (explosionEdgeUp != null)
            flameGrid[explosionEdgeUp.getY() / tileSize][explosionEdgeUp.getX() / tileSize] = GRASS;
        if (explosionEdgeLeft != null)
            flameGrid[explosionEdgeLeft.getY() / tileSize][explosionEdgeLeft.getX() / tileSize] = GRASS;
        for (Entity e : middleHorizontalExplosion) {
            flameGrid[e.getY() / tileSize][e.getX() / tileSize] = GRASS;
        }
        for (Entity e : middleVerticalExplosion) {
            flameGrid[e.getY() / tileSize][e.getX() / tileSize] = GRASS;
        }
        BombList.remove(this);
        StaticEntities.remove(explosionEdgeLeft);
        StaticEntities.remove(explosionEdgeDown);
        StaticEntities.remove(explosionEdgeUp);
        StaticEntities.remove(explosionEdgeRight);
        StaticEntities.removeAll(middleVerticalExplosion);
        StaticEntities.removeAll(middleHorizontalExplosion);
        middleVerticalExplosion.clear();
        middleHorizontalExplosion.clear();
        isCreatedEdge = false;
        isCreatedMiddle = false;
        middleExplosionRight = 0;
        middleExplosionLeft = 0;
        middleExplosionUp = 0;
        middleExplosionDown = 0;
        timeAfterExplosion = 20;
        timeToExplode = 120;
    }

    @Override
    public void update() {
        if (timeToExplode > 0) {
            bombActiveAnimation();
            timeToExplode--;
        } else {
            if (!isExploded) {
                explode();
            }
            if (timeAfterExplosion > 0) {
                timeAfterExplosion--;
            } else {
                removeAfterExplosion();
            }
        }
    }


}
