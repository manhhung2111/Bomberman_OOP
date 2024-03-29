package Control;

import java.lang.Math;
import entities.DynamicEntities.Bomber;
import entities.Entity;

import java.util.List;
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
                entityTopRow = (int)((entityTopWorldY - entity.getSpeed()) / tileSize);
                tileNum1 = map[entityTopRow][entityLeftCol];
                tileNum2 = map[entityTopRow][entityRightCol];
                if (tileNum1 == WALL || tileNum2 == WALL || tileNum1 == BRICK || tileNum2 == BRICK ) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (int)((entityBottomWorldY + entity.getSpeed()) / tileSize);
                tileNum1 = map[entityBottomRow][entityLeftCol];
                tileNum2 = map[entityBottomRow][entityRightCol];
                if (tileNum1 == WALL || tileNum2 == WALL || tileNum1 == BRICK || tileNum2 == BRICK) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (int)((entityLeftWorldX - entity.getSpeed()) / tileSize);
                tileNum1 = map[entityTopRow][entityLeftCol];
                tileNum2 = map[entityBottomRow][entityLeftCol];
                if (tileNum1 == WALL || tileNum2 == WALL || tileNum1 == BRICK || tileNum2 == BRICK) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (int)((entityRightWorldX + entity.getSpeed()) / tileSize);
                tileNum1 = map[entityTopRow][entityRightCol];
                tileNum2 = map[entityBottomRow][entityRightCol];
                if (tileNum1 == WALL || tileNum2 == WALL || tileNum1 == BRICK || tileNum2 == BRICK) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
    public static void checkCollisionBomb(Entity entity) {
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
                entityTopRow = (int)((entityTopWorldY - entity.getSpeed()) / tileSize);
                tileNum1 = bombGrid[entityTopRow][entityLeftCol];
                tileNum2 = bombGrid[entityTopRow][entityRightCol];
                if (tileNum1 == BOMB || tileNum2 == BOMB) {
                    entity.collisionBomb = true;
                }
                break;
            case "down":
                entityBottomRow = (int)((entityBottomWorldY + entity.getSpeed()) / tileSize);
                tileNum1 = bombGrid[entityBottomRow][entityLeftCol];
                tileNum2 = bombGrid[entityBottomRow][entityRightCol];
                if (tileNum1 == BOMB || tileNum2 == BOMB) {
                    entity.collisionBomb = true;
                }
                break;
            case "left":
                entityLeftCol = (int)((entityLeftWorldX - entity.getSpeed()) / tileSize);
                tileNum1 = bombGrid[entityTopRow][entityLeftCol];
                tileNum2 = bombGrid[entityBottomRow][entityLeftCol];
                if (tileNum1 == BOMB || tileNum2 == BOMB) {
                    entity.collisionBomb = true;
                }
                break;
            case "right":
                entityRightCol = (int)((entityRightWorldX + entity.getSpeed()) / tileSize);
                tileNum1 = bombGrid[entityTopRow][entityRightCol];
                tileNum2 = bombGrid[entityBottomRow][entityRightCol];
                if (tileNum1 == BOMB || tileNum2 == BOMB) {
                    entity.collisionBomb = true;
                }
                break;
        }

    }

    public static void checkCollisionEnemy(Entity entity1, List<Entity> enemy) {
        for(int i = 0; i < enemy.size(); i++) {
            entity1.solidArea.x = entity1.getX() + entity1 .solidArea.x;
            entity1.solidArea.y = entity1.getY() + entity1.solidArea.y;
            enemy.get(i).solidArea.x = enemy.get(i).getX() + enemy.get(i).solidArea.x;
            enemy.get(i).solidArea.y = enemy.get(i).getY() + enemy.get(i).solidArea.y;

            switch(entity1.direction) {
                case "up":
                    entity1.solidArea.y -= entity1.getSpeed();
                    break;
                case "down":
                    entity1.solidArea.y += entity1.getSpeed();
                    break;
                case "left":
                    entity1.solidArea.x -= entity1.getSpeed();
                    break;
                case "right":
                    entity1.solidArea.x += entity1.getSpeed();
                    break;
            }
            if(entity1.solidArea.intersects(enemy.get(i).solidArea)){
                if(entity1 instanceof Bomber){
                    ((Bomber) entity1).setAlive(false);
                }
            }
            entity1.solidArea.x = entity1.solidAreaDefaultX;
            entity1.solidArea.y = entity1.solidAreaDefaultY;
            enemy.get(i).solidArea.x = enemy.get(i).solidAreaDefaultX;
            enemy.get(i).solidArea.y = enemy.get(i).solidAreaDefaultY;
        }
    }
    public static void checkBullet(Entity entity) {
        player.solidArea.x = player.getX() + player.solidArea.x;
        player.solidArea.y = player.getY() + player.solidArea.y;
        entity.solidArea.x = entity.getX() + entity.solidArea.x;
        entity.solidArea.y = entity.getY() + entity.solidArea.y;

        switch(entity.direction) {
            case "up":
                player.solidArea.y -= player.getSpeed();
                break;
            case "down":
                player.solidArea.y += player.getSpeed();
                break;
            case "left":
                player.solidArea.x -= player.getSpeed();
                break;
            case "right":
                player.solidArea.x += player.getSpeed();
                break;
        }
        if(player.solidArea.intersects(entity.solidArea)){
            player.setAlive(false);
        }
        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
    }
}
