package com.example.a1150643534.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private int level;
    private int maxLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button1.setClickable(false);
        button2.setClickable(false);
        button3.setClickable(false);
        button4.setClickable(false);
        level = 1;
        maxLevel = 4;
        if(level==1){
            button1.setClickable(true);
        }else if(level==2){
            button1.setClickable(true);
            button2.setClickable(true);
        }else if(level==3){
            button1.setClickable(true);
            button2.setClickable(true);
            button3.setClickable(true);
        }else if(level==4){
            button1.setClickable(true);
            button2.setClickable(true);
            button3.setClickable(true);
            button4.setClickable(true);

        }



    }
}
