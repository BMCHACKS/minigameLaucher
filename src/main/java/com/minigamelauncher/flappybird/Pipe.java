package com.minigamelauncher.flappybird;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Pipe extends GameObject {

    private boolean passed = false;

    private static final double VELOCITY_X = -2;
    private static final double WIDTH      = 64;
    private static final double HEIGHT     = 512;

    public Pipe(double x, double y, Image img) {
        super(x, y, WIDTH, HEIGHT, img);
    }

    @Override
    public void update() {
        x += VELOCITY_X;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(img, x, y, width, height);
    }

    public boolean checkPassed(Bird bird) {
        if (!passed && bird.getX() > x + width) {
            passed = true;
            return true;
        }
        return false;
    }
}
