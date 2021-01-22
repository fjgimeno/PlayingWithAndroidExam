package com.example.testinvaders;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends MainMenu {

    public static MediaPlayer mpBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("LifeCycle", "onCreate ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mpBackground = MediaPlayer.create(this, R.raw.misteri);
        mpBackground.start();
        Log.d("LifeCycle", "onStart2 ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifeCycle", "onPause ");
        mpBackground.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifeCycle", "onPause ");
        mpBackground.stop();
    }

}