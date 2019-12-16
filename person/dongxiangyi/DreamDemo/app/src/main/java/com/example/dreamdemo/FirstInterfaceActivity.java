package com.example.dreamdemo;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;

public class FirstInterfaceActivity extends AppCompatActivity {

    private Button btn_music;
    private ImageView iv_settings;

    private CustomeClickListener listener;
    //定义MediaPlayer引用对象
    private MediaPlayer mediaPlayer;
    //定义AssetManager引用对象
    private AssetManager assets;
    //定义用于记录当前音频文件的整型变量
    private int currentId;

//    private Controller controller;
//    private RelativeLayout relativeLayout;
//    private ImageView dreamer;
//    private ImageView monster;
//    private Button buttonLeftMove;
//    private Button buttonRightMove;
//    private Button buttonFight;
//    private Button buttonSprint;
//    private Button buttonJump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_first_interface);

        MyApplication.getApp().addActivity(this);
        getViews();
        registerListeners();
        try {
            initMediaPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        controller = new Controller();
//        controller.setRelativeLayout(relativeLayout);
//        controller.setDreamer(dreamer);
//        controller.setMonster(monster);
    }

    private void initMediaPlayer() throws IOException {
        mediaPlayer = new MediaPlayer();
        assets = getAssets();
        currentId = 3;
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
        btn_music.setOnClickListener(listener);
//        buttonLeftMove.setOnClickListener(listener);
//        buttonRightMove.setOnClickListener(listener);
//        buttonFight.setOnClickListener(listener);
//        buttonSprint.setOnClickListener(listener);
//        buttonJump.setOnClickListener(listener);
    }

    private void getViews() {
        iv_settings = findViewById(R.id.iv_settings);
        btn_music = findViewById(R.id.btn_music);
//        relativeLayout = findViewById(R.id.layout1);
//        dreamer = findViewById(R.id.dreamer);
//        monster = findViewById(R.id.monster);
//        buttonLeftMove = findViewById(R.id.btnLeftMove);
//        buttonRightMove = findViewById(R.id.btnRightMove);
//        buttonFight = findViewById(R.id.btnFight);
//        buttonSprint = findViewById(R.id.btnSprint);
//        buttonJump = findViewById(R.id.btnJump);
    }

    class CustomeClickListener implements View.OnClickListener{
        Intent intent = new Intent();
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_settings:
//                    intent.setClass(FirstInterfaceActivity.this, SettingsActivity.class);
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
                case R.id.btn_music:
                    if (!mediaPlayer.isPlaying()){
                        mediaPlayer.start();
                        btn_music.setBackgroundResource(R.mipmap.pause);
                    }else{
                        mediaPlayer.pause();
                        btn_music.setBackgroundResource(R.mipmap.on);
                    }
                    break;
//                case R.id.btnLeftMove:
//                    //主角向左移动
//                    //按下按键开始MoveLeftThread进程
//                    //松开结束进程
//                    buttonLeftMove.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            if(event.getAction() == MotionEvent.ACTION_UP){
//                                controller.stopLeftThread();
//
//                            }else if(event.getAction() == MotionEvent.ACTION_DOWN){
//                                controller.startLeftThread();
//                            }
//                            return false;
//                        }
//
//                    });
//                    break;
//                case R.id.btnRightMove:
//                    //主角向右移动
//                    //按下按键开始MoveRightThread进程
//                    //松开结束进程
//                    buttonRightMove.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            if(event.getAction() == MotionEvent.ACTION_UP){
//                                controller.stopRightThread();
//
//                            }else if(event.getAction() == MotionEvent.ACTION_DOWN){
//                                controller.startRightThread();
//                            }
//                            return false;
//                        }
//                    });
//                    break;
//                case R.id.btnFight:
//                    //主角发起攻击
//                    //按下按键开启攻击进程
//
//                    buttonFight.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            controller.setContext(getApplicationContext());
//
//                            controller.startAttack();
//                        }
//                    });
//                    break;
//                case R.id.btnJump:
//                    buttonJump.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            controller.startJump();
//                        }
//                    });
//                    break;
//                case R.id.btnSprint:
//                    //主角冲刺
//                    //点击按键开启冲刺进程
//                    buttonSprint.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            controller.startSprint();
//                        }
//                    });
//                    break;
            }
        }
    }
}
