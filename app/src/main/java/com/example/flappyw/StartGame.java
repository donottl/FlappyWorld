package com.example.flappyw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class StartGame extends Activity {
    GameView gameView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        gameView = new GameView(this, null);
        setContentView(R.layout.game_view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backmain = new Intent(StartGame.this,MainActivity.class);
        startActivity(backmain);
        finish();
    }

    }

