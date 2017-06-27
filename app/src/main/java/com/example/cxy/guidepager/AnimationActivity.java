package com.example.cxy.guidepager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.cxy.guidepager.newguide.SecondActivity;

import java.util.Timer;
import java.util.TimerTask;

public class AnimationActivity extends AppCompatActivity {
    private ImageView alphaImag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        alphaImag = (ImageView) findViewById(R.id.alphaImag);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.drawroll_ani_in);
        alphaImag.startAnimation(animation);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                MainActivity.SwitchToMain(AnimationActivity.this);
                startActivity(new Intent(AnimationActivity.this, SecondActivity.class));
                finish();
            }
        }, 2000);

    }
}
