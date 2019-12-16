package com.example.dreamdemo;

import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

public class BeijingAcitvity extends AppCompatActivity {
    private VideoView vv_pass1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_beijing);
        vv_pass1 = findViewById(R.id.vv_pass1);
        vv_pass1.setVideoURI(Uri.parse("android.resource://" +getPackageName()+"/"+R.raw.beijing1920_1));

        vv_pass1.start();
        vv_pass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BeijingAcitvity.this,FirstInterfaceActivity.class);
                startActivity(intent);
            }
        });
        vv_pass1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent();
                intent.setClass(BeijingAcitvity.this,FirstInterfaceActivity.class);
                startActivity(intent);
            }
        });
    }
}
