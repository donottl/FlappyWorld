package com.example.flappyw;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class GameOver extends Activity {
    int score;
    int highscore=0;
    private SharedPreferences myPreferences;
    private String sharedFile = "com.example.flappyw";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        myPreferences = getSharedPreferences(sharedFile, MODE_PRIVATE);
        highscore = myPreferences.getInt("highscore",0);
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        TextView scoreview = (TextView) findViewById(R.id.score);
        TextView highscoreview = (TextView) findViewById(R.id.highscore);
        if(score >= highscore) {
        highscore = score;
        }
        scoreview.setText("Score: "+ Integer.toString(score));
        highscoreview.setText("Highscore:" + Integer.toString(highscore));


    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor prefEdit = myPreferences.edit();
        prefEdit.putInt("highscore", highscore);
        prefEdit.apply();
    }

    public void restartGame(View view) {
        Intent go = new Intent(GameOver.this, com.example.flappyw.StartGame.class);
        startActivity(go);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);
        finish();
    }
    public void backtomain (View view){
        Intent backmain = new Intent(GameOver.this,MainActivity.class);
        startActivity(backmain);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backmain = new Intent(GameOver.this,MainActivity.class);
        startActivity(backmain);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);
        finish();
    }
}
