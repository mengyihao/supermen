package com.example.dreamdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Controller {
    private boolean directionLeft = false; //主角方向
    private boolean directionRight = true;
    //主角面朝那边  向左为false 向右为true
    private boolean dreamerDirection = true;
    //子弹发射
    private boolean attack = true;
    //攻击力
    private int strength=1;
    //跳跃
    public boolean jump = false;
    //主角当前位置
    private float dreamerTranslationX;
    private float dreamerTranslationY;
    //子弹生成位置
    private float bulletTranslationX;
    private float bulletTranslationY;
    //子弹销毁位置
    private float bulletMovedTranslationX;
    private float bulletMovedTranslationY;
    //主角移动后的位置
    private float dreamerMovedX;
    private float dreamerMovedY;
    //怪物与子弹是否碰撞
    private boolean monsterCollide = false;
    //怪物血量
    private int monsterHp=5;


    private ImageView dreamer;
    private ImageView monster;
    private JumpThread jumpThread;

    //游戏主界面Context
    private Context mainContext;
    //游戏元素所在Layout
    private RelativeLayout mainRelativeLayout;

    //获取怪物
    public void setMonster(ImageView v){
        this.monster = v;
    }
    public ImageView getMonster(){
        return this.monster;
    }

    //获取主角的图片
    public void setDreamer(ImageView v){
        this.dreamer = v;
    }

    public ImageView getDreamer() {
        return dreamer;
    }

    public void setContext(Context context){
        this.mainContext = context;
    }
    public void setRelativeLayout(RelativeLayout relativeLayout){
        this.mainRelativeLayout = relativeLayout;
    }



    //碰撞检测 碰到返回true
    public boolean collide(View a, View b){
        float directionAX = a.getX();
        float directionAY = a.getY();
        float directionBX = b.getX();
        float directionBY = b.getY();
        float lengthX,lengthY;
        if(directionAX>=directionBX)lengthX = directionAX-directionBX;
        else
            lengthX = directionBX-directionAX;
        if(directionAY>=directionBY)lengthY = directionAY-directionBY;
        else
            lengthY = directionBY-directionAY;
        if(lengthX<=a.getWidth()&&lengthY<=a.getHeight())
            return true;
        else
            return false;
    }

    //主角跳跃
    public void startJump(){
        if(jumpThread == null){
            jumpThread = new JumpThread();
            jump = true;
            jumpThread.start();
        }
        else{
            if(jumpThread.isAlive()){
                jump = false;
            }
            else{
                jumpThread = new JumpThread();
                jump = true;
                jumpThread.start();
            }
        }


    }



    //生成子弹
    public Bullet createBullet(){
        Bullet bullet = new Bullet(mainContext);
        bullet.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
        bullet.setTranslationX(bulletTranslationX);
        bullet.setTranslationY(bulletTranslationY);


        return bullet;
    }

    //销毁子弹
    public void destroyBullet(Bullet bullet){
        mainRelativeLayout.removeView(bullet);
    }

    //销毁怪物
    public void destroyMonster(ImageView v){
        mainRelativeLayout.removeView(v);
    }

    //开启冲刺操作
    public void startSprint(){
        dreamerTranslationX = dreamer.getTranslationX();
        if(dreamerDirection){
            dreamerMovedX = dreamerTranslationX+200;

        }
        else {
            dreamerMovedX = dreamerTranslationX - 200;
        }
        final ValueAnimator animatorSprintLeft = ValueAnimator.ofFloat(dreamerTranslationX,dreamerMovedX);
        animatorSprintLeft.setDuration(400);
        animatorSprintLeft.setInterpolator(new LinearInterpolator());
        animatorSprintLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if(dreamer.getX()>=mainRelativeLayout.getWidth()-dreamer.getWidth()){ }
                else if(dreamer.getX()<=0){
                    if(dreamerDirection){
                        float current = (float) animatorSprintLeft.getAnimatedValue();
                        dreamer.setTranslationX(current);
                    }
                }
                else{
                    float current = (float) animatorSprintLeft.getAnimatedValue();
                    dreamer.setTranslationX(current);
                }
            }
        });
        animatorSprintLeft.start();
    }



    //主角向右移动线程
    class MoveRightThread extends Thread{
        @Override
        public void run() {
            while (directionRight) {
                try {
                    moveHandler.sendEmptyMessage(1);
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }
    }
    //主角向左移动线程
    class MoveLeftThread extends Thread{
        @Override
        public void run() {
            while (directionLeft) {
                try {
                    moveHandler.sendEmptyMessage(2);
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }
    }
    //发射子弹线程
    class BulletThread extends Thread{
        @Override
        public void run() {
            if (attack) {
                try {

                    moveHandler.sendEmptyMessage(3);
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }
    }
    //主角跳跃线程
    class JumpThread extends Thread{
        @Override
        public void run() {
            if (jump) {
                try {

                    moveHandler.sendEmptyMessage(4);
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }
    }

    //开启攻击线程
    public void startAttack(){
        BulletThread bulletThread = new BulletThread();
        bulletThread.start();
    }

    //开启向左移动的线程
    public void startLeftThread(){
        MoveLeftThread moveLeftThread = new MoveLeftThread();
        directionRight = false;
        directionLeft = true;
        dreamerDirection = false;
        moveLeftThread.start();
    }
    //停止向左移动的线程
    public void stopLeftThread(){
        directionLeft = false;
    }

    //开始向右移动的线程
    public void startRightThread(){
        MoveRightThread moveRightThread = new MoveRightThread();
        directionLeft = false;
        directionRight = true;
        dreamerDirection = true;
        moveRightThread.start();
    }
    //停止向右移动的线程
    public void stopRightThread(){
        directionRight = false;
    }




    //管理线程
    Handler moveHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://向右移动
                    dreamerTranslationX = dreamer.getTranslationX();

                    dreamerMovedX = dreamerTranslationX+30;
                    final ValueAnimator animatorRight = ValueAnimator.ofFloat(dreamerTranslationX,dreamerMovedX);
                    animatorRight.setDuration(400);
                    animatorRight.setInterpolator(new LinearInterpolator());
                    animatorRight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            if(dreamer.getX()>=mainRelativeLayout.getWidth()-dreamer.getWidth()){}
                            else {
                                float current = (float) animatorRight.getAnimatedValue();
                                dreamer.setTranslationX(current);
                            }
                        }
                    });
                    animatorRight.start();
                    break;
                case 2://向左移动
                    dreamerTranslationX = dreamer.getTranslationX();
                    dreamerMovedX = dreamerTranslationX-30;
                    final ValueAnimator animatorLeft = ValueAnimator.ofFloat(dreamerTranslationX,dreamerMovedX);
                    animatorLeft.setDuration(400);
                    animatorLeft.setInterpolator(new LinearInterpolator());
                    animatorLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            if(dreamer.getX()<=0){}
                            else {
                                float current = (float) animatorLeft.getAnimatedValue();
                                dreamer.setTranslationX(current);

                            }
                        }
                    });
                    animatorLeft.start();
                    break;
                case 3://发射子弹
                    bulletTranslationX = dreamer.getTranslationX();
                    bulletTranslationY = dreamer.getTranslationY();
                    final Bullet bullet = createBullet();
                    mainRelativeLayout.addView(bullet);
                    if(dreamerDirection){
                        bulletMovedTranslationX = bulletTranslationX+2500;
                    }
                    else {
                        bulletMovedTranslationX = bulletTranslationX-2500;
                    }
                    System.out.println(bulletTranslationX+"  "+bulletMovedTranslationX);
                    final ValueAnimator animator1 = ValueAnimator.ofFloat(bulletTranslationX,bulletMovedTranslationX);
                    animator1.setDuration(1200);
                    animator1.setInterpolator(new LinearInterpolator());
                    animator1.start();
                    animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float current = (float) animator1.getAnimatedValue();
                            bullet.setTranslationX(current);
                            if(monsterHp>0) {
                                if (collide(bullet, monster)) {
                                    destroyBullet(bullet);
                                    monsterHp--;
                                    if(monsterHp<=0)
                                        destroyMonster(monster);
                                    animator1.cancel();

                                } else if (current >= bulletMovedTranslationX && dreamerDirection) {
                                    destroyBullet(bullet);
                                } else if (current <= bulletMovedTranslationX && !dreamerDirection) {
                                    destroyBullet(bullet);
                                }

                            }



                        }

                    });

                    break;
                case 4:
                    dreamerTranslationY = dreamer.getTranslationY();
                    dreamerMovedY = dreamerTranslationY-200;
                    final ValueAnimator animatorJump = ValueAnimator.ofFloat(dreamerTranslationY,dreamerMovedY,dreamerTranslationY);
                    animatorJump.setDuration(400);
                    animatorJump.setInterpolator(new LinearInterpolator());
                    animatorJump.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float current = (float) animatorJump.getAnimatedValue();
                            System.out.println(dreamerTranslationY);
                            dreamer.setTranslationY(current);
                        }
                    });
                    animatorJump.start();
                    break;
            }
        }
    };
}
