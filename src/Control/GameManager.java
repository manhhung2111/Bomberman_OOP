package Control;
import javafx.animation.AnimationTimer;
import entities.*;
import entities.DynamicEntities.Bomber;
import entities.StaticEntities.Grass;
import entities.StaticEntities.Wall;
import graphics.Sprite;
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
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static final int tileSize = 32;

    public static int[][] map = new int [HEIGHT][WIDTH];
    /**
     * -1: wall; 0: grass, 1: brick
     */
    public boolean isRunning = true;
    private GraphicsContext graphicsContext;
    private Canvas canvas;

    private Scene scene;

    private List<Entity> DynamicEntities = new ArrayList<>();
    private List<Entity> StaticEntities = new ArrayList<>();
    public static Bomber player;

    // Key listeners
    public static boolean isRightKeyPressed;
    public static boolean isLeftKeyPressed;
    public static  boolean isUpKeyPressed;
    public static boolean isDownKeyPressed;
    public boolean isSpaceKeyPressed;
    public long currentTime ;

    //public static CollisionChecker checkCollision = new CollisionChecker(this); //check collision for the whole game

    // Constructor

    public GameManager(){
        this.canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        this.graphicsContext = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        this.scene = new Scene(root);
        this.DynamicEntities = new ArrayList<>();
        this.StaticEntities = new ArrayList<>();
        player = new Bomber(1, 1, Sprite.player_right.getFxImage());
        //DynamicEntities.add(player);
        this.isDownKeyPressed = false;
        this.isLeftKeyPressed = false;
        this.isUpKeyPressed = false;
        this.isRightKeyPressed = false;
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
        while(scanner.hasNextLine()){
            line.add(scanner.nextLine());
        }
        for(String s : line){
            for (int j = 0; j < s.length(); j++) {
                Entity object;
                char c = s.charAt(j);
                if(c == '#'){
                    map[i][j] = -1;
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                } else if (c == '*') {
                    map[i][j] = 1;
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                } else {
                    map[i][j] = 0;
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                }
                this.StaticEntities.add(object);
            }
            i++;
        }
        scanner.close();
    }

    //check collision
    public static void checkTile(Entity entity){

        //pixel location
        int entityLeftWorldX = entity.getX() + entity.solidArea.x;
        int entityRightWorldX = entity.getX() + +entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.getY() + entity.solidArea.y;
        int entityBottomWorldY = entity.getY() + entity.solidArea.y + entity.solidArea.height;

        //map location
        int entityLeftCol  =entityLeftWorldX/tileSize;
        int entityRightCol = entityRightWorldX/tileSize;
        int entityTopRow = entityTopWorldY/tileSize;
        int entityBottomRow = entityBottomWorldY/tileSize;

        int tileNum1=0, tileNum2=0;
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / tileSize;
                tileNum1 = map[entityTopRow][entityLeftCol];
                tileNum2 = map[entityTopRow][entityRightCol];
                if ( tileNum1 == -1 || tileNum2 == -1||tileNum1 == 1 ||  tileNum2 == 1) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / tileSize;
                tileNum1 = map[entityBottomRow][entityLeftCol];
                tileNum2 = map[entityBottomRow][entityRightCol];
                if (tileNum1 == -1 || tileNum2 == -1||tileNum1 == 1 ||  tileNum2 == 1) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / tileSize;
                tileNum1 = map[entityTopRow][entityLeftCol];
                tileNum2 = map[entityBottomRow][entityLeftCol];
                if ( tileNum1 == -1 || tileNum2 == -1||tileNum1 == 1 ||  tileNum2 == 1) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / tileSize;
                tileNum1 = map[entityTopRow][entityRightCol];
                tileNum2 = map[entityBottomRow][entityRightCol];
                if ( tileNum1 == -1 ||  tileNum2 == -1||tileNum1 == 1 ||  tileNum2 == 1) {
                    entity.collisionOn = true;
                }
                break;
        }
        //check pos
        System.out.println("Tilenum1: " + tileNum1 +"\n" + "Tilenum2 :" + tileNum2);
    }

    public void createKeyListeners() {
        this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.RIGHT){
                    isRightKeyPressed = true;

                        player.direction ="right";

                } else if(event.getCode() == KeyCode.LEFT){
                    isLeftKeyPressed = true;

                        player.direction="left";
                } else if(event.getCode() == KeyCode.DOWN){
                    isDownKeyPressed = true;

                        player.direction = "down";

                } else if(event.getCode() == KeyCode.UP){
                    isUpKeyPressed = true;

                        player.direction="up";


                } else if(event.getCode() == KeyCode.SPACE){
                    isSpaceKeyPressed = true;
                    StaticEntities.add(new Bomb((player.getX()+tileSize/2)/32, (player.getY()+tileSize/2)/32, Sprite.bomb.getFxImage()));
                }
            }
        });

        this.scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.RIGHT){
                    isRightKeyPressed = false;


                } else if(event.getCode() == KeyCode.LEFT){
                    isLeftKeyPressed = false;


                } else if(event.getCode() == KeyCode.DOWN){
                    isDownKeyPressed = false;


                } else if(event.getCode() == KeyCode.UP){
                    isUpKeyPressed = false;


                } else if(event.getCode() == KeyCode.SPACE){
                    isSpaceKeyPressed = false;
                }

            }
    });
    }

    public void update() {
        //DynamicEntities.forEach(Entity::update);
        player.update();
    }

    public void render() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        StaticEntities.forEach(g -> g.render(graphicsContext));
        DynamicEntities.forEach(g -> g.render(graphicsContext));
        player.render(graphicsContext);

    }
    public void start() throws FileNotFoundException {
        AnimationTimer timer = new AnimationTimer() {
            double drawInterval =1000000000/player.getFPS();
            double delta = 0;
            long lastTime = System.nanoTime();
            long timer = 0;
            int drawCount = 0;
            @Override
            public void handle(long l) {
                //System.out.println(l);
                isRunning = true;
                delta += (l - lastTime) / drawInterval;
                timer += (l -lastTime);
                lastTime = l;
                if(delta >= 1) {
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

}

