package com.example.dreamdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class CreaterolesActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ImageView iv_role_1;
    private ImageView iv_role_2;
    private EditText ed_role_id;
    private Button btn_start;
    private Button btn_music;
    private ImageView iv_settings;
    private ImageView imageView[] = new ImageView[2];
    int []drawables = {R.mipmap.rolemanjianshi,R.mipmap.rolemanjianshi};
    int []intIds = {R.id.iv_role_1,R.id.iv_role_2};

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
        setContentView(R.layout.avtivity_createroles);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        MyApplication.getApp().addActivity(this);
        getViews();
        registerListener();
        try {
            initMediaPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0;i<2;i++){
            imageView[i] = findViewById(intIds[i]);
            imageView[i].setImageResource(drawables[i]);
            imageView[i].setOnClickListener(this);
        }
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

    private void registerListener() {
        listener = new CustomeClickListener();
        iv_back.setOnClickListener(listener);
        iv_role_1.setOnClickListener(listener);
        iv_role_2.setOnClickListener(listener);
        btn_start.setOnClickListener(listener);
        iv_settings.setOnClickListener(listener);
        btn_music.setOnClickListener(listener);
    }

    private void getViews() {
        iv_back = findViewById(R.id.iv_back);
        iv_role_1 = findViewById(R.id.iv_role_1);
        iv_role_2 = findViewById(R.id.iv_role_2);
        ed_role_id = findViewById(R.id.ed_role_id);
        btn_start = findViewById(R.id.btn_start);
        iv_settings = findViewById(R.id.iv_settings);
        btn_music = findViewById(R.id.btn_music);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_role_1:
                changeImageView(0);
                break;
            case R.id.iv_role_2:
                changeImageView(1);
                break;
        }
    }

    private void changeImageView(int i){
        if(imageView[i].getDrawable() instanceof BitmapDrawable){
            Resources resources = this.getResources();
            Drawable[] layers = new Drawable[2];
            layers[0] = resources.getDrawable(drawables[i]);
            layers[1] = resources.getDrawable(R.mipmap.logo2);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            imageView[i].setImageDrawable(layerDrawable);
        }else if (imageView[i].getDrawable() instanceof LayerDrawable){
            Resources resources = this.getResources();
            Drawable drawable = resources.getDrawable(drawables[i]);
            imageView[i].setImageDrawable(drawable);
        }
    }

    class CustomeClickListener implements View.OnClickListener{
        Intent intent = new Intent();
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    break;
                case R.id.iv_back:
                    intent.setClass(CreaterolesActivity.this,FileActivity.class);
                    mediaPlayer.pause();
                    btn_music.setBackgroundResource(R.mipmap.on);
                    startActivity(intent);
                    break;
                case R.id.btn_start:
                    String msg = ed_role_id.getText().toString().trim();
                    if (TextUtils.isEmpty(msg)){
                        AlertDialog alertDialog = new AlertDialog.Builder(CreaterolesActivity.this)
                                .setMessage("请输入角色ID")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        return;
                                    }
                                }).create();
                        alertDialog.show();
                    }else{
                        EventBean stiEvent = new EventBean(msg);
                        EventBus.getDefault().postSticky(stiEvent);
                        intent.setClass(CreaterolesActivity.this, BeijingAcitvity.class);
                        mediaPlayer.pause();
                        btn_music.setBackgroundResource(R.mipmap.on);
                        startActivity(intent);
                    }
                    break;
                case R.id.iv_settings:
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
            }
        }
    }
}
