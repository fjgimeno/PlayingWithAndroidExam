package com.example.testinvaders.Classes;

public class Asteroide {

    private float x;
    private float y;
    private int ample;
    private int alt;
    private int speedX, speedY;


    public Asteroide(float x, float y, int ample, int alt, int speedX, int  speedY) {
        this.x = x;   this.y = y;
        this.ample = ample;  this.alt = alt;
        this.speedX = speedX;
        this.speedY = speedY;
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

    public float getAmple() {
        return ample;
    }

    public void setAmple(int ample) {
        this.ample = ample;
    }

    public float getAlt() {
        return alt;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
}
