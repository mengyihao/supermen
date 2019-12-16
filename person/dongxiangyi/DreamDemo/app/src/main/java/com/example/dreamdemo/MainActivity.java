package com.example.dreamdemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_settings;
    private TextView tv_start;
    private TextView tv_ending;
    private Button btn_music;

    private CustomeClickListener listener;
    //定义MediaPlayer引用对象
    private MediaPlayer mediaPlayer;
    //定义AssetManager引用对象
    private AssetManager assets;
    //定义用于记录当前音频文件的整型变量
    private int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_main);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        MyApplication.getApp().addActivity(this);
        getViews();
        registerListeners();
        try {
            initMediaPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initMediaPlayer() throws IOException {
        mediaPlayer = new MediaPlayer();
        assets = getAssets();
        currentId =3;
        setAudioData(currentId);
        mediaPlayer.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        try {
                            //重新绑定音频数据源
                            mediaPlayer.reset();
//                            currentId++;
//                            if(currentId > 2){
//                                currentId = 1;
//                            }
                            setAudioData(currentId);
                            //开始播放
                            mediaPlayer.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

    }

    /**
     *  绑定指定的音频数据源
     * @param currentId 待播放的音频文件ID
     * @throws IOException
     */
    private void setAudioData(int currentId) throws IOException {
        //2. 得到Assets目录下的文件
        AssetFileDescriptor afd =
                assets.openFd("backgroundmusic"+currentId+".mp3");
        mediaPlayer.setDataSource(
                afd.getFileDescriptor(),
                afd.getStartOffset(),
                afd.getLength()
        );
        //准备音频文件
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    private void registerListeners() {
        listener = new CustomeClickListener();
        iv_settings.setOnClickListener(listener);
        tv_start.setOnClickListener(listener);
        tv_ending.setOnClickListener(listener);
        btn_music.setOnClickListener(listener);
    }

    private void getViews() {
        iv_settings = findViewById(R.id.iv_settings);
        tv_start = findViewById(R.id.tv_start);
        tv_ending = findViewById(R.id.tv_ending);
        btn_music = findViewById(R.id.btn_music);
    }

    class CustomeClickListener implements View.OnClickListener {
        Intent intent = new Intent();
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.iv_settings:
//                    intent.setClass(MainActivity.this,SettingsActivity.class);
//                    mediaPlayer.pause();
//                    btn_music.setBackgroundResource(R.mipmap.on);
//                    startActivity(intent);
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    //事务(原子性的操作)
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    CustomSetting customDialog = new CustomSetting();

                    //判断是否已经被添加过
                    if (!customDialog.isAdded()) {
                        //添加Fragment
                        transaction.add(customDialog, "dialog");
                    }
                    //显示Fragment
                    transaction.show(customDialog);
                    transaction.commit();
                    break;
                case R.id.tv_start:
                    intent.setClass(MainActivity.this,ChooseActivity.class);
                    mediaPlayer.pause();
                    btn_music.setBackgroundResource(R.mipmap.on);
                    startActivity(intent);
                    break;
                case R.id.tv_ending:
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("退出程序")
                            .setMessage("是否退出程序")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    MyApplication.getApp().clearActivity();
                                    MyApplication.getApp().exit();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            }).create();
                    alertDialog.show();
                    break;
                case R.id.btn_music:
                    if (!mediaPlayer.isPlaying()){
                        mediaPlayer.start();
                        btn_music.setBackgroundResource(R.mipmap.pause);
                    }else{
                        mediaPlayer.pause();
                        btn_music.setBackgroundResource(R.mipmap.on);
                    }
                    break;
            }
        }
    }
}
