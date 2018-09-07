package net.fyloz.smb3.physic;

import com.badlogic.gdx.math.Vector2;

public class RectangleBody {

    private int x;
    private int y;
    private int width;
    private int height;

    // Properties
    private int mass = 0;
    private float initialSpeed = 0;

    private boolean applyGravity;
    private boolean onGround = false;

    private Vector2 forces = new Vector2(0, 0);

    public RectangleBody(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public RectangleBody setProperties(int mass, float initialSpeed) {
        this.mass = mass;
        this.initialSpeed = initialSpeed;
        return this;
    }

    public void applyGravity(boolean applyGravity) {
        this.applyGravity = applyGravity;
    }

    public void applyForce(Vector2 force) {
        this.forces = forces.add(force);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMass() {
        return mass;
    }

    public float getInitialSpeed() {
        return initialSpeed;
    }

    public boolean isGravityApplied() {
        return applyGravity;
    }

    public boolean isOnGround() {
        return onGround;
    }
}
