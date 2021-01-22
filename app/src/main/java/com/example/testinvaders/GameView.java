package com.example.testinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.testinvaders.Classes.Asteroide;
import com.example.testinvaders.Classes.Bala;
import com.example.testinvaders.Classes.Bola;
import com.example.testinvaders.Classes.Nau;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    String TAG = "SpaceInvaders";
    // The thread
    private GameThread gameThread = null;
    // The bala
    private int x, x0, y, y0;
    private static int radius = 20;
    Boolean dispara = false;
    // La nau
    private int ample = 20, alt = 150;
    // Speed: horizontal and vertical
    private int xDirection = 10, yDirection = 30;
    int radi = 20;
    // L'asteroide
    Asteroide asteroide;
    // El bitmaps
    private Bitmap asteroideBitmap, fonsAsteroidesBitmap,
            nauEspacialBitmap, bolaFocBitmap, celFonsBitmap,
            asteroidExplosioBitmap;
    // El so del xoc
    MediaPlayer mp;
    // Nivell: per anar baixant
    private int altAsteroid = 40;
    // Paints i colors
    private int balaColor = Color.BLUE;
    private int nauColor = Color.RED;

    // Paints (sobren? Els posem a la classe)
    Paint nauPaint = new Paint();
    Paint balaPaint = new Paint();
    // Bala / bola
    int colorBola = Color.BLACK;
    protected boolean disparaBolaAleatoria;
    ArrayList<Bola> boles = new ArrayList<>();
    private float mLastTouchX, mLastTouchY;
    // Les bales i la nau
    Bala bala;
    ArrayList<Bala> bales = new ArrayList();
    Nau nau;
    Boolean explosio = false;
    // Rectangle fons
    Rect rectBackground;
    Rect rectAsteroide = new Rect();
    Rect rectNau = new Rect();

    // El constructor
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        initGame();
        if (gameThread != null) return;
        gameThread = new GameThread(getHolder());
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)         {
        stopThread();
    }

    public void stopThread() {
        if (gameThread != null) gameThread.stop = true;
    }

    public void initGame () {
        // Els bitmaps
        asteroide = new Asteroide(0, 0, ample, alt, xDirection, yDirection);
        asteroideBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid);
        fonsAsteroidesBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fons_asteroides);
        nauEspacialBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nau_espacial);
        bolaFocBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bola_foc_xicoteta);
        celFonsBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cel_fons);
        asteroidExplosioBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid_explosion);
        celFonsBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cel_fons);
        nauEspacialBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nau_espacial_unity);
        // Paints and colors
        balaPaint.setColor(balaColor);
        nauPaint.setColor(nauColor);
        // Situació inicial bala
        bala = new Bala(getWidth() / 2,  getHeight() - alt ,radius, balaColor);
        // Situació inicial nau
        nau = new Nau (getWidth() / 2,getHeight() - alt,  ample, alt , nauColor );
        // Mides del fons de la pantalla
        rectBackground = new Rect (0,0, getWidth(), getHeight());

        Log.d (TAG, "surfaceCreated: px = " + nau.getX() + "  py = " + nau.getY() );

    }

    public void newDraw(Canvas canvas) {
        // El tauler
        canvas.drawBitmap(celFonsBitmap, null, rectBackground, null);
        // L'asteroid
        if (explosio) { // Explosio asteroid
            canvas.drawBitmap(asteroidExplosioBitmap, asteroide.getX(), asteroide.getY(), null );
                    GameActivity.mpExplosion.start();
        }
        else { // Asteroid normal
            canvas.drawBitmap(asteroideBitmap, asteroide.getX(), asteroide.getY(), null );
        }
        // Les boles: Mostrem totes les boles de foc disparades
        for (int i = 0; i < boles.size(); i++) {
            canvas.drawBitmap(bolaFocBitmap, (float) boles.get(i).getX(), (float) boles.get(i).getY(), null);
        }
        // La nau
        canvas.drawBitmap(nauEspacialBitmap, (float) nau.getX(), (float) nau.getY(), null);
        // Les bales
        if (dispara) {
            // Mostrem totes les bales disparades
            for (int i = 0; i < bales.size(); i++) {
                Log.d (TAG, "Pinta bales i: " + i + " x=  " + bales.get(i).getX() + " y= " + bales.get(i).getY());
                canvas.drawCircle(bales.get(i).getX() , bales.get(i).getY(), bales.get(i).getRadius(), balaPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mLastTouchX = ev.getX();
                mLastTouchY = ev.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                // Calculate the distance moved
                final float x = ev.getX();  final float y = ev.getY();
                // Calculate the distance moved
                final float dx = x - mLastTouchX; final float dy = y - mLastTouchY;
                // Sols moviment horitzontal de la paleta 1
                // paletaX += dx;
                nau.setX(nau.getX() + dx);
                // Evitem que la paleta se'n isca pels estrems
                if (nau.getX() < -2*ample) nau.setX(-2*ample);
                if (nau.getX() + 5*nau.getAmple() > getWidth()) nau.setX( getWidth() - 5*nau.getAmple());
                // Remember this touch position for the next move event
                mLastTouchX = x;                 mLastTouchY = y;
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                // La pilota apareixerà dalt de la paleta
                Bala balaTemp = new Bala(nau.getX() + 3* nau.getAmple(), nau.getY() , radius, balaColor);
                Log.d (TAG, "Nova bala x= " + nau.getX() + nau.getAmple() / 2);
                bales.add(balaTemp);
                // I eixirà disparada
                dispara = true;
                break;
            }

        }
        return true;// Fi  onTouchEvent
    }

    public void resumeThread() {
        Log.d (TAG, "Parat?: " + gameThread.stop);
        if (gameThread.stop) gameThread.stop = false;
    }

    // The thread -----------------------------------------------------------
    private class GameThread extends Thread {

        public boolean stop = false;
        private SurfaceHolder surfaceHolder;
        private int xAleatori = 0;

        public GameThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void run() {
            while (!stop) {
                if (dispara) {
                    if (!bales.isEmpty()) {
                        Log.d(TAG, "!bales.isEmpty())");
                        for (int j = 0; j < bales.size(); j++) {
                            // Actual y value
                            float yAux = bales.get(j).getY();
                            yAux -= yDirection;
                            bales.get(j).setY(yAux);
                            // Arriba dalt
                            if (bales.get(j).getY() < 0) {
                                // Suprimim la bala de l'array
                                bales.remove(j);
                            }
                        }
                    }
                }

                // El moviment de l'asteroid: Sols es desplaça  en horitzontal
                asteroide.setX (asteroide.getX() + asteroide.getSpeedX());
                // Arriba baix de tot
                if (asteroide.getY() + 4* asteroide.getAlt() > getHeight()) asteroide.setY(0);
                // El rectangle de l'asteroid per al xoc
                rectAsteroide.set((int) asteroide.getX(), (int)asteroide.getY(),
                        (int) asteroide.getX() + (int) asteroide.getAmple() , (int) asteroide.getY() + (int) asteroide.getAlt() );
                // El rectangle de l'asteroid per al xoc: Duplliquem els marges per apuntar millor
                rectNau.set((int) nau.getX() - (int) nau.getAmple() , (int)nau.getY() - (int) nau.getY() ,
                        (int) nau.getX() +  (int) nau.getAmple()* 2 , (int) nau.getY() + (int) nau.getAlt()*2 );
                // El moviment de les boles disparades per l'asteroid
                if (!boles.isEmpty()) {
                    for (int j = 0; j < boles.size(); j++) {
                        // Actual y value
                        float yAux = boles.get(j).getY();
                        yAux += yDirection;
                        boles.get(j).setY(yAux);
                        // Arriba baix
                        if (boles.get(j).getY() > getHeight()) {
                            // Suprimim la bala de l'array
                            boles.remove(j);
                        }
                    }
                }
                // Dispara bola aleatòria
                if (disparaBolaAleatoria) {
                    if (xDirection < 0) { // Dreta -> esquerre
                        if (asteroide.getX() < xAleatori) { // Hem superat el punt de dispar
                            disparaBolaAleatoria = false;
                            Bola bolaTemp = new Bola(asteroide.getX() + asteroide.getAmple() / 2, asteroide.getY() + 3*asteroide.getAlt(), radi, colorBola);
                            boles.add(bolaTemp);
                        }
                    } else { // Direcció esquerre -> dreta
                        if (asteroide.getX() > xAleatori) { // Hem superat el punt aleatori per disparar
                            disparaBolaAleatoria = false;
                            Bola bolaTemp = new Bola(asteroide.getX() + asteroide.getAmple() / 2, asteroide.getY() + 3*asteroide.getAlt(), radi, colorBola);
                            boles.add(bolaTemp);
                        }
                    }
                }
                // Arriba a la dreta
                if (asteroide.getX() + asteroide.getAmple()> getWidth()) {
                    // Canvia el sentit
                    asteroide.setSpeedX(-asteroide.getSpeedX());
                    // Baixa un nivell asteroidY += nivell;
                    asteroide.setY (asteroide.getY() + alt);
                    // Generem un nombre aleatori corresponent a un valor de l'interval
                    // Volem una única bola en cada interval.
                    // Ací xDirection < 0
                    xAleatori = getAleatori (0, (int) getWidth());
                    disparaBolaAleatoria = true;
                    explosio = false;

                }
                // Arriba a l'esquerre
                if (asteroide.getX() < 0) {
                    // Canvia el sentit
                    asteroide.setSpeedX(-asteroide.getSpeedX());
                    // Baixa un nivell asteroidY += nivell;
                    asteroide.setY (asteroide.getY() + alt);
                    // Generem un nombre aleatori corresponent a un valor de l'interval
                    // Volem una única bola en cada interval
                    // Ací xDirection > 0
                    xAleatori = getAleatori (0, (int) getWidth());
                    disparaBolaAleatoria = true;
                    explosio = false;
                }
                // Xoc asteroide amb bala
                try {
                    for (int j = 0; j < bales.size(); j++) {
                        if ( (bales.get(j).getX() > asteroide.getX()) && (bales.get(j).getX() < asteroide.getX() + 2* asteroide.getAmple() )) {
                            if ( (bales.get(j).getY() > asteroide.getY()) && (bales.get(j).getY() < asteroide.getY()+ 2* asteroide.getAlt() )) {
                                explosio = true;
                                // Suprimim la bala de l'array
                                boles.remove(j);
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException e) {   }

                /*// Xoc bola de foc amb nau
                if (!boles.isEmpty()) {
                    try {
                        for (int j = 0; j < boles.size(); j++) {
                            if ( (boles.get(j).getX() > asteroide.getX()) && (boles.get(j).getX() < asteroide.getX() + 2* asteroide.getAmple() )) {
                                if ( (bales.get(j).getY() > asteroide.getY()) && (bales.get(j).getY() < asteroide.getY()+ 2* asteroide.getAlt() )) {
                                    explosio = true;
                                    // Suprimim la bala de l'array
                                    boles.remove(j);
                                }
                            }
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {   }
                }*/

                Canvas c = null;
                try {
                    c = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        newDraw(c);
                    }
                } finally {
                    if (c != null) surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

    public boolean xoc (Rect r, int x, int y) {
        if (r.contains(x, y)) {
            Log.d ("Contains" , "Center: " + r.centerX() + " x = " + x );
            return true;
        }
        else {
            return false;
        }
    }

    public int getAleatori (int x0, int y0) {
        // Random value int [x0, y0] interval
        Random r = new Random();
        return r.nextInt( y0 - x0) + x0;
    }

}
