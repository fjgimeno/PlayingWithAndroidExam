package com.example.testinvaders;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends MainMenu {

    // La vista
    public static GameView gameView;
    // El so
    public static MediaPlayer mpExplosion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Creem la vista
        gameView = new GameView(this);
        // Creem el so
        mpExplosion = MediaPlayer.create(this, R.raw.explosion);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SurfaceView", "onPause");
        // Per evitar l'error a l'eixir de l'aplicació
        if (gameView != null) gameView.stopThread();
    }

}