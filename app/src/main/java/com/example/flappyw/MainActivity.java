package com.example.flappyw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    Button Startbutton;
    ImageView TubeImage;
    Bitmap Tube;
    ImageView CharackterImage;
    Bitmap Charackter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.SCREEN_HEIGHT= displayMetrics.heightPixels;
        Constants.SCREEN_WIDTH= displayMetrics.widthPixels;

        Intent intent = getIntent();
        TubeImage = (ImageView) findViewById(R.id.TubeImage);
        CharackterImage = (ImageView) findViewById(R.id.CharackterImage);

        if(getIntent() != null){
            try {
                Tube = BitmapFactory.decodeStream(this.openFileInput("Tube"));
                TubeImage.setImageBitmap(Tube);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error Tube main");
            }
        }
        if(getIntent() != null){
            try {
                Charackter = BitmapFactory.decodeStream(this.openFileInput("Charackter"));
                CharackterImage.setImageBitmap(Charackter);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error Charackter main");
            }
        }

        Button clickButton = (Button) findViewById(R.id.CreateButton);
        if (clickButton != null) {
            clickButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startCreateActivity();
                }
            });
        }


/*        Startbutton = (Button)findViewById(R.id.buttonstart);
        Startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Spielstart = new Intent(MainActivity.this,GameView.class);
                startActivity(Spielstart);
                setContentView(R.layout.game_view);

            }
        });*/


    }

    public void startGame(View view) {
        Intent go = new Intent(MainActivity.this, com.example.flappyw.StartGame.class);
        go.putExtra("KEY", "Tube");
        go.putExtra("KEY2","Charackter");
        startActivity(go);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);

        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backmain = new Intent(MainActivity.this,MainActivity.class);
        startActivity(backmain);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);
        finish();
    }

    public void startCreateActivity(){
        Intent tube = new Intent(this, CreateActivity.class);
        startActivity(tube);
        this.finish();
    }
}
