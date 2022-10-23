package entities.Ai;
import static Control.GameManager.*;
import Control.GameManager.*;
import com.sun.xml.internal.ws.api.pipe.helper.PipeAdapter;
import entities.DestroyableEntities.Bomb;
import graphics.Sprite;
import javafx.util.Pair;

import java.util.*;

public class BFS {
    private static int[] dx = {-1, 0, 0, 1};
    private static int[] dy = {0, -1, 1, 0};

    private static int imageLeft = 0;
    private static int imageRight = 0;
    private static int imageUp = 0;
    private static int imageDown = 0;
    private static int remainingStep = 0;
    private static String direction = "right";

    public static boolean AI_isBombDeployed = false;
    public static boolean AI_isBombExploded = false;
    private static boolean isBrickPortalDestroyed = false;


    public static void autoKillEnemies(){
        Pair<Integer, Integer> nextEnemy = getNextEnemy();
        if(nextEnemy == null){
            goToThePortal();
        }else {
//            System.out.println("size: " + enemyEntities.size() + " " + nextEnemy.getKey() + " " + nextEnemy.getValue()
//                                + " " + map[nextEnemy.getKey()][nextEnemy.getValue()]);
            List<Pair<Integer, Integer>> path = findPathTo(nextEnemy);
            
            if(BombList.size() == 0 && path.size() > 2) {
                goTo(path.get(1));
            }
            else {
                killNextEnemy();
                if(AI_isBombDeployed) {
                    dodgeTheBomb();
                }
            }
        }
    }

    private static void killNextEnemy() {
        if(!AI_isBombDeployed){
            //System.out.println("bomb + " + player.getY()/tileSize +  ' ' + player.getX()/tileSize);
            BombList.add(new Bomb(player.getX()/tileSize, player.getY()/tileSize, Sprite.bomb.getFxImage()));
            bombGrid[player.getY()/tileSize][player.getX()/tileSize] = BOMB;
            AI_isBombDeployed = true;
        }
        if(AI_isBombExploded){
            AI_isBombDeployed = false;
            AI_isBombExploded = false;
        }
    }

    private static void dodgeTheBomb() {
        List<Pair<Integer, Integer>> path = findPathTo(new Pair<>(1, 1));
        if(path.size() > 2) goTo(path.get(1));
    }

    private static void goToThePortal() {
        destroyBrickToThePortal();
        if(isBrickPortalDestroyed){
            List<Pair<Integer, Integer>> path = findPathToPortal(portal);
            goTo(path.get(1));
        }
    }

    private static void destroyBrickToThePortal(){
        List<Pair<Integer, Integer>> path = findPathToPortal(portal);
        if(BombList.size() == 0 && path.size() > 2) goTo(path.get(1));
        else if(!isBrickPortalDestroyed){
            killNextEnemy();
            if(AI_isBombDeployed){
                dodgeTheBomb();
            }
            if(map[portal.getKey()][portal.getValue()] == PORTAL) isBrickPortalDestroyed = true;

        }
    }

    private static Pair<Integer, Integer> getNextEnemy(){
        if(enemyEntities.size() == 0) return null;
        return new Pair<>((enemyEntities.get(0).getY() + enemyEntities.get(0).solidArea.y)/tileSize,
                (enemyEntities.get(0).solidArea.x + enemyEntities.get(0).getX())/tileSize);
    }
    private static void goTo(Pair<Integer, Integer> coordinate){
        if(player.getX() % 48 == 0 && player.getY() % 48 == 0){
            remainingStep = 12;
            if(player.getX()/tileSize < coordinate.getValue()){
                direction = "right";
                moveRight();
            } else if(player.getX()/tileSize > coordinate.getValue()){
                direction = "left";
                moveLeft();
            } else if(player.getY()/tileSize < coordinate.getKey()){
                direction = "down";
                moveDown();
            } else if(player.getY()/tileSize > coordinate.getKey()) {
                direction = "up";
                moveUp();
            }
        }else{
            switch (direction){
                case "right":
                    moveRight();
                    break;
                case "left":
                    moveLeft();
                    break;
                case "down":
                    moveDown();
                    break;
                case "up":
                    moveUp();
                    break;
            }
        }

    }
    private static List<Pair<Integer, Integer>> findPathTo(Pair<Integer, Integer> nextEnemy){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        boolean[][] set = new boolean[HEIGHT][WIDTH];
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> parent = new HashMap<>();

        Pair<Integer, Integer> start = new Pair<>(player.getY()/tileSize, player.getX()/tileSize);
        Pair<Integer, Integer> dest = nextEnemy;

        queue.add(start);
        set[start.getKey()][start.getValue()] = true;
        parent.put(start, start);

        while(!queue.isEmpty() && !set[dest.getKey()][dest.getValue()]){
            int i = queue.peek().getKey();
            assert queue.peek() != null;
            int j = queue.peek().getValue();

            for(int k = 0; k < dx.length; k++){
                int new_i = i + dx[k];
                int new_j = j + dy[k];
                if(new_i < 0 || new_i >= HEIGHT) continue;
                if(new_j < 0 || new_j >= WIDTH) continue;
                Pair<Integer, Integer> neighbour = new Pair<>(new_i, new_j);
                if(map[new_i][new_j] == GRASS && !set[neighbour.getKey()][neighbour.getValue()]){
                    queue.add(neighbour);
                    parent.put(neighbour, queue.peek());
                    set[neighbour.getKey()][neighbour.getValue()] = true;
                }
            }
            queue.poll();
        }

        if(set[dest.getKey()][dest.getValue()]){
            while(dest.getValue().intValue() != start.getValue().intValue()
                    || dest.getKey().intValue() != start.getKey().intValue()){
                path.add(new Pair<>(dest.getKey(), dest.getValue()));
                dest = parent.get(dest);
            }
            path.add(start);
            Collections.reverse(path);
        }

        return path;
    }

    private static List<Pair<Integer, Integer>> findPathToPortal(Pair<Integer, Integer> portal){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        boolean[][] set = new boolean[HEIGHT][WIDTH];
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> parent = new HashMap<>();

        Pair<Integer, Integer> start = new Pair<>(player.getY()/tileSize, player.getX()/tileSize);
        Pair<Integer, Integer> dest = portal;

        queue.add(start);
        set[start.getKey()][start.getValue()] = true;
        parent.put(start, start);

        while(!queue.isEmpty() && !set[dest.getKey()][dest.getValue()]){
            int i = queue.peek().getKey();
            assert queue.peek() != null;
            int j = queue.peek().getValue();

            for(int k = 0; k < dx.length; k++){
                int new_i = i + dx[k];
                int new_j = j + dy[k];
                if(new_i < 0 || new_i >= HEIGHT) continue;
                if(new_j < 0 || new_j >= WIDTH) continue;
                Pair<Integer, Integer> neighbour = new Pair<>(new_i, new_j);
                if((map[new_i][new_j] == GRASS || new_i == portal.getKey() && new_j == portal.getValue() )&& !set[neighbour.getKey()][neighbour.getValue()]){
                    queue.add(neighbour);
                    parent.put(neighbour, queue.peek());
                    set[neighbour.getKey()][neighbour.getValue()] = true;
                }
            }
            queue.poll();
        }

        if(set[dest.getKey()][dest.getValue()]){
            while(dest.getValue().intValue() != start.getValue().intValue()
                    || dest.getKey().intValue() != start.getKey().intValue()){
                path.add(new Pair<>(dest.getKey(), dest.getValue()));
                dest = parent.get(dest);
            }
            path.add(start);
            Collections.reverse(path);
        }

        return path;
    }
    private static void moveLeft(){
        if(imageLeft <= 16){
            player.setImg(Sprite.player_left.getFxImage());
            imageLeft += 4;
        }else if(imageLeft <= 32){
            player.setImg(Sprite.player_left_1.getFxImage());
            imageLeft += 4;
        }else if(imageLeft <= 48){
            player.setImg(Sprite.player_left_2.getFxImage());
            imageLeft = 0;
        }
        remainingStep--;
        player.setX(player.getX()-4);
    }
    private static void moveRight(){
        if(imageRight <= 16){
            player.setImg(Sprite.player_right.getFxImage());
            imageRight += 4;
        }else if(imageRight <= 32){
            player.setImg(Sprite.player_right_1.getFxImage());
            imageRight += 4;
        }else if(imageRight <= 48){
            player.setImg(Sprite.player_right_2.getFxImage());
            imageRight = 0;
        }
        remainingStep--;
        player.setX(player.getX()+4);
    }
    private static void moveUp(){
        if(imageUp <= 16){
            player.setImg(Sprite.player_up.getFxImage());
            imageUp += 4;
        }else if(imageUp <= 32){
            player.setImg(Sprite.player_up_1.getFxImage());
            imageUp += 4;
        }else if(imageUp <= 48){
            player.setImg(Sprite.player_up_2.getFxImage());
            imageUp = 0;
        }
        remainingStep--;
        player.setY((player.getY() - 4));
    }
    private static void moveDown(){
        if(imageDown <= 16){
            player.setImg(Sprite.player_down.getFxImage());
            imageDown += 4;
        }else if(imageDown <= 32){
            player.setImg(Sprite.player_down_1.getFxImage());
            imageDown += 4;
        }else if(imageDown <= 48){
            player.setImg(Sprite.player_down_2.getFxImage());
            imageDown = 0;
        }
        remainingStep--;
        player.setY(player.getY()+4);
    }
}
