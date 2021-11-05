package com.example.a42_18083051_nguyenphucthinh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private CircleImageView imgSong;
    private Boolean isPlaying;
    private ImageView imgPlay;
    private MusicService mMusicService;
    private SeekBar seekBar;
    private Handler handler;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MyBinder binder = (MusicService.MyBinder) iBinder;
            mMusicService = binder.getService();
            mMusicService.StartMusic();
            update();
            isPlaying = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isPlaying = true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgSong = findViewById(R.id.imgSong);
        imgPlay = findViewById(R.id.imgPlay);
        handler = new Handler();
        seekBar = findViewById(R.id.seekBar);
        imgRotate();
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MusicService.mediaPlayer.isPlaying()){
                    MusicService.mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.ic_play);
                }else{
                    MusicService.mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.ic_pause);
                }
            }
        });
    }
    private void imgRotate() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.img_xoay);
        animation.setInterpolator(new LinearInterpolator());
        imgSong.startAnimation(animation);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isPlaying) {
            unbindService(connection);
            isPlaying = false;
        }
    }
    private void update() {
        seekBar.setMax(MusicService.mediaPlayer.getDuration());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(MusicService.mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 1000);
            }
        }, 100);
    }
}