package com.hieunghia.dmt.appnghenhac.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hieunghia.dmt.appnghenhac.R;

public class SplashScreenActivity extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    ImageView imageView;
    TextView txtApp,txtSologon;

    private static int SPLASH_SCREEN = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        anhxa();
        animation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

    }

    private void animation() {
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        imageView.setAnimation(topAnim);
        txtApp.setAnimation(bottomAnim);
        txtSologon.setAnimation(bottomAnim);
    }

    private void anhxa() {
        imageView = findViewById(R.id.imganim);
        txtApp = findViewById(R.id.txtappanim);
        txtSologon = findViewById(R.id.txtslogonanim);
    }

}