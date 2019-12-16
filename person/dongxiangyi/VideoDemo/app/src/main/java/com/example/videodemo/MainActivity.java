package com.example.videodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private VideoView vv_pass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_main);

        vv_pass1 = findViewById(R.id.vv_pass1);
        vv_pass1.setVideoURI(Uri.parse("android.resource://" +getPackageName()+"/"+R.raw.wuqi_1));
//        File videoPath = new File("E:/mv/beijing2.avi");
//        if(videoPath.exists()) {
//            vv_pass1.setVideoPath(videoPath.getAbsolutePath());
//            vv_pass1.start();
//        }
        vv_pass1.start();
        vv_pass1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,NewActivity.class);
                startActivity(intent);
            }
        });
    }
}
