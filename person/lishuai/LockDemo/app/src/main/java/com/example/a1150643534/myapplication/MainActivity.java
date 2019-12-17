package com.example.a1150643534.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private int level = 2;
    private int maxLevel = 4;
    private int minLevel = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button1.setClickable(false);
        button1.setBackgroundResource(R.drawable.lock);
        button2.setClickable(false);
        button2.setBackgroundResource(R.drawable.lock);
        button3.setClickable(false);
        button3.setBackgroundResource(R.drawable.lock);
        button4.setClickable(false);
        button4.setBackgroundResource(R.drawable.lock);
        if(level==1){
            button1.setClickable(true);
            button1.setBackgroundResource(R.drawable.empty);
        }else if(level==2){
            button1.setClickable(true);
            button1.setBackgroundResource(R.drawable.empty);
            button2.setClickable(true);
            button2.setBackgroundResource(R.drawable.empty);
        }else if(level==3){
            button1.setClickable(true);
            button1.setBackgroundResource(R.drawable.empty);
            button2.setClickable(true);
            button2.setBackgroundResource(R.drawable.empty);
            button3.setClickable(true);
            button3.setBackgroundResource(R.drawable.empty);
        }else if(level==4){
            button1.setClickable(true);
            button1.setBackgroundResource(R.drawable.empty);
            button2.setClickable(true);
            button2.setBackgroundResource(R.drawable.empty);
            button3.setClickable(true);
            button3.setBackgroundResource(R.drawable.empty);
            button4.setClickable(true);
            button4.setBackgroundResource(R.drawable.empty);

        }



    }
}
