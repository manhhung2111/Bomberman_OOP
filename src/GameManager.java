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



/*import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameManager {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    private int[][] map = new int [HEIGHT][WIDTH];
    /**
     * -1: wall; 0: grass, 1: brick
     */

    private GraphicsContext graphicsContext;
    private Canvas canvas;

    private Scene scene;

    private List<Entity> DynamicEntities = new ArrayList<>();
    private List<Entity> StaticEntities = new ArrayList<>();
    public static Bomber player;

    // Key listeners
    private boolean isRightKeyPressed;
    private boolean isLeftKeyPressed;
    private boolean isUpKeyPressed;
    private boolean isDownKeyPressed;
    private boolean isSpaceKeyPressed;

    // Constructor

    GameManager(){
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

    public int[][] getMap() {
        return map;
    }

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
    //endregion

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

    public void createKeyListeners() {
        this.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int j = player.getX() / 32;
                int i = player.getY() / 32;
                if(event.getCode() == KeyCode.RIGHT){
                    isRightKeyPressed = true;
                    if(map[i][j+1] == 0) {
                        player.moveRight();
                        player.setImg(Sprite.player_right.getFxImage());
                    }
                } else if(event.getCode() == KeyCode.LEFT){
                    isLeftKeyPressed = true;
                    if(map[i][j-1] == 0) {
                        player.moveLeft();
                        player.setImg(Sprite.player_left.getFxImage());
                    }
                } else if(event.getCode() == KeyCode.DOWN){
                    isDownKeyPressed = true;
                    if(map[i+1][j] == 0) {
                        player.moveDown();
                        player.setImg(Sprite.player_down.getFxImage());
                    }
                } else if(event.getCode() == KeyCode.UP){
                    isUpKeyPressed = true;
                    if(map[i-1][j] == 0) {
                        player.moveUp();
                        player.setImg(Sprite.player_up.getFxImage());
                    }
                } else if(event.getCode() == KeyCode.SPACE){
                    isSpaceKeyPressed = true;
                    StaticEntities.add(new Bomb(player.getX()/32, player.getY()/32, Sprite.bomb.getFxImage()));
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
                player.stay();
            }
    });
    }

    public void update() {
        DynamicEntities.forEach(Entity::update);
        player.update();
    }

    public void render() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        StaticEntities.forEach(g -> g.render(graphicsContext));
        DynamicEntities.forEach(g -> g.render(graphicsContext));
        player.render(graphicsContext);
    }
}
