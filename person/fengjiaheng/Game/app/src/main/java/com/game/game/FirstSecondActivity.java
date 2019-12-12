package com.game.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.game.mainInterface.MainActivity;

public class FirstSecondActivity extends AppCompatActivity {
    private RelativeLayout relativeLayout;
    private Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_second);
        MainActivity.activityList.add(this);

    }


}
