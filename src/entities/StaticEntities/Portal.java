package entities.StaticEntities;

import MapLevels.MapLevel;
import entities.Entity;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.Map;

import static Control.GameManager.*;

public class Portal extends Entity {
    private boolean isPlayerPassed = false;
    public Portal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public boolean isPlayerPassed() {
        return isPlayerPassed;
    }

    public void setPlayerPassed(boolean playerPassed) {
        isPlayerPassed = playerPassed;
    }

    @Override
    public void update() {
        if(isPlayerPassed){
            level++;
            try {
                new MapLevel(level);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            player.setX(tileSize);
            player.setY(tileSize);
            player.reset();
        }
    }
}
