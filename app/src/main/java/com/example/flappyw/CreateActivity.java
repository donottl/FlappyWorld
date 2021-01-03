package com.example.flappyw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;

public class CreateActivity extends AppCompatActivity {

    Bitmap Tube;
    ImageView TubeImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_main);
        Intent intent = getIntent();

        TubeImage = (ImageView) findViewById(R.id.TubeImage);

        if(getIntent() != null){
            try {
                Tube = BitmapFactory.decodeStream(this.openFileInput("Tube"));
                TubeImage.setImageBitmap(Tube);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error 9");
            }
        }

        Button clickButton = (Button) findViewById(R.id.TubeButton);
        if (clickButton != null) {
            clickButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startTubePicture();
                }
            });
        }

        Button clickButton2 = (Button) findViewById(R.id.BackgroundButton);
        if (clickButton2 != null) {
            clickButton2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startBackgroundPicture();
                }
            });
        }

        Button clickButton3 = (Button) findViewById(R.id.CharackterButton);
        if (clickButton3 != null) {
            clickButton3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startCharackterPicture();
                }
            });
        }
    }

    public void startTubePicture(){
        Intent tube = new Intent(this, TubePictureActivity.class);
        startActivity(tube);
        this.finish();
    }

    public void startBackgroundPicture(){}

    public void startCharackterPicture(){
        Intent tube = new Intent(this, CharackterPictureActivity.class);
        startActivity(tube);
        this.finish();
    }
}
