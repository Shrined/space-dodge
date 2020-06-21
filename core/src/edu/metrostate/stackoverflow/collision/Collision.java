package edu.metrostate.stackoverflow.collision;

public abstract class Collision {

    private int x;
    private int y;
    private int width;
    private int height;

    public Collision(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void updatePos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean collidesWith(Collision cObj) {
        return x < cObj.x + cObj.width && y < cObj.y + cObj.height && x + width > cObj.x && y + height > cObj.y;
    }
}
