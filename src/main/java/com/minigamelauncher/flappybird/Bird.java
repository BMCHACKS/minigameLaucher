package com.minigamelauncher.flappybird;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bird extends GameObject {

    private double velocityY = 0;

    private static final double GRAVITY    = 0.25;
    private static final double JUMP_FORCE = -6.0;
    private static final double MAX_FALL   = 2.0;
    private static final double WIDTH      = 34;
    private static final double HEIGHT     = 24;

    private final double startX;
    private final double startY;

    public Bird(double startX, double startY, Image img) {
        super(startX, startY, WIDTH, HEIGHT, img);
        this.startX = startX;
        this.startY = startY;
    }

    @Override
    public void update() {
        velocityY += GRAVITY;
        if (velocityY > MAX_FALL) velocityY = MAX_FALL;
        y += velocityY;
        if (y < 0) {
            y = 0;
            velocityY = 0;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(x + width / 2, y + height / 2);
        double tilt = Math.min(Math.max(velocityY * 3, -25), 45);
        gc.rotate(tilt);
        gc.drawImage(img, -width / 2, -height / 2, width, height);
        gc.restore();
    }

    public void jump() {
        velocityY = JUMP_FORCE;
    }

    public void reset() {
        x = startX;
        y = startY;
        velocityY = 0;
    }

    public boolean isOutOfBounds(double boardHeight) {
        return y + height >= boardHeight;
    }
}
