package com.example.audioservicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button btn_music;
    private Button btn_up;
    private Button btn_down;
//    private Button btn_close;
    private Button btn_mplayer_close;
    private Button btn_open;

    private MediaPlayer mediaPlayer;
    //定义AssetManager引用对象
    private AssetManager assets;
    //定义用于记录当前音频文件的整型变量
    private int currentId;

    private AudioManager audioManager;
    private SeekBar soundvalue;
    private AudioTrack at;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_music = findViewById(R.id.btn_music);
        btn_up = findViewById(R.id.btn_big);
        btn_down = findViewById(R.id.btn_small);
//        btn_close = findViewById(R.id.btn_close);
        btn_mplayer_close = findViewById(R.id.btn_mplay_close);
        btn_open = findViewById(R.id.btn_open);

        try {
            initMediaPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        btn_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }else{
                    mediaPlayer.pause();
                }
            }
        });

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        final boolean flag = audioManager.isStreamMute(AudioManager.STREAM_MUSIC);

        //声音放大
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
            }
        });

        //声音缩小
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            }
        });
//        btn_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (flag){
//                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);//设为静音
//                }else{
//                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE , 1);//取消静音
//                }
//            }
//        });


        //静音
        btn_mplayer_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.setVolume(0,0);
            }
        });


        //恢复声音
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.setVolume(1,1);
            }
        });
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
}
