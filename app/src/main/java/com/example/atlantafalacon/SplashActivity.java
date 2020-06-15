package com.example.atlantafalacon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private TextView yazi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initializeViews();
        executeAnimations();

    }

    private void initializeViews() {
        yazi = findViewById(R.id.ac_splash_aciklama);
    }

    private void executeAnimations() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation);
        yazi.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        },3000);
    }

}
