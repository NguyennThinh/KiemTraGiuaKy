package com.example.a42_18083051_nguyenphucthinh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private CircleImageView imgSong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgSong = findViewById(R.id.imgSong);
    }
    private void imgRotate() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.img_xoay);
        animation.setInterpolator(new LinearInterpolator());
        imgSong.startAnimation(animation);
    }
}