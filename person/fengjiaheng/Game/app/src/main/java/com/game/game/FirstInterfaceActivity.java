package com.game.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

public class FirstInterfaceActivity extends AppCompatActivity{
    private RelativeLayout relativeLayout;
    private Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_interface);
        MainActivity.activityList.add(this);
        relativeLayout = findViewById(R.id.layout1);
        CustomOnClickListener();


        ImageView dreamerImage = findViewById(R.id.dreamer);
        ImageView monsterImage = findViewById(R.id.monster);
        ImageView dreamerHp1 = findViewById(R.id.dreamerHp1);
        ImageView dreamerHp2 = findViewById(R.id.dreamerHp2);
        ImageView dreamerHp3 = findViewById(R.id.dreamerHp3);
        ImageView next1 = findViewById(R.id.next1);


        controller = new Controller();
        controller.setContext(getApplicationContext());
        //三个血量图片
        controller.setDreamerHp1(dreamerHp1);
        controller.setDreamerHp2(dreamerHp2);
        controller.setDreamerHp3(dreamerHp3);
        //
        controller.setRelativeLayout(relativeLayout);
        //人物
        Dreamer dreamer = new Dreamer();
        dreamer.setDreamer(dreamerImage);
        controller.setDreamer(dreamer);
        //怪物
        Glide.with(this).asBitmap().load(R.drawable.monster).into(monsterImage);
        Monster monster = new Monster();
        monster.setMonster(monsterImage);
        monsterImage.setX(1200);
        controller.setMonster(monster);
        controller.startMonsterMoveThread();
        controller.startMonsterBulletThread();
        //下一关图片
        controller.setNext1(next1);

    }

    //主角行动控制函数
    public void CustomOnClickListener(){
        final Button buttonLeftMove = findViewById(R.id.btnLeftMove);
        final Button buttonRightMove = findViewById(R.id.btnRightMove);
        final Button buttonFight = findViewById(R.id.btnFight);
        final Button buttonSprint = findViewById(R.id.btnSprint);
        final Button buttonJump = findViewById(R.id.btnJump);
        final Button buttonMagic = findViewById(R.id.magic);

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
                                .error( R.drawable.stand)
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
                                .load(R.drawable.leftmove)
                                .error( R.drawable.stand)
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
                                .error( R.drawable.stand)
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
                                .error( R.drawable.stand)
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

        //魔法攻击
        //点击发动
        buttonMagic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置按键透明度(cd)
                buttonMagic.getBackground().setAlpha(100);
                controller.startMagicThread(buttonMagic);
            }
        });

    }
}
