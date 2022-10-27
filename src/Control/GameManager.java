package Control;

import MapLevels.MapLevel;
import Menu.Sound;
import entities.Ai.BFS;
import entities.DestroyableEntities.Bomb;
import entities.DestroyableEntities.Brick;
import entities.DynamicEntities.*;

import entities.Entity;
import entities.Item.BombItem;
import entities.Item.FlameItem;
import entities.Item.SpeedItem;
import entities.StaticEntities.Grass;
import entities.StaticEntities.Wall;
import graphics.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManager {
    public static final int GRASS = 0;
    public static final int PORTAL = 1;
    public static final int WALL = 2;
    public static final int BRICK = 3;
    public static final int PLAYER = 4;
    public static final int BOMBITEM = 5;
    public static final int SPEEDITEM = 6;
    public static final int FLAMEITEM = 7;

    public static final int BOMB = 8;
    public static final int FLAME = 9;
    public static final char BALLOON = '#';
    public static final int ONEAL = '*';
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static final int tileSize = Sprite.SCALED_SIZE;

    public static int[][] map = new int[HEIGHT][WIDTH];
    public static int[][] bombGrid = new int[HEIGHT][WIDTH];
    private boolean isRunning = true;
    private boolean isMapCreated = false;
    public static int level = 1;

    public static GameManager gameManager = null;

    private GraphicsContext graphicsContext;
    private Canvas canvas;

    private Scene scene;

    public static List<Entity> DynamicEntities = new ArrayList<>();
    public static List<Entity> StaticEntities = new ArrayList<>();
    public static List<Entity> BombList = new ArrayList<>();
    public static List<Entity> enemyEntities = new ArrayList<Entity>();
    public static List<Entity> projectileList = new ArrayList<>();
    public static int[][] flameGrid = new int[HEIGHT][WIDTH];
    public static Bomber player;

    // Key listeners
    public static boolean isRightKeyPressed;
    public static boolean isLeftKeyPressed;
    public static boolean isUpKeyPressed;
    public static boolean isDownKeyPressed;
    public boolean isSpaceKeyPressed;

    public static Pair<Integer, Integer> portal;
    // Constructor

    public GameManager() {
        this.canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        this.graphicsContext = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        this.scene = new Scene(root);
        DynamicEntities = new ArrayList<>();
        StaticEntities = new ArrayList<>();
        player = new Bomber(1, 1, Sprite.player_right.getFxImage());
        DynamicEntities.add(player);
        isDownKeyPressed = false;
        isLeftKeyPressed = false;
        isUpKeyPressed = false;
        isRightKeyPressed = false;
    }

    //region Getter and Setter Methods
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Scene getScene() {
        return scene;
    }

    public List<Entity> getDynamicEntities() {
        return DynamicEntities;
    }

    public List<Entity> getStaticEntities() {
        return StaticEntities;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isMapCreated() {
        return isMapCreated;
    }

    public void setMapCreated(boolean mapCreated) {
        isMapCreated = mapCreated;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    //endregion

    public void createKeyListeners() {
        this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = true;
                    player.direction = "right";
                } else if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;
                    player.direction = "left";
                } else if (event.getCode() == KeyCode.DOWN) {
                    isDownKeyPressed = true;
                    player.direction = "down";
                } else if (event.getCode() == KeyCode.UP) {
                    isUpKeyPressed = true;
                    player.direction = "up";
                } else if (event.getCode() == KeyCode.SPACE) {
                    Bomb newBomb = new Bomb((player.getX() + tileSize / 2) / tileSize,
                            (player.getY() + tileSize / 2) / tileSize, Sprite.bomb.getFxImage());
                    if(Bomber.currentBomb > 0 && bombGrid[newBomb.getY()/tileSize][newBomb.getX()/tileSize] != BOMB) {
                        SoundManager.playBombPlantedSound();
                        isSpaceKeyPressed = true;
                        bombGrid[newBomb.getY()/tileSize][newBomb.getX()/tileSize] = BOMB;
                        BombList.add(newBomb);
                        Bomber.currentBomb--;
                    }
                } else if(event.getCode() == KeyCode.S){
                    player.setAutomated(true);
                }
            }
        });

        this.scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                } else if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                } else if (event.getCode() == KeyCode.DOWN) {
                    isDownKeyPressed = false;
                } else if (event.getCode() == KeyCode.UP) {
                    isUpKeyPressed = false;
                } else if (event.getCode() == KeyCode.SPACE) {
                    isSpaceKeyPressed = false;
                }
            }
        });
    }

    public void update() {
        for(Entity e : StaticEntities){
            if(e instanceof SpeedItem) ((SpeedItem) e ).update(player);
            else e.update();
        }
        BombList.forEach(Entity::update);
        enemyEntities.forEach(Entity::update);
        DynamicEntities.forEach(Entity::update);
        projectileList.forEach(Entity::update);
    }

    public void render() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        StaticEntities.forEach(g -> g.render(graphicsContext));
        BombList.forEach(g -> g.render(graphicsContext));
        enemyEntities.forEach(g -> g.render(graphicsContext));
        DynamicEntities.forEach(g -> g.render(graphicsContext));
        projectileList.forEach(g->g.render(graphicsContext));
    }

    public void start() throws FileNotFoundException {
        AnimationTimer timer = new AnimationTimer() {
            final double drawInterval = 1000000000 / player.getFPS();
            double delta = 0;
            long lastTime = System.nanoTime();

            @Override
            public void handle(long l) {
                isRunning = true;
                delta += (l - lastTime) / drawInterval;
                //System.out.println(delta);
                lastTime = l;
                if (delta >= 1) {
                    render();
                    update();
                    createKeyListeners();
                    delta--;
                }
                if(!isMapCreated) {
                    try {
                        new MapLevel(level);
                        isMapCreated = true;
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        timer.start();
    }


    public void reset(AnimationTimer timer){
        this.canvas = null;
        this.graphicsContext = null;
        this.scene = null;
        DynamicEntities.clear();
        StaticEntities.clear();
        BombList.clear();
        enemyEntities.clear();;
        player = null;
        isDownKeyPressed = false;
        isLeftKeyPressed = false;
        isUpKeyPressed = false;
        isRightKeyPressed = false;
        isMapCreated = false;
        timer.stop();
    }
}

