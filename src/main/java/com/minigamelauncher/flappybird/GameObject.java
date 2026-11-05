package com.minigamelauncher.flappybird;

import javafx.scene.image.Image;

public abstract class GameObject implements Drawable, Updatable {

    protected double x, y;
    protected double width, height;
    protected Image img;

    public GameObject(double x, double y, double width, double height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }

    public double getX()      { return x; }
    public double getY()      { return y; }
    public double getWidth()  { return width; }
    public double getHeight() { return height; }
    public Image  getImg()    { return img; }

    public javafx.geometry.Rectangle2D getBounds() {
        return new javafx.geometry.Rectangle2D(x, y, width, height);
    }
}
