package Control;

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
    public static final int tileSize = 32;

    public static int[][] map = new int[HEIGHT][WIDTH];
    public static int[][] bombGrid = new int[HEIGHT][WIDTH];
    public boolean isRunning = true;
    private GraphicsContext graphicsContext;
    private Canvas canvas;

    private Scene scene;

    public static List<Entity> DynamicEntities = new ArrayList<>();
    public static List<Entity> StaticEntities = new ArrayList<>();
    public static List<Entity> BombList = new ArrayList<>();
    public static List<Entity> enemyEntities = new ArrayList<>();
    public static int[][] flameGrid = new int[HEIGHT][WIDTH];
    public static Bomber player;

    // Key listeners
    public static boolean isRightKeyPressed;
    public static boolean isLeftKeyPressed;
    public static boolean isUpKeyPressed;
    public static boolean isDownKeyPressed;
    public boolean isSpaceKeyPressed;
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
    //end region


    public void createMap() throws FileNotFoundException {
        File file = new File("res/levels/Level1.txt");
        Scanner scanner = new Scanner(file);
        int level = Integer.parseInt(scanner.next());
        int rows = Integer.parseInt(scanner.next());
        int cols = Integer.parseInt(scanner.next());
        scanner.nextLine();
        int i = 0;
        List<String> line = new ArrayList<>();
        while (scanner.hasNextLine()) {
            line.add(scanner.nextLine());
        }
        for (String s : line) {
            for (int j = 0; j < s.length(); j++) {
                Entity object;
                char c = s.charAt(j);
                if (c == '2') {
                    map[i][j] = WALL;
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    StaticEntities.add(object);
                } else if (c == '3') {
                    map[i][j] = BRICK;
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    StaticEntities.add(object);
                } else if(c == '5'){
                    map[i][j] = BRICK;
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    StaticEntities.add(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    StaticEntities.add(object);
                } else if(c == '6'){
                    map[i][j] = BRICK;
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    StaticEntities.add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    StaticEntities.add(object);
                }else if(c == '7'){
                    map[i][j] = BRICK;
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    StaticEntities.add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    StaticEntities.add(object);
                } else if(c == '#'){
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    enemyEntities.add(new Balloon(j, i, Sprite.balloom_left3.getFxImage()));
                }else if(c == '*'){
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    enemyEntities.add(new Oneal(j, i, Sprite.oneal_right1.getFxImage()));
                }else if(c == '@'){
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    enemyEntities.add(new Doll(j, i, Sprite.doll_right1.getFxImage()));
                } else{
                    map[i][j] = GRASS;
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    StaticEntities.add(object);
                }

            }
            i++;
        }
        scanner.close();
    }

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
                    if(Bomber.currentBomb > 0){
                        isSpaceKeyPressed = true;
                        Bomb newBomb = new Bomb((player.getX() + tileSize / 2) / 32,
                                (player.getY() + tileSize / 2) / 32, Sprite.bomb.getFxImage());
                        bombGrid[newBomb.getY()/tileSize][newBomb.getX()/tileSize] = BOMB;
                        BombList.add(newBomb);
                        Bomber.currentBomb--;
                    }
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
    }

    public void render() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        StaticEntities.forEach(g -> g.render(graphicsContext));
        BombList.forEach(g -> g.render(graphicsContext));
        enemyEntities.forEach(g -> g.render(graphicsContext));
        DynamicEntities.forEach(g -> g.render(graphicsContext));
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
                lastTime = l;
                if (delta >= 1) {
                    render();
                    update();
                    createKeyListeners();
                    delta--;
                }
            }
        };
        timer.start();
        createMap();
    }


    public void stop() throws Exception {
        System.out.println("STOP");
        Platform.exit();
        System.exit(0);
    }

}

