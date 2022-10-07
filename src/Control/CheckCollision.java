package Control;

import entities.Entity;

import static Control.GameManager.*;
public class CheckCollision {
    public static int bombBlockDown(Entity entity, int flamePowerUp) {
        return map[entity.getY() / tileSize + 1 + flamePowerUp][entity.getX() / tileSize];
    }

    public static int bombBlockUp(Entity entity, int flamePowerUp) {
        return map[entity.getY() / tileSize - 1 - flamePowerUp][entity.getX() / tileSize];
    }

    public static int bombBlockLeft(Entity entity, int flamePowerUp) {
        return map[entity.getY() / tileSize][entity.getX() / tileSize - 1 - flamePowerUp];
    }

    public static int bombBlockRight(Entity entity, int flamePowerUp) {
        return map[entity.getY() / tileSize][entity.getX() / tileSize + 1 + flamePowerUp];
    }

    //check collision
    public static void checkTile(Entity entity) {
        //pixel location
        int entityLeftWorldX = entity.getX() + entity.solidArea.x;
        int entityRightWorldX = entity.getX() + +entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.getY() + entity.solidArea.y;
        int entityBottomWorldY = entity.getY() + entity.solidArea.y + entity.solidArea.height;

        //map location
        int entityLeftCol = entityLeftWorldX / tileSize;
        int entityRightCol = entityRightWorldX / tileSize;
        int entityTopRow = entityTopWorldY / tileSize;
        int entityBottomRow = entityBottomWorldY / tileSize;

        int tileNum1 = 0, tileNum2 = 0;
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.initialSpeed) / tileSize;
                tileNum1 = map[entityTopRow][entityLeftCol];
                tileNum2 = map[entityTopRow][entityRightCol];
                if (tileNum1 == WALL || tileNum2 == WALL || tileNum1 == BRICK || tileNum2 == BRICK) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.initialSpeed) / tileSize;
                tileNum1 = map[entityBottomRow][entityLeftCol];
                tileNum2 = map[entityBottomRow][entityRightCol];
                if (tileNum1 == WALL || tileNum2 == WALL || tileNum1 == BRICK || tileNum2 == BRICK) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.initialSpeed) / tileSize;
                tileNum1 = map[entityTopRow][entityLeftCol];
                tileNum2 = map[entityBottomRow][entityLeftCol];
                if (tileNum1 == WALL || tileNum2 == WALL || tileNum1 == BRICK || tileNum2 == BRICK) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.initialSpeed) / tileSize;
                tileNum1 = map[entityTopRow][entityRightCol];
                tileNum2 = map[entityBottomRow][entityRightCol];
                if (tileNum1 == WALL || tileNum2 == WALL || tileNum1 == BRICK || tileNum2 == BRICK) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
}
