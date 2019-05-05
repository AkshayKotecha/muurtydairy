package com.pardipguha.muurtydairy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SpalshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Intent in = new Intent(SpalshActivity.this, MainActivity.class);
                    startActivity(in);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }
}
