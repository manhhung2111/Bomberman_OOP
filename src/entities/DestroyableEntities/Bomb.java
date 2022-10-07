package entities.DestroyableEntities;

import Control.CheckCollision;
import entities.DynamicEntities.Bomber;
import entities.Entity;
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
        if (CheckCollision.bombBlockDown(this, 0) == GRASS) {
            explosionEdgeDown = new Flame(this.getX() / tileSize, this.getY() / tileSize + 1
                    , Sprite.explosion_vertical_down_last2.getFxImage());
            if (flamePowerUp > 0) {
                for (int i = 1; i <= flamePowerUp; i++) {
                    if (CheckCollision.bombBlockDown(this, i) == GRASS) {
                        explosionEdgeDown.setY(explosionEdgeDown.getY() + tileSize);
                        middleExplosionDown++;
                    } else if (CheckCollision.bombBlockDown(this, i) == BRICK) {
                        int row = (explosionEdgeDown.getY() + tileSize) / tileSize;
                        int col = explosionEdgeDown.getX() / tileSize;
                        flameGrid[row][col] = FLAME;
                        break;
                    } else break;
                }
            }
            StaticEntities.add(explosionEdgeDown);
        } else if (CheckCollision.bombBlockDown(this, 0) == BRICK) {
            int row = (this.getY() + tileSize) / tileSize;
            int col = this.getX() / tileSize;
            flameGrid[row][col] = FLAME;
        }


        if (CheckCollision.bombBlockUp(this, 0) == GRASS) {
            explosionEdgeUp = new Flame(this.getX() / tileSize, this.getY() / tileSize - 1
                    , Sprite.explosion_vertical_top_last2.getFxImage());
            if (flamePowerUp > 0) {
                for (int i = 1; i <= flamePowerUp; i++) {
                    if (CheckCollision.bombBlockUp(this, i) == GRASS) {
                        explosionEdgeUp.setY(explosionEdgeUp.getY() - tileSize);
                        middleExplosionUp++;
                    } else if (CheckCollision.bombBlockUp(this, i) == BRICK) {
                        int row = (explosionEdgeUp.getY() - tileSize) / tileSize;
                        int col = explosionEdgeUp.getX() / tileSize;
                        flameGrid[row][col] = FLAME;
                        break;
                    } else break;
                }
            }
            StaticEntities.add(explosionEdgeUp);
        } else if (CheckCollision.bombBlockUp(this, 0) == BRICK) {
            int row = (this.getY() - tileSize) / tileSize;
            int col = this.getX() / tileSize;
            flameGrid[row][col] = FLAME;
        }

        if (CheckCollision.bombBlockLeft(this, 0) == GRASS) {
            explosionEdgeLeft = new Flame(this.getX() / tileSize - 1, this.getY() / tileSize
                    , Sprite.explosion_horizontal_left_last2.getFxImage());
            if (flamePowerUp > 0) {
                for (int i = 1; i <= flamePowerUp; i++) {
                    if (CheckCollision.bombBlockLeft(this, i) == GRASS) {
                        explosionEdgeLeft.setX(explosionEdgeLeft.getX() - tileSize);
                        middleExplosionLeft++;
                    } else if (CheckCollision.bombBlockLeft(this, i) == BRICK) {
                        int row = explosionEdgeLeft.getY() / tileSize;
                        int col = (explosionEdgeLeft.getX() - tileSize) / tileSize;
                        flameGrid[row][col] = FLAME;
                        break;
                    } else break;
                }
            }
            StaticEntities.add(explosionEdgeLeft);
        } else if (CheckCollision.bombBlockLeft(this, 0) == BRICK) {
            int row = this.getY() / tileSize;
            int col = (this.getX() - tileSize) / tileSize;
            flameGrid[row][col] = FLAME;
        }

        if (CheckCollision.bombBlockRight(this, 0) == GRASS) {
            explosionEdgeRight = new Flame(this.getX() / tileSize + 1, this.getY() / tileSize
                    , Sprite.explosion_horizontal_right_last2.getFxImage());
            if (flamePowerUp > 0) {
                for (int i = 1; i <= flamePowerUp; i++) {
                    if (CheckCollision.bombBlockRight(this, i) == GRASS) {
                        explosionEdgeRight.setX(explosionEdgeRight.getX() + tileSize);
                        middleExplosionRight++;
                    } else if (CheckCollision.bombBlockRight(this, i) == BRICK) {
                        int row = explosionEdgeRight.getY() / tileSize;
                        int col = (explosionEdgeRight.getX() + tileSize) / tileSize;
                        flameGrid[row][col] = FLAME;
                        break;
                    } else break;
                }
            }
            StaticEntities.add(explosionEdgeRight);
        } else if (CheckCollision.bombBlockRight(this, 0) == BRICK) {
            int row = this.getY() / tileSize;
            int col = (this.getX() + tileSize) / tileSize;
            flameGrid[row][col] = FLAME;
        }
    }

    public void createExplosionMiddle() {
        Entity middle;
        for (int i = 1; i <= middleExplosionDown; i++) {
            middle = new Flame(this.getX() / tileSize, this.getY() / tileSize + i
                    , Sprite.explosion_vertical2.getFxImage());
            middleVerticalExplosion.add(middle);
        }

        for (int i = 1; i <= middleExplosionUp; i++) {
            middle = new Flame(this.getX() / tileSize, this.getY() / tileSize - i
                    , Sprite.explosion_vertical2.getFxImage());
            middleVerticalExplosion.add(middle);
        }

        for (int i = 1; i <= middleExplosionLeft; i++) {
            middle = new Flame(this.getX() / tileSize - i, this.getY() / tileSize
                    , Sprite.explosion_horizontal2.getFxImage());
            middleHorizontalExplosion.add(middle);
        }

        for (int i = 1; i <= middleExplosionRight; i++) {
            middle = new Flame(this.getX() / tileSize + i, this.getY() / tileSize
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
        Bomber.currentBomb++;
    }

    public void destroyObjectsOnFlame(){
        for(Entity entity : StaticEntities) {
            if(entity instanceof Brick) {
                int row = entity.getY() / tileSize;
                int col = entity.getX() / tileSize;
                if(flameGrid[row][col] == FLAME){
                    ((Brick) entity).setDestroyed(true);
                }
            }
        }
    }

    public void removeAfterExplosion() {
        isExploded = false;
        flameGrid[this.getY() / tileSize][this.getX() / tileSize] = GRASS;
        if (explosionEdgeDown != null)
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
        if(explosionEdgeLeft != null) StaticEntities.remove(explosionEdgeLeft);
        if(explosionEdgeDown != null) StaticEntities.remove(explosionEdgeDown);
        if(explosionEdgeUp != null) StaticEntities.remove(explosionEdgeUp);
        if(explosionEdgeRight != null) StaticEntities.remove(explosionEdgeRight);
        StaticEntities.removeAll(middleVerticalExplosion);
        StaticEntities.removeAll(middleHorizontalExplosion);
        if(middleVerticalExplosion.size() > 0)middleVerticalExplosion.clear();
        if(middleHorizontalExplosion.size() > 0) middleHorizontalExplosion.clear();
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
                destroyObjectsOnFlame();
            }
            if (timeAfterExplosion > 0) {
                timeAfterExplosion--;
            } else {
                removeAfterExplosion();
            }
        }
    }


}
