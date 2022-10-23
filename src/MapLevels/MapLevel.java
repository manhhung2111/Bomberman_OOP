package MapLevels;

import Menu.ViewManager;
import entities.DestroyableEntities.Brick;
import entities.DynamicEntities.*;
import entities.Item.BombItem;
import entities.Item.FlameItem;
import entities.Item.SpeedItem;
import entities.StaticEntities.Grass;
import entities.StaticEntities.Portal;
import entities.StaticEntities.Wall;
import graphics.Sprite;
import javafx.util.Pair;

import static Control.GameManager.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MapLevel {
    private final String LEVEL1_PATH = "res/levels/Level1.txt";
    private final String LEVEL2_PATH = "res/levels/Level2.txt";

    private final String LEVEL3_PATH = "res/levels/Level3.txt";

    public MapLevel(int level) throws FileNotFoundException {
        switch (level){
            case 1:
                createMap(LEVEL1_PATH);
                break;
            case 2:
                createMap(LEVEL2_PATH);
                break;
            case 3:
                createMap(LEVEL3_PATH);
                break;
            default:
                ViewManager.setWinnerScene();
        }
    }

    private void createMap(String path) throws FileNotFoundException {
        StaticEntities.clear();
        enemyEntities.clear();
        player.reset();
        File file = new File(path);
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
                char c = s.charAt(j);
                if (c == '1') {
                    map[i][j] = BRICK;
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    StaticEntities.add(new Portal(j, i, Sprite.portal.getFxImage()));
                    StaticEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    portal = new Pair<>(i , j);
                }else if (c == '2') {
                    map[i][j] = WALL;
                    StaticEntities.add( new Wall(j, i, Sprite.wall.getFxImage()));
                } else if (c == '3') {
                    map[i][j] = BRICK;
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    StaticEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                } else if(c == '5'){
                    map[i][j] = BRICK;
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    StaticEntities.add(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                    StaticEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                } else if(c == '6'){
                    map[i][j] = BRICK;
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    StaticEntities.add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                    StaticEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                }else if(c == '7'){
                    map[i][j] = BRICK;
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    StaticEntities.add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                    StaticEntities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                } else if(c == '#'){
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    enemyEntities.add(new Balloon(j, i, Sprite.balloom_left3.getFxImage()));
                }else if(c == '*'){
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    enemyEntities.add(new Oneal(j, i, Sprite.oneal_right1.getFxImage()));
                }else if(c == '@'){
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    enemyEntities.add(new Doll(j, i, Sprite.doll_right1.getFxImage()));
                } else if(c == 'M'){
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    enemyEntities.add(new Minvo(j, i, Sprite.doll_right1.getFxImage()));
                } else if(c == 'K'){
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    enemyEntities.add(new Kondoria(j, i, Sprite.doll_right1.getFxImage()));
                } else{
                    map[i][j] = GRASS;
                    StaticEntities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                }
            }
            i++;
        }
        enemyEntities.sort((o1, o2) -> {
            if (o1.getX() > o2.getX()) return 1;
            else if (o1.getX() == o1.getY()) return 0;
            return -1;
        });
        scanner.close();
    }
}
