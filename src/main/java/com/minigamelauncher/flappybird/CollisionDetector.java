package com.minigamelauncher.flappybird;

import javafx.geometry.Rectangle2D;

public class CollisionDetector {

    private CollisionDetector() {
    }

    public static boolean hasCollision(Bird bird, Pipe pipe) {
        Rectangle2D birdBounds = bird.getBounds();
        Rectangle2D pipeBounds = pipe.getBounds();
        return birdBounds.intersects(
            pipeBounds.getMinX(),
            pipeBounds.getMinY(),
            pipeBounds.getWidth(),
            pipeBounds.getHeight()
        );
    }
}
