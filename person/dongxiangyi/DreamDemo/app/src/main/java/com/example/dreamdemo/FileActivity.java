package com.example.dreamdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

public class FileActivity extends AppCompatActivity {

    private ImageView iv_settings;
    private Button btn_file_1;
    private Button btn_file_2;
    private Button btn_file_3;
    private Button btn_file_4;
    private Button btn_file_5;
    private ImageView iv_role;
    private Button btn_back;
    private Button btn_start;
    private Button btn_music;
    private EventBus eventBus;


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
        setContentView(R.layout.activity_file);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        MyApplication.getApp().addActivity(this);
        getViews();
        registListener();
        try {
            initMediaPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        eventBus = eventBus.getDefault();
        eventBus.register(FileActivity.this);

//        if (!TextUtils.isEmpty(btn_file_1.getText().toString().trim())){
//            btn_file_1.setCompoundDrawables(null,null,null,null);
//        }

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

    private void registListener() {
        listener = new CustomeClickListener();
        iv_settings.setOnClickListener(listener);
        btn_start.setOnClickListener(listener);
        btn_back.setOnClickListener(listener);
        btn_file_1.setOnClickListener(listener);
        btn_music.setOnClickListener(listener);
    }

    private void getViews() {
        iv_settings = findViewById(R.id.iv_settings);
        btn_file_1 = findViewById(R.id.btn_file_1);
        btn_file_2 = findViewById(R.id.btn_file_2);
        btn_file_3 = findViewById(R.id.btn_file_3);
        btn_file_4 = findViewById(R.id.btn_file_4);
        btn_file_5 = findViewById(R.id.btn_file_5);
        iv_role = findViewById(R.id.iv_role);
        btn_start = findViewById(R.id.btn_start);
        btn_back = findViewById(R.id.btn_back);
        btn_music = findViewById(R.id.btn_music);
    }

    class CustomeClickListener implements View.OnClickListener{
        Intent intent = new Intent();
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_start:
                    String strs = btn_file_1.getText().toString().trim();
                    if (TextUtils.isEmpty(strs)){
                        AlertDialog alertDialog = new AlertDialog.Builder(FileActivity.this)
                                .setTitle("请先创建角色")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        return;
                                    }
                                }).create();
                        alertDialog.show();
                    }else{
                        intent.setClass(FileActivity.this, FirstInterfaceActivity.class);
                        mediaPlayer.pause();
                        btn_music.setBackgroundResource(R.mipmap.on);
                        startActivity(intent);
                    }
                    break;
                case R.id.btn_back:
                    AlertDialog alertDialog = new AlertDialog.Builder(FileActivity.this)
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
                case R.id.iv_settings:
//                    intent.setClass(FileActivity.this,SettingsActivity.class);
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
                case R.id.btn_file_1:
                    String str = btn_file_1.getText().toString().trim();
                    if (TextUtils.isEmpty(str)){
                        intent.setClass(FileActivity.this,CreaterolesActivity.class);
                        mediaPlayer.pause();
                        btn_music.setBackgroundResource(R.mipmap.on);
                        startActivity(intent);
                    }else {
                        
                    }
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

    /**
     * 订阅事件(EventBean)
     * @param eventBean 发布的事件对象
     */
    @Subscribe(threadMode = ThreadMode.ASYNC, sticky = true)
    public void onEventBeanStikyEvent(EventBean eventBean){
        btn_file_1.setText(eventBean.getMsg());
        btn_file_1.setCompoundDrawables(null,null,null,null);
    }
}
