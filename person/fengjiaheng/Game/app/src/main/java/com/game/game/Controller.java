package com.game.game;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.security.acl.Group;

import static java.lang.Thread.sleep;

public class Controller{

    private Dreamer dreamer;
    private ImageView dreamerHp1;
    private ImageView dreamerHp2;
    private ImageView dreamerHp3;
    private ImageView dreamerImg;
    private Button buttonMagic;
    private ImageView next1;
    private boolean isWin = false;
    private Monster monster;

    public void setNext1(ImageView next1) {
        this.next1 = next1;
    }

    public void setDreamer(Dreamer dreamer) {
        this.dreamer = dreamer;
    }

    public void setDreamerHp1(ImageView dreamerHp1) {
        this.dreamerHp1 = dreamerHp1;
    }

    public void setDreamerHp2(ImageView dreamerHp2) {
        this.dreamerHp2 = dreamerHp2;
    }

    public void setDreamerHp3(ImageView dreamerHp3) {
        this.dreamerHp3 = dreamerHp3;
    }
    /*子弹属性
    * 子弹生成位置
    * 子弹销毁位置
    * */

    //子弹生成位置
    //人物子弹
    private float bulletX;
    private float bulletY;
    //子弹销毁位置
    private float bulletMovedX;
    private float bulletMovedY;
    //怪物子弹
    private float bulletmstTx;
    private float bulletmstTy;
    //怪物子弹销毁位置
    private float bulletMovedmstTx;
    private float bulletMovedmstTy;

    public void setMonster(Monster monster){
        this.monster = monster;

    }


    /*控制器类
    *
    * */
    private JumpThread jumpThread;
    private BulletThread bulletThread;
    private MonsterBulletThread monsterBulletThread;
    private Thread magicThread;
    //游戏主界面Context
    private Context mainContext;
    //游戏元素所在Layout
    private RelativeLayout mainRelativeLayout;

    public void setContext(Context context){
        this.mainContext = context;
    }
    public void setRelativeLayout(RelativeLayout relativeLayout){
        this.mainRelativeLayout = relativeLayout;
    }

    //碰撞检测 碰到返回true
    public boolean collide(View a,View b){
        float directionAX = a.getX();
        float directionAY = a.getY();
        float directionBX = b.getX();
        float directionBY = b.getY();
        //a在b的右侧碰撞是否
         boolean aa = false;
        //左侧
         boolean bb = false;
        float lengthX,lengthY;
        if(directionAX>=directionBX){
            lengthX = directionAX+a.getWidth()-directionBX-b.getWidth();
        }else{
            lengthX = directionBX-directionAX;
        }
        if(directionAY>=directionBY){
            lengthY = directionAY-directionBY;
        }else{
            lengthY = directionBY-directionAY;
        }
        if(lengthX<=a.getWidth()&&lengthY<=a.getHeight())

            return true;
        else
            return false;
    }

    //生成子弹
    public ImageView createBullet(){

        ImageView bullet = new ImageView(mainContext);
        Glide.with(mainContext).load(R.drawable.dreamerbullet).into(bullet);
        bullet.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
        bullet.setX(bulletX);
        bullet.setY(bulletY+100);
        if(!dreamer.isDreamerDirection())bullet.setRotation(180);

        return bullet;
    }

    //生成怪物子弹
    public ImageView createMonsterBullet(){
        ImageView bullet = new ImageView(mainContext);
        Glide.with(mainContext).load(R.drawable.fire).into(bullet);
        bullet.setLayoutParams(new ViewGroup.LayoutParams(100, 100));
        bullet.setX(monster.getMonster().getX());
        bullet.setY(monster.getMonster().getY()+100);
        if(!monster.isMonsterDirection())bullet.setRotation(180);

        return bullet;
    }
    //生成魔法View
    public ImageView createDreamerMagic(){
        int rotation;//旋转方向
        float x;//位置
        if(dreamer.isDreamerDirection()){
            rotation = -90;
            x = dreamer.getDreamer().getX()+dreamer.getDreamer().getWidth();
        }else{
            rotation = 90;
            x = dreamer.getDreamer().getX()-200;
        }
        ImageView mofa = new ImageView(mainContext);
        mofa.setLayoutParams(new ViewGroup.LayoutParams(200,400));
        mofa.setRotation(rotation);
        mofa.setX(x);
        mofa.setY(dreamer.getDreamer().getY()-dreamer.getDreamer().getHeight()+200);
        return mofa;
    }


    //销毁子弹
    public void destroyBullet(ImageView bullet){
        mainRelativeLayout.removeView(bullet);
    }

    //销毁怪物
    public void destroyMonster(ImageView v){
        mainRelativeLayout.removeView(v);
    }

    /*
    * 操作的实现
    */

    //主角向右移动线程
    class MoveRightThread extends Thread{
        @Override
        public void run() {
            while (dreamer.isDirectionRight()) {
                try {
                    moveHandler.sendEmptyMessage(1);
                    sleep(400);
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
            while (dreamer.isDirectionLeft()) {
                try {
                    moveHandler.sendEmptyMessage(2);
                    sleep(400);
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
            if (dreamer.isAttack()) {
                try {
                    moveHandler.sendEmptyMessage(3);
                    sleep(600);
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
            if (dreamer.isJump()) {
                try {
                    moveHandler.sendEmptyMessage(4);
                    sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }
    }
    //怪物移动线程
    class MonsterMoveThread extends Thread{
        @Override
        public void run() {
            try {
                while(monster.getMonsterHp()>0){
                    moveHandler.sendEmptyMessage(5);
                    sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.run();
        }
    }
    //怪物子弹线程
    class MonsterBulletThread extends  Thread{
        @Override
        public void run() {
            while (monster.getMonsterHp()>0){
                try {
                    if (monster.isMonsterAttack()) {
                        moveHandler.sendEmptyMessage(6);
                        sleep(5000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            super.run();
        }
    }
    //主角魔法攻击
    public class MagicThread implements Runnable{
        private Button buttonMagic;
        public MagicThread(Button buttonMagic){
            this.buttonMagic=buttonMagic;
        }
        @Override
        public void run(){
            try {
                moveHandler.sendEmptyMessage(7);
                sleep(3000);
                moveHandler.sendEmptyMessage(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 操作的启动与停止
     **/

    //开启冲刺操作
    public void startSprint(){
        float dreamerMovedX;
        float dreamerX = dreamer.getDreamer().getX();
        if(dreamer.isDreamerDirection()){
            dreamerMovedX = dreamerX+200;
        }
        else {
            dreamerMovedX = dreamerX-200;
        }
        final ValueAnimator animatorSprintLeft = ValueAnimator.ofFloat(dreamerX,dreamerMovedX);
        animatorSprintLeft.setDuration(400);
        animatorSprintLeft.setInterpolator(new LinearInterpolator());
        animatorSprintLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if(dreamer.getDreamer().getX()>=mainRelativeLayout.getWidth()-dreamer.getDreamer().getWidth()){ }
                else if(dreamer.getDreamer().getX()<=0){}
                else{
                    if(isWin){
                        float current = (float) animatorSprintLeft.getAnimatedValue();
                        dreamer.getDreamer().setX(current);
                        if(collide(dreamer.getDreamer(),next1)){
                            //开启下一关
                            Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setClass(mainContext,FirstSecondActivity.class);
                            mainContext.startActivity(intent);
                        }
                    }else{
                        float current = (float) animatorSprintLeft.getAnimatedValue();
                        dreamer.getDreamer().setX(current);
                    }
                }
            }
        });
        animatorSprintLeft.start();
    }

    //开启攻击线程
    public void startAttack(){
        if(bulletThread == null){
            dreamer.setAttack(true);
            bulletThread = new BulletThread();
            bulletThread.start();
        }
        else{
            if(bulletThread.isAlive()){
                dreamer.setAttack(false);
            }
            else{
                dreamer.setAttack(true);
                bulletThread = new BulletThread();
                bulletThread.start();
            }
        }
    }

    //开启主角跳跃线程
    public void startJump(){
        if(jumpThread == null){
            jumpThread = new JumpThread();
            dreamer.setJump(true);
            jumpThread.start();
        }
        else{
            if(jumpThread.isAlive()){
                dreamer.setJump(false);
            }
            else{
                jumpThread = new JumpThread();
                dreamer.setJump(true);
                jumpThread.start();
            }
        }
    }

    //启动向左移动的线程
    public void startLeftThread(){
        MoveLeftThread moveLeftThread = new MoveLeftThread();
        dreamer.setDirectionLeft(true);
        dreamer.setDirectionRight(false);
        dreamer.setDreamerDirection(false);
        moveLeftThread.start();
    }
    //停止向左移动的线程
    public void stopLeftThread(){
        dreamer.setDirectionLeft(false);
    }

    //启动向右移动的线程
    public void startRightThread(){
        MoveRightThread moveRightThread = new MoveRightThread();
        dreamer.setDirectionLeft(false);
        dreamer.setDirectionRight(true);
        dreamer.setDreamerDirection(true);
        moveRightThread.start();
    }
    //停止向右移动的线程
    public void stopRightThread(){
        dreamer.setDirectionRight(false);
    }

    //启动怪物移动线程
    public void startMonsterMoveThread(){
        MonsterMoveThread monsterMoveThread = new MonsterMoveThread();
        monsterMoveThread.start();
    }
    //启动怪物攻击线程
    public void startMonsterBulletThread(){
        if(monsterBulletThread == null){
            monsterBulletThread = new MonsterBulletThread();
            monster.setMonsterAttack(true);
            monsterBulletThread.start();
        }
        else{
            if(monsterBulletThread.isAlive()){
                monster.setMonsterAttack(false);
            }
            else{
                monsterBulletThread = new MonsterBulletThread();
                monster.setMonsterAttack(true);
                monsterBulletThread.start();
            }
        }
    }

    //启动魔法攻击线程
    public void startMagicThread(Button buttonMagic){
        this.buttonMagic = buttonMagic;
        if(magicThread ==null){
            magicThread = new Thread(new MagicThread(buttonMagic));
            magicThread.start();
        }else{
            if(magicThread.isAlive()){
                Toast.makeText(mainContext,"技能冷却中",Toast.LENGTH_SHORT).show();
            }else{
                magicThread = new Thread(new MagicThread(buttonMagic));
                magicThread.start();
            }
        }
    }

    //cd管理
    public void removeAlpha(Button buttonMagic){

    }

    //管理线程
    Handler moveHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://向右移动
                    final ValueAnimator animatorRight = ValueAnimator.ofFloat(dreamer.getDreamer().getX(),dreamer.getDreamer().getX()+30);
                    animatorRight.setDuration(400);
                    animatorRight.setInterpolator(new LinearInterpolator());
                    animatorRight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            if(dreamer.getDreamer().getX()>=mainRelativeLayout.getWidth()-dreamer.getDreamer().getWidth()){}
                            else {
                                if(isWin){
                                    float current = (float) animatorRight.getAnimatedValue();
                                    dreamer.getDreamer().setX(current);
                                    if(collide(dreamer.getDreamer(),next1)){
                                        //开启下一关
                                        Intent intent = new Intent();
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.setClass(mainContext,FirstSecondActivity.class);
                                        mainContext.startActivity(intent);
                                    }
                                }else{
                                    float current = (float) animatorRight.getAnimatedValue();
                                    dreamer.getDreamer().setX(current);
                                }
                            }
                        }
                    });
                    animatorRight.start();
                    break;
                case 2://向左移动
                    final ValueAnimator animatorLeft = ValueAnimator.ofFloat(dreamer.getDreamer().getX(),dreamer.getDreamer().getX()-30);
                    animatorLeft.setDuration(400);
                    animatorLeft.setInterpolator(new LinearInterpolator());
                    animatorLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            if(dreamer.getDreamer().getX()<=0){}
                            else {
                                if(isWin){
                                    float current = (float) animatorLeft.getAnimatedValue();
                                    dreamer.getDreamer().setX(current);
                                    if(collide(dreamer.getDreamer(),next1)){
                                        //开启下一关
                                        Intent intent = new Intent();
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.setClass(mainContext,FirstSecondActivity.class);
                                        mainContext.startActivity(intent);
                                    }
                                }else{
                                    float current = (float) animatorLeft.getAnimatedValue();
                                    dreamer.getDreamer().setX(current);
                                }
                            }
                        }
                    });
                    animatorLeft.start();
                    break;
                case 3://发射子弹
                    System.out.println("攻击---------------------------------------------------");
                    bulletX = dreamer.getDreamer().getX();
                    bulletY = dreamer.getDreamer().getY();
                    final ImageView bullet = createBullet();
                    //bullet.setY(dreamer.getDreamer().getY());
                    mainRelativeLayout.addView(bullet);
                    if(dreamer.isDreamerDirection()){
                        bulletMovedX = bulletX+2500;
                    }
                    else {
                        bulletMovedX = bulletX-2500;
                    }
                    final ValueAnimator animator1 = ValueAnimator.ofFloat(bulletX,bulletMovedX);
                    animator1.setDuration(1200);
                    animator1.setInterpolator(new LinearInterpolator());
                    animator1.start();
                    animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float current = (float) animator1.getAnimatedValue();
                            bullet.setX(current);
                            if(monster.getMonsterHp()>0) {
                                if (collide(bullet, monster.getMonster())) {
                                    destroyBullet(bullet);
                                    monster.setMonsterHp(monster.getMonsterHp()-dreamer.getStrength());
                                    if(monster.getMonsterHp()<=0) {
                                        //加载消失动态图，之后销毁monster
                                        GifLoadOneTimeGif.loadOneTimeGif(mainContext,R.drawable.xiaoshi,monster.getMonster(),1,new GifLoadOneTimeGif.GifListener(){
                                            @Override
                                            public void gifPlayComplete() {
                                                mainRelativeLayout.removeView(monster.getMonster());
                                            }
                                        });
                                        //生成传送门
                                        Glide.with(mainContext).load(R.drawable.chuans).into(next1);
                                        isWin = true;
                                    }
                                    animator1.cancel();
                                } else if (current >= mainRelativeLayout.getWidth() || current<=0) {
                                    destroyBullet(bullet);
                                }
                            }
                        }

                    });

                    break;
                case 4://跳跃
                    float dreamerY = dreamer.getDreamer().getY();
                    final ValueAnimator animatorJump = ValueAnimator.ofFloat(dreamerY,dreamerY-500,dreamerY);
                    animatorJump.setDuration(500);
                    animatorJump.setInterpolator(new LinearInterpolator());
                    animatorJump.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float current = (float) animatorJump.getAnimatedValue();
                            dreamer.getDreamer().setY(current);
                        }
                    });
                    animatorJump.start();
                    break;

                case 5://怪物移动
                    float monsterX = monster.getMonster().getX();
                    float monsterMovedX;
                    double a = -0.5 + Math.random();
                    float b = (float)a*200;
                    if(monster.getMonster().getX()-dreamer.getDreamer().getX()>=900){
                        monster.setMonsterDirection(true);
                        monsterMovedX = monsterX-200;
                    }
                    else if(monster.getMonster().getX()-dreamer.getDreamer().getX()<-900){
                        monster.setMonsterDirection(false);
                        monsterMovedX = monsterX+200;
                    }
                    else{
                        if(b>=0){
                            monster.setMonsterDirection(true);
                        }else{
                            monster.setMonsterDirection(false);
                        }
                        monsterMovedX = monsterX+b;
                    }

                    final ValueAnimator animatorMonsterMove = ValueAnimator.ofFloat(monsterX,monsterMovedX);
                    animatorMonsterMove.setDuration(1000);
                    animatorMonsterMove.setInterpolator(new LinearInterpolator());
                    animatorMonsterMove.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float current = (float) animatorMonsterMove.getAnimatedValue();
                            monster.getMonster().setX(current);
                        }
                    });
                    animatorMonsterMove.start();
                    break;
                case 6://怪物子弹
                    //子弹
                    bulletmstTx = monster.getMonster().getX();
                    final ImageView monsterBullet = createMonsterBullet();
                    //monsterBullet.setY(monster.getMonster().getY());
                    mainRelativeLayout.addView(monsterBullet);
                    if(monster.isMonsterDirection()){
                        bulletMovedmstTx = bulletmstTx+2500;
                    }
                    else {
                        bulletMovedmstTx = bulletmstTy-2500;
                    }
                    final ValueAnimator animatorMonsterBullet = ValueAnimator.ofFloat(bulletmstTx,bulletMovedmstTx);
                    animatorMonsterBullet.setDuration(3000);
                    animatorMonsterBullet.setInterpolator(new LinearInterpolator());
                    animatorMonsterBullet.start();
                    animatorMonsterBullet.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float current = (float) animatorMonsterBullet.getAnimatedValue();
                            monsterBullet.setX(current);
                            if(dreamer.getHp()>0) {
                                if (collide(monsterBullet,dreamer.getDreamer())) {
                                    destroyBullet(monsterBullet);
                                    dreamer.setHp(dreamer.getHp()-monster.getMonsterStrength());
                                    if(dreamer.getHp() == 2){
                                        dreamerHp3.setImageResource(R.drawable.hp2);
                                    }else if(dreamer.getHp() ==1){
                                        dreamerHp2.setImageResource(R.drawable.hp2);
                                    }else{
                                        dreamerHp1.setImageResource(R.drawable.hp2);
                                        Toast.makeText(mainContext,"狗带。。",Toast.LENGTH_SHORT).show();
                                        destroyMonster(dreamer.getDreamer());
                                    }
                                    animatorMonsterBullet.cancel();
                                } else if (current >= mainRelativeLayout.getWidth() || current<=0) {
                                    destroyBullet(monsterBullet);
                                }
                            }
                        }
                    });
                    break;
                case 7://魔法攻击
                    final ImageView mofa = createDreamerMagic();
                    mainRelativeLayout.addView(mofa);

                    //加载魔法动态图，之后销毁mofa;
                    GifLoadOneTimeGif gfls = new GifLoadOneTimeGif();
                    gfls.loadOneTimeGif(mainContext,R.drawable.mofa0,mofa,1,new GifLoadOneTimeGif.GifListener(){
                        @Override
                        public void gifPlayComplete() {
                            if(monster.getMonsterHp()>0){
                                //判断命中
                                if(collide(mofa,monster.getMonster())){
                                    monster.setMonsterHp(monster.getMonsterHp()-3);
                                    if(monster.getMonsterHp()<0){
                                        //加载消失动态图，之后销毁monster
                                        GifLoadOneTimeGif.loadOneTimeGif(mainContext,R.drawable.xiaoshi,monster.getMonster(),1,new GifLoadOneTimeGif.GifListener(){
                                            @Override
                                            public void gifPlayComplete() {
                                                mainRelativeLayout.removeView(monster.getMonster());
                                            }
                                        });
                                        //生成传送门
                                        Glide.with(mainContext).load(R.drawable.chuans).into(next1);
                                        isWin = true;
                                    }
                                    System.out.println("命中");
                                }else{
                                    System.out.println("未命中");
                                }
                            }
                            mainRelativeLayout.removeView(mofa);
                        }
                    });
                    break;
                case  8://取消按键透明度(cd)
                    buttonMagic.getBackground().setAlpha(255);

            }
        }
    };



}
