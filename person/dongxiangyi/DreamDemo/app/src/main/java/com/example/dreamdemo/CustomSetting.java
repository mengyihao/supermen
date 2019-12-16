package com.example.dreamdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CustomSetting extends DialogFragment {
    private ImageView iv_close;
    private Button btn_close_game;
    private Button btn_hold_file;
    private Button btn_back;
    private Context context;
    private ImageView iv_service;
    private TextView tv_music;
    private TextView tv_yinxiao;

    private MediaPlayer mediaPlayer;
    //定义AssetManager引用对象
    private AssetManager assets;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_setting, container, false);

        iv_close = view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btn_close_game = view.findViewById(R.id.btn_close_game);
        btn_close_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
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
            }
        });

        btn_hold_file = view.findViewById(R.id.btn_hold_file);
        btn_hold_file.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent();
            @Override
            public void onClick(View v) {
                intent.setClass(getContext(), FileActivity.class);
//                mediaPlayer.pause();
//                btn_music.setBackgroundResource(R.mipmap.on);
                startActivity(intent);
            }
        });

        btn_back = view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent();
            @Override
            public void onClick(View v) {
                intent.setClass(getContext(),MainActivity.class);
//                mediaPlayer.pause();
//                btn_music.setBackgroundResource(R.mipmap.on);
                startActivity(intent);
            }
        });

        iv_service = view.findViewById(R.id.iv_service);
        iv_service.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent();
            @Override
            public void onClick(View v) {
                intent.setClass(getContext(),ServiceActivity.class);
                startActivity(intent);
            }
        });

        tv_music = view.findViewById(R.id.tv_music);
        //静音
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        tv_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.setVolume(0,0);
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(1800,1012);
    }
}
