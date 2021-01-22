package com.example.testinvaders.Classes;

public class Bala {
    // Coordenades centre
    private float x;     private float y;
    // Radi
    private float radius;
    // Other characteristics
    private int color;

    public Bala(float x, float y, float radius, int color ) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;

    }

}
