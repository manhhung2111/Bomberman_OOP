package entities.DynamicEntities;

import entities.Projectile;
import graphics.Sprite;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Bullet extends Projectile {

    public int bulletSpeed = 2;

    public Bullet() {
        projectileSpeed = bulletSpeed;
        life = maxLife;
        alive = false;
        this.direction = "down";
    }
    public void update() {
        getImage();
        super.update();

    }


    public void getImage() {

        if(this.direction == "up"){
            this.setImg(resample(new Image("/sprites/fireball_up_1.png"),Sprite.SCALED_SIZE/Sprite.DEFAULT_SIZE));
        } else if(this.direction == "down") {
            this.setImg(resample(new Image("/sprites/fireball_down_1.png"),Sprite.SCALED_SIZE/Sprite.DEFAULT_SIZE));
        } else if(this.direction == "left") {
            this.setImg(resample(new Image("/sprites/fireball_left_1.png"),Sprite.SCALED_SIZE/Sprite.DEFAULT_SIZE));
        } else if(this.direction == "right") {
            this.setImg(resample(new Image("/sprites/fireball_right_1.png"),Sprite.SCALED_SIZE/Sprite.DEFAULT_SIZE));
        }


    }
    private Image resample(Image input, int scaleFactor) {
        final int W = (int) input.getWidth();
        final int H = (int) input.getHeight();
        final int S = scaleFactor;

        WritableImage output = new WritableImage(
                W * S,
                H * S
        );

        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int argb = reader.getArgb(x, y);
                for (int dy = 0; dy < S; dy++) {
                    for (int dx = 0; dx < S; dx++) {
                        writer.setArgb(x * S + dx, y * S + dy, argb);
                    }
                }
            }
        }

        return output;
    }
}
