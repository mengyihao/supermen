package com.game.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.game.mainInterface.MainActivity;

public class FirstThirdActivity extends AppCompatActivity {
    private RelativeLayout relativeLayout;
    private Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_third);
        MainActivity.activityList.add(this);
    }


}
