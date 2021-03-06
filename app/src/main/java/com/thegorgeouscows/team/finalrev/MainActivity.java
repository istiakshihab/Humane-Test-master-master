package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIMEOUT= 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(MainActivity.this, loginActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIMEOUT);
    }
}
