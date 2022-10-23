package entities.DynamicEntities;

import static Control.GameManager.*;
import Control.CheckCollision;
import entities.Entity;
import graphics.Sprite;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Doll extends Enemy {

    private static int[] dx = {-1, 0, 0, 1};
    private static int[] dy = {0, -1, 1, 0};

    private static int imageLeft = 0;
    private static int imageRight = 0;
    private static int imageUp = 0;
    private static int imageDown = 0;
    private static int remainingStep = 0;
    private int timeAfterDeath = 24;
    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        moveToPlayer();
        super.checkAlive();
        enemyAfterDeath();
    }

    public void moveToPlayer() {
        List<Pair<Integer, Integer>> path = findPathToPlayer();
        if(path.size() > 0 && player.isAlive()){ // There is a path from doll to player
            goTo(path.get(1));
        }
    }

    private List<Pair<Integer, Integer>> findPathToPlayer(){
        List<Pair<Integer, Integer>> path = new ArrayList<>();
        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        boolean[][] set = new boolean[HEIGHT][WIDTH];
        Map<Pair<Integer, Integer>, Pair<Integer, Integer>> parent = new HashMap<>();

        Pair<Integer, Integer> start = new Pair<>(this.getY()/tileSize, this.getX()/tileSize);
        Pair<Integer, Integer> dest = new Pair<>((player.getY() + player.solidArea.y) / tileSize,
                                                  (player.getX() + player.solidArea.x) / tileSize );

        queue.add(start);
        set[start.getKey()][start.getValue()] = true;
        parent.put(start, start);

        while(!queue.isEmpty() && !set[dest.getKey()][dest.getValue()]){
            assert queue.peek() != null;
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
    private void goTo(Pair<Integer, Integer> coordinate){
        this.collisionOn = false;
        this.collisionBomb = false;
        CheckCollision.checkCollisionBomb(this);
        CheckCollision.checkTile(this);
        if(collisionOn || collisionBomb) return;
        if(this.getX() % 48 == 0 && this.getY() % 48 == 0){
            remainingStep = 24;
            if(this.getX()/tileSize < coordinate.getValue()){
                this.direction = "right";
                moveRight();
            } else if(this.getX()/tileSize > coordinate.getValue()){
                this.direction = "left";
                moveLeft();
            } else if(this.getY()/tileSize < coordinate.getKey()){
                this.direction = "down";
                moveDown();
            } else if(this.getY()/tileSize > coordinate.getKey()) {
                this.direction = "up";
                moveUp();
            }
        }else{
            switch (this.direction){
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

    private void moveLeft(){
        if(imageLeft <= 16){
            this.setImg(Sprite.doll_left1.getFxImage());
            imageLeft += 2;
        }else if(imageLeft <= 32){
            this.setImg(Sprite.doll_left2.getFxImage());
            imageLeft += 2;
        }else if(imageLeft <= 48){
            this.setImg(Sprite.doll_left3.getFxImage());
            imageLeft = 0;
        }
        remainingStep--;
        this.setX(this.getX()-2);
    }
    private void moveRight(){
        if(imageRight <= 16){
            this.setImg(Sprite.doll_right1.getFxImage());
            imageRight += 2;
        }else if(imageRight <= 32){
            this.setImg(Sprite.doll_right2.getFxImage());
            imageRight += 2;
        }else if(imageRight <= 48){
            this.setImg(Sprite.doll_right3.getFxImage());
            imageRight = 0;
        }
        remainingStep--;
        this.setX(this.getX()+2);
    }
    private void moveUp(){
        if(imageUp <= 16){
            this.setImg(Sprite.doll_left1.getFxImage());
            imageUp += 2;
        }else if(imageUp <= 32){
            this.setImg(Sprite.doll_left2.getFxImage());
            imageUp += 2;
        }else if(imageUp <= 48){
            this.setImg(Sprite.doll_left3.getFxImage());
            imageUp = 0;
        }
        remainingStep--;
        this.setY((this.getY() - 2));
    }
    private void moveDown(){
        if(imageDown <= 16){
            this.setImg(Sprite.doll_right1.getFxImage());
            imageDown += 2;
        }else if(imageDown <= 32){
            this.setImg(Sprite.doll_right2.getFxImage());
            imageDown += 2;
        }else if(imageDown <= 48){
            this.setImg(Sprite.doll_right3.getFxImage());
            imageDown = 0;
        }
        remainingStep--;
        this.setY(this.getY()+2);
    }

    public void enemyAfterDeath() {
        if (isDead) {
            this.setSpeed(0);
            if (timeAfterDeath >= 18) {
                this.setImg(Sprite.doll_dead.getFxImage());
                timeAfterDeath--;
            } else if (timeAfterDeath >= 12) {
                this.setImg(Sprite.mob_dead1.getFxImage());
                timeAfterDeath--;
            } else if (timeAfterDeath >= 6) {
                this.setImg(Sprite.mob_dead2.getFxImage());
                timeAfterDeath--;
            } else if(timeAfterDeath >= 0){
                this.setImg(Sprite.mob_dead3.getFxImage());
                timeAfterDeath--;
            }
            else {
                enemyEntities.remove(this);
            }
        }
    }
}

