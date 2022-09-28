package entities;

import javafx.scene.image.Image;

public abstract class AnimatedEntity extends Entity{

    public AnimatedEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    public int _animate = 0;
    protected final int MAX_ANIMATE = 100015;

    protected void animate() {
        if(_animate < MAX_ANIMATE) {
            _animate ++;
        } else {
            _animate = 0;
        }
    }
    @Override
    public void update() {

    }
}
