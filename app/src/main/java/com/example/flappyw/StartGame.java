package com.example.flappyw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.io.FileNotFoundException;

public class StartGame extends Activity {
    GameView gameView;
    static public Bitmap Charackter;
    static public Bitmap Tube;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if(getIntent() != null){
            try {
                Tube = BitmapFactory.decodeStream(this.openFileInput("Tube"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error Tube main");
            }
        }
        if(getIntent() != null){
            try {
                Charackter = BitmapFactory.decodeStream(this.openFileInput("Charackter"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error Charackter main");
            }
        }

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

    public static Bitmap returnTube(){
        return Tube;
    }

    public static Bitmap returnCharackter(){
        return Charackter;
    }

    }

