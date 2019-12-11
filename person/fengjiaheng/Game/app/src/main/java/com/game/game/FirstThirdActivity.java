package com.game.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

public class FirstThirdActivity extends AppCompatActivity {
    private RelativeLayout relativeLayout;
    private Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_third);
        MainActivity.activityList.add(this);
        relativeLayout = findViewById(R.id.layout1);
        CustomOnClickListener();

        ImageView dreamer = findViewById(R.id.dreamer);
        ImageView monster = findViewById(R.id.monster);
        ImageView dreamerHp1 = findViewById(R.id.dreamerHp1);
        ImageView dreamerHp2 = findViewById(R.id.dreamerHp2);
        ImageView dreamerHp3 = findViewById(R.id.dreamerHp3);


        controller = new Controller();
        controller.setContext(getApplicationContext());
        //三个血量图片
        controller.setDreamerHp1(dreamerHp1);
        controller.setDreamerHp2(dreamerHp2);
        controller.setDreamerHp3(dreamerHp3);
        //
        controller.setRelativeLayout(relativeLayout);
        //人物

        //怪物
        Glide.with(this).asGif().load(R.drawable.rightmove).into(monster);

        controller.startMonsterMoveThread();
        controller.startMonsterBulletThread();

    }

    //主角行动控制函数
    public void CustomOnClickListener(){
        final Button buttonLeftMove = findViewById(R.id.btnLeftMove);
        final Button buttonRightMove = findViewById(R.id.btnRightMove);
        final Button buttonFight = findViewById(R.id.btnFight);
        final Button buttonSprint = findViewById(R.id.btnSprint);
        final Button buttonJump = findViewById(R.id.btnJump);


        //主角向左移动
        //按下按键开始MoveLeftThread进程
        //松开结束进程
        buttonLeftMove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //设置按键透明度
                    buttonLeftMove.getBackground().setAlpha(255);
                    //动图
                    ImageView img1 = findViewById(R.id.dreamer);
                    if(img1!=null){
                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(R.drawable.stand)
                                .error( R.drawable.dongtu)
                                .into(img1);
                    }
                    //关闭移动线程
                    controller.stopLeftThread();
                }else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //设置按键透明度
                    buttonLeftMove.getBackground().setAlpha(100);
                    //动图
                    ImageView img1 = findViewById(R.id.dreamer);
                    if(img1!=null){
                        Glide.with(getApplicationContext())
                                .asGif()
                                .load(R.drawable.rightmove)
                                .error( R.drawable.dongtu)
                                .into(img1);
                    }
                    //启动移动线程
                    controller.startLeftThread();
                }
                return false;
            }

        });

        //主角向右移动
        //按下按键开始MoveRightThread进程
        //松开结束进程
        buttonRightMove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                    //设置按键透明度
                    buttonRightMove.getBackground().setAlpha(255);
                    //动图
                    ImageView img1 = findViewById(R.id.dreamer);
                    if(img1!=null){

                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(R.drawable.stand)
                                .error( R.drawable.dongtu)
                                .into(img1);
                    }
                    //关闭移动线程
                    controller.stopRightThread();
                }else if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //设置按键透明度
                    buttonRightMove.getBackground().setAlpha(100);
                    //动图
                    ImageView img1 = findViewById(R.id.dreamer);
                    if(img1!=null){
                        Glide.with(getApplicationContext())
                                .asGif()
                                .load(R.drawable.rightmove)
                                .error( R.drawable.dongtu)
                                .into(img1);
                    }
                    //启动移动线程
                    controller.startRightThread();
                }
                return false;
            }
        });

        //主角发起攻击
        //按下按键开启攻击进程

        buttonFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.startAttack();

            }
        });

        buttonJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.startJump();
            }
        });

        //主角冲刺
        //点击按键开启冲刺进程
        buttonSprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.startSprint();
            }
        });



    }
}
