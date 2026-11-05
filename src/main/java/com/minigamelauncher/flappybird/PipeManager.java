package com.minigamelauncher.flappybird;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PipeManager {

    private final List<Pipe> pipes = new ArrayList<>();
    private final Image topPipeImg;
    private final Image bottomPipeImg;
    private final double boardWidth;
    private final double boardHeight;

    private static final double PIPE_HEIGHT    = 512;
    private static final double OPENING_RATIO  = 4;

    public PipeManager(Image topPipeImg, Image bottomPipeImg, double boardWidth, double boardHeight) {
        this.topPipeImg    = topPipeImg;
        this.bottomPipeImg = bottomPipeImg;
        this.boardWidth    = boardWidth;
        this.boardHeight   = boardHeight;
    }

    public void spawnPair() {
        double openingSpace = boardHeight / OPENING_RATIO;
        double randomY = (-PIPE_HEIGHT / 4) - Math.random() * (PIPE_HEIGHT / 2);
        pipes.add(new Pipe(boardWidth, randomY,                            topPipeImg));
        pipes.add(new Pipe(boardWidth, randomY + PIPE_HEIGHT + openingSpace, bottomPipeImg));
    }

    public int updateAll(Bird bird) {
        int scoreGained = 0;
        Iterator<Pipe> it = pipes.iterator();
        while (it.hasNext()) {
            Pipe pipe = it.next();
            pipe.update();
            if (pipe.checkPassed(bird)) {
                scoreGained++;
            }
            if (pipe.getX() + pipe.getWidth() < 0) {
                it.remove();
            }
        }
        return scoreGained / 2;
    }

    public void drawAll(GraphicsContext gc) {
        for (Pipe pipe : pipes) {
            pipe.draw(gc);
        }
    }

    public boolean anyCollision(Bird bird) {
        for (Pipe pipe : pipes) {
            if (CollisionDetector.hasCollision(bird, pipe)) return true;
        }
        return false;
    }

    public void clear() {
        pipes.clear();
    }
}
