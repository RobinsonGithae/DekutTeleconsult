package com.example.dekutteleconsult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashscreenActivity extends AppCompatActivity {

    private static final long SPLASH_TIMEOUT =3000 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashscreenActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMEOUT);



    }
}
