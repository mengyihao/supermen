package com.game.game;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Activity> activityList = new LinkedList();
    private Controller controller;
    private RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        relativeLayout = findViewById(R.id.layout1);
        CustomOnClickListener();

    }

    //主角行动控制函数
    public void CustomOnClickListener(){
        final Button buttonStart = findViewById(R.id.btn_start);
        final Button buttonExit = findViewById(R.id.btn_exit);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,FirstInterfaceActivity.class);
                startActivity(intent);
            }
        });
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });



    }
    public void exit(){
        for(Activity act:activityList){
            act.finish();
        }
        System.exit(0);
    }


}
