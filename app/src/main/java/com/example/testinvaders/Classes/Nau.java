package com.example.testinvaders.Classes;

public class Nau {

    private float x;
    private float y;
    private float ample;
    private float alt;
    private float ultimaX;
    private float ultimaY;
    private boolean valor;

    // Other characteristics
    private int color;

    public Nau(float x, float y, float ample, float alt, int color) {
        this.x = x;
        this.y = y;
        this.ample = ample;
        this.alt = alt;
        this.valor = false;
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

    public float getAmple() {
        return ample;
    }

    public void setAmple(float ample) {
        this.ample = ample;
    }

    public float getAlt() {
        return alt;
    }

    public void setAlt(float alt) {
        this.alt = alt;
    }

    public float getUltimaX() {
        return ultimaX;
    }

    public void setUltimaX(float ultimaX) {
        this.ultimaX = ultimaX;
    }

    public float getUltimaY() {
        return ultimaY;
    }

    public void setUltimaY(float ultimaY) {
        this.ultimaY = ultimaY;
    }

    public boolean isValor() {
        return valor;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }

}
