package com.example.web1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Game extends AppCompatActivity {

    ImageView star;
    ImageButton btnNext;
    ImageButton btnPrev;
    TextView score;

    int scoreInt;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        star = findViewById(R.id.star);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        score = findViewById(R.id.score);

        btnNext.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.animclock);
            star.startAnimation(animation);
            scoreInt++;
            score.setText(scoreInt + "");
        });
        btnPrev.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.animanticlock);
            star.startAnimation(animation);
            scoreInt--;
            score.setText(scoreInt + "");
        });

    }
}