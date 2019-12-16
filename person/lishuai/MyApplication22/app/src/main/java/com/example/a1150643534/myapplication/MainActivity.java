package com.example.a1150643534.myapplication;

import android.animation.*;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Region;
import android.os.*;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.jar.Attributes;

import static android.animation.ObjectAnimator.ofPropertyValuesHolder;
import static com.example.a1150643534.myapplication.R.id.*;

public class MainActivity extends AppCompatActivity {

    private Button goLeft;
    private Button goRight;
    private Button toJump;
    private int mapPages;
    private int myHP;
    private int type=0;
    private int magicTime = 0;//能量盾时间，0为没有
    private int typeJump=0;
    private int typeJump1=1;
    private int typeShoot=0;
    private int allMoNum=10;
    private int mooNum = allMoNum-1;
    private ImageView moView;
    private Button toShoot;
    private Boolean isBlock=false;
    private List<Integer> MoNum;
    //技能书
    private List<Skills> allSkills;
    //怪物类
    private List<MoAll> allMoo;//
    //人物类
    private Miner miner;
    //人物位置
    private float myX;
    private float myY;
    private List<MoAll> allMooo;//排好之后
    private int oneAmmoNum=30;
    private int size = 0;
    private int whereWatch = 2;//1为左，2为右
    private RelativeLayout relativeLayout;
    private DisplayMetrics dm;//获得屏幕大小
    private Button toMagic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.hide();

        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = findViewById(R.id.whereIAm);
        allMoo = new ArrayList<>();
        allMooo = new ArrayList<>();
        allSkills = new ArrayList<>();
        miner = new Miner();
        addMiner();
        addSkills(allSkills);
        addMos(allMoNum);
        reListMo(allMoo);
        moMove(allMoo);
//        reListMoo(allMooo);
//        for(int i =0;i<5;i++){
//            Log.i("full",allMoo.get(i).getMo().getId()+"");
//            Log.i("full",allMoo.get(i).getX()+"");
//        }

//        Log.i("NO",allMoo.size()+"");
        goLeft = findViewById(R.id.goLeft);
        goRight = findViewById(R.id.goRight);
        toJump = findViewById(R.id.toJump);
        toShoot = findViewById(R.id.toShoot);
        toMagic = findViewById(R.id.toMagic);

        // 通过WindowManager获取

        System.out.println("width-display :" + dm.widthPixels);
        System.out.println("heigth-display :" + dm.heightPixels);




        toMagic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addMagic();
                ThreadMagic threadMagic = new ThreadMagic();
                threadMagic.start();


            }
        });

        goLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Threads threads = new Threads();
                        threads.start();
                        type = 1;
                        whereWatch = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        type = 0;
                        break;
                }

                return false;
            }
        });
        goRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Threads threads = new Threads();
                        threads.start();
                        type = 2;
                        whereWatch = 2;
                        break;
                    case MotionEvent.ACTION_UP:
                        type = 0;
                        break;
                }
                return false;
            }
        });
//        toJump.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Threads threads = new Threads();
//                        threads.start();
//                        type = 3;
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        type = 0;
//                        break;
//                }
//                return false;
//            }
//        });
        toJump.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadJump threads = new ThreadJump();
                threads.start();
                typeJump = 1;

            }
        });

        toShoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ThreadShoot threads = new ThreadShoot();
                        threads.start();
                        typeShoot = 1;
                        break;
                    case MotionEvent.ACTION_UP:
                        typeShoot = 0;
                        break;
                }

                return false;
            }
        });


    }

    private void addSkills(List<Skills> list) {
        for(int i=30;i<35;i++) {
            float skillX = (float) (dm.widthPixels * 0.6 + Math.random() * (dm.widthPixels * 0.8 - dm.widthPixels * 0.6 + 1));
            float skillY = (float) (dm.heightPixels * 0.6 + Math.random() * (dm.heightPixels * 0.8 - dm.heightPixels * 0.6 + 1));
            int skillType = (int) (1 + Math.random() * (5 - 1 + 1));
            RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(80, 80);
            ImageView mmageView = new ImageView(MainActivity.this);//new 一个ImageView
            mmageView.setBackgroundColor(getColor(R.color.colorAccent));
            mmageView.setId(i);
            mmageView.setX(skillX);
            mmageView.setY((float) (dm.heightPixels * 0.7));
            Skills skills = new Skills();
            skills.setId(skillType);
            skills.setImageView(mmageView);
            allSkills.add(skills);
            relativeLayout.addView(mmageView, llp);
        }
    }

    private void addMagic() {
        //改变人物图形，初始化计时器

        magicTime = 30;

    }

    //从高到低排序
    private void reListMo(List<MoAll> allMoo) {
        Comparator<MoAll> comparator = new Comparator<MoAll>() {

            @Override
            public int compare(MoAll o1, MoAll o2) {
                float result = o2.getX() - o1.getX(); // 投票按降序
                return (int) result;
            }
        };
        Collections.sort(allMoo, comparator);
    }
    //从低到高排序
    private void reListMoo(List<MoAll> allMoo) {
        Comparator<MoAll> comparator = new Comparator<MoAll>() {

            @Override
            public int compare(MoAll o1, MoAll o2) {
                float result = o1.getX() - o2.getX(); // 投票按升序
                return (int) result;
            }
        };
        Collections.sort(allMoo, comparator);
    }






//添加怪物
    private void addMos(int moNum) {
        int j = moNum;
        int i = 0;
        for(;i<j;i++){
            //(数据类型)(最小值+Math.random()*(最大值-最小值+1))
            //moX怪物x坐标2000-300
            //moY怪物y坐标700-1000
            //moHP怪物血量1-10
            //moVelocity怪物速度1-5
            float moX = (float) (dm.widthPixels*0.3+Math.random()*(dm.widthPixels*0.9-(dm.widthPixels*0.3)+1));
            float moY = (float) (dm.heightPixels*0.6+Math.random()*(dm.heightPixels*0.8-dm.heightPixels*0.6+1));
            int moHP = (int) (1+Math.random()*(10-1+1));
            int moVelocity = (int) (1+Math.random()*(5-1+1));
            RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(80, 80);
            ImageView mmageView = new ImageView(MainActivity.this);//new 一个ImageView
            mmageView.setBackgroundColor(getColor(R.color.colorAccent));
            mmageView.setId(i);
            mmageView.setX(moX);
            MoAll oneMo = new MoAll();
            mmageView.setY((float) (dm.heightPixels*0.7));
            oneMo.setX(moX);
            oneMo.setMo(mmageView);
            oneMo.setY(mmageView.getY());
            oneMo.setMoHP(moHP);
            oneMo.setMoVelocity(moVelocity);
            Log.i("fuckAll1",oneMo.getMo().getId()+"");
            Log.i("fuckAll2",oneMo.getX()+"");
            Log.i("fuckAll3",oneMo.getY()+"");
            allMoo.add(i,oneMo);
            allMooo.add(i,oneMo);
            relativeLayout.addView(mmageView, llp);

        }
    }

    private void moMove(List<MoAll> allMoo) {
        for(int i=0;i<allMoo.size();i++){
//            ThreadMoMove threadMoMove = new ThreadMoMove(allMoo.get(i).getMo(),allMoo.get(i).getMoVelocity());
//            threadMoMove.start();
            Message msg = handler1.obtainMessage();

            msg.arg1 = 2;//x追击
            msg.obj = allMoo.get(i).getMo();
            msg.arg2 = allMoo.get(i).getMoVelocity();

            myX = miner.getMyImage().getX();
            myY = miner.getMyImage().getY();
            handler1.sendMessage(msg);

        }


    }

    private void addMiner() {
        RelativeLayout.LayoutParams llpp = new RelativeLayout.LayoutParams(80, 80);

//        llpp.setMargins(300,300,0,0);
        ImageView mmageView = new ImageView(MainActivity.this);//new 一个ImageView
        mmageView.setBackgroundColor(getColor(R.color.colorPrimary));
            mmageView.setId(R.id.miner);
            mmageView.setX((float) (dm.widthPixels*0.2));
            mmageView.setY((float) (dm.heightPixels*0.7));
            miner.setMyHP(10);//设置人物血量
            miner.setMyImage(mmageView);
        relativeLayout.addView(mmageView, llpp); //最后一步，添加控件到布局中
    }
    //size:代码中获取到的图片数量
    private void addAmmo(){

//            oneAmmoNum++;
            RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(20, 20);
            if(whereWatch==2) {
                llp.setMargins((int) (miner.getMyImage().getX() + miner.getMyImage().getWidth()), (int) miner.getMyImage().getY() + miner.getMyImage().getHeight() / 2 - 10, 0, 0);
            }else {
                llp.setMargins((int) miner.getMyImage().getX()-20, (int) miner.getMyImage().getY() + miner.getMyImage().getHeight() / 2 - 10, 0, 0);
            }
            ImageView mImageView = new ImageView(MainActivity.this);//new 一个ImageView
            mImageView.setBackgroundColor(getColor(R.color.colorPrimary));
//            mImageView.setId(oneAmmoNum);

            relativeLayout.addView(mImageView, llp); //最后一步，添加控件到布局中
            //子弹移动
            ThreadAmmo ammo = new ThreadAmmo(mImageView);
            ammo.start();
//            ammoMove(mImageView);
    }

    private void ammoMove(final ImageView imageView) {

        if(whereWatch==2) {

            float cScrollX1 = imageView.getX() + 3000;

//        Log.i("信息", cScrollX1 + "");
            final PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("translationX", cScrollX1);
            final ObjectAnimator k = ofPropertyValuesHolder(imageView, propertyValuesHolder2);
            k.setDuration(3500);
            k.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if(allMoo.size()!=0) {
                        int m = allMoo.size();
                        int a = m;

                        for(int i = 0;i<allMoo.size();i++) {
                            moView = findViewById(allMoo.get(i).getMo().getId());
                            if (moView != null && imageView != null) {
                                Point moP = new Point((int) moView.getX(), (int) moView.getY());
                                Point amP = new Point((int) imageView.getX(), (int) imageView.getY());
//                            Log.i("mo", moView.getX() + "");
//                            Log.i("im", imageView.getX() + "");
//                            Log.i("fuck",allMoo.get(0).getMo().getId()+"");
//                            Log.i("fuck1",allMoo.get(1).getMo().getId()+"");
//                            Log.i("fuck2",allMoo.get(2).getMo().getId()+"");
//                            Log.i("fuck3",allMoo.get(3).getMo().getId()+"");
//                            Log.i("fuck4", allMoo.get(a).getX() + "");
//                            Log.i("moo",allMoo.get(0).getX()+","+allMooo.get(0).getX());
//                            relativeLayout.removeView(moView);
                                if((moView.getY()<=imageView.getY()&&moView.getY()+moView.getHeight()>=imageView.getY())||(moView.getY()<=imageView.getY()+imageView.getHeight()&&moView.getY()+moView.getHeight()>=imageView.getY()+imageView.getHeight())) {
                                    if (moView.getX() <= imageView.getX() && moView.getX() + moView.getWidth() >= imageView.getX() + imageView.getWidth()) {
                                        int moHp = allMoo.get(i).getMoHP();

                                        if(moHp-1==0) {
                                            isBlock = true;
                                            relativeLayout.removeView(moView);
                                            allMoo.remove(i);
//                                        Log.i("ok", "ok");

                                        }else {
                                            allMoo.get(i).setMoHP(moHp-1);
                                        }

                                        k.cancel();

                                    }
                                }
                            }
                        }
                    }


                }
            });
            k.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    relativeLayout.removeView(imageView);
                }
            });
            k.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    relativeLayout.removeView(imageView);
                }
            });
            k.start();

        }else {
            float cScrollX1 = imageView.getX() - 4000;

//        Log.i("信息", cScrollX1 + "");
            PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("translationX", cScrollX1);
            final ObjectAnimator k = ofPropertyValuesHolder(imageView, propertyValuesHolder2);
            k.setDuration(3500);
            k.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if(allMoo.size()!=0) {
                        int m = allMoo.size();
                        int a = m - 1;
                        for(int i = 0;i<allMoo.size();i++) {
                            Region region = new Region();
                            moView = findViewById(allMoo.get(i).getMo().getId());
                            if(moView!=null&&imageView!=null) {
//                                Log.i("fuck4", allMooo.get(a).getX() + "");
//                            Log.i("moo",allMoo.get(0).getX()+","+allMooo.get(0).getX());
//                            relativeLayout.removeView(moView);
                                if((moView.getY()<=imageView.getY()&&moView.getY()+moView.getHeight()>=imageView.getY())||(moView.getY()<=imageView.getY()+imageView.getHeight()&&moView.getY()+moView.getHeight()>=imageView.getY()+imageView.getHeight())) {
                                    if (moView.getX() <= imageView.getX() && moView.getX() + moView.getWidth() >= imageView.getX()) {
                                        isBlock = true;
                                        int moHp = allMoo.get(i).getMoHP();

                                        if(moHp-1==0) {
                                            relativeLayout.removeView(moView);
                                            allMoo.remove(i);

                                        }
                                        else {
                                            allMoo.get(i).setMoHP(moHp-1);
                                        }

                                        k.cancel();

                                    }
                                }
                            }
                        }
                    }



                }
            });
            k.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    relativeLayout.removeView(imageView);
                }
            });
            k.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    relativeLayout.removeView(imageView);
                }
            });
            k.start();

        }
    }

    class Threads implements Runnable{
        private Thread t;

        public void run() {
            while (type==1) {
                try {
                    handler.sendEmptyMessage(1);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (type==2){
                try {
                    handler.sendEmptyMessage(2);
                    Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
//            while ((type==3)){
//                handler.sendEmptyMessage(3);
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }



        }
        public void start(){
            if (t == null) {
                t = new Thread(this);
                t.start();
            }
        }
    }
//跳跃
    class ThreadJump implements Runnable{

        private Thread t;

        @Override
        public void run() {
//            Log.i("dad",typeJump+","+typeJump1);
            while ((typeJump==1&&typeJump1==1)){
                handler.sendEmptyMessage(3);
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

            }
        }
        public void start(){
            if (t == null) {
                t = new Thread(this);
                t.start();
            }
        }
    }
//射击
    class ThreadShoot implements Runnable{

        private Thread t;

        @Override
        public void run() {
            while ((typeShoot==1)){
                handler.sendEmptyMessage(4);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        public void start(){
            if (t == null) {
                t = new Thread(this);
                t.start();
            }
        }
    }

//监听子弹
    class ThreadAmmo implements Runnable{

        private Thread t;
        private ImageView imageView;

    public ThreadAmmo(ImageView mImageView) {
        imageView = mImageView;
    }

        @Override
        public void run() {
            Message msg = handler1.obtainMessage();
            msg.obj = imageView;
            msg.arg1 = 1;
            handler1.sendMessage(msg);

        }

        public void start(){
            if (t == null) {
                t = new Thread(this);
                t.start();
            }
        }
    }
//    class ThreadMoMove implements Runnable{
//
//        private Thread t;
//        private ImageView imageView;
//        private int moVelocity;
//
//        public ThreadMoMove(ImageView mImageView,int mMoVelocity){
//            imageView = mImageView;
//            moVelocity = mMoVelocity;
//        }
//        @Override
//        public void run() {
//            Message msg = handler1.obtainMessage();
//
//            msg.arg1 = 2;
//            msg.obj = imageView;
//            msg.arg2 = moVelocity;
//            if(whereWatch==2) {
//                myX = miner.getMyImage().getX()-miner.getMyImage().getWidth();
//                myY = miner.getMyImage().getY();
//            }else {
//                myX = miner.getMyImage().getX()+miner.getMyImage().getWidth();
//                myY = miner.getMyImage().getY();
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            handler1.sendMessage(msg);
//        }
//
//        public void start(){
//            if (t == null) {
//                t = new Thread(this);
//                t.start();
//            }
//        }
//    }

    class ThreadMagic implements Runnable {

        private Thread t;

        @Override
        public void run() {
            while (magicTime!=0) {
                try {
                    Message msg = handler1.obtainMessage();
                    msg.arg1 = 3;
                    handler1.sendMessage(msg);

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }

        public void start(){
            if (t == null) {
                t = new Thread(this);
                t.start();
            }
        }
    }


    //捡技能书



//人物、怪物移动

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //能量盾-错误方式
//                    if(findViewById(magic)!=null) {
//                        float cMScrollX = (float) (findViewById(magic).getX() - dm.widthPixels*0.02);
//                        PropertyValuesHolder propertyValuesHolder5 = PropertyValuesHolder.ofFloat("translationX", cMScrollX);
//                        final ObjectAnimator m = ofPropertyValuesHolder(findViewById(magic),propertyValuesHolder5);
//                        m.setDuration(100);
//                        m.start();
//                    }
                    float cScrollX = (float) (miner.getMyImage().getX() - dm.widthPixels*0.02);

                    Log.i("信息", cScrollX + "");
                    PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("translationX", cScrollX);

                    final ObjectAnimator o = ofPropertyValuesHolder(miner.getMyImage(), propertyValuesHolder1);

                    o.setDuration(100);
                    o.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            if(allSkills!=null){
                                for(int i=0;i<allSkills.size();i++){
                                    if(miner.getMyImage().getY()==allSkills.get(i).getImageView().getY()) {
                                        if ((allSkills.get(i).getImageView().getX() + allSkills.get(i).getImageView().getWidth() > miner.getMyImage().getX())&&(allSkills.get(i).getImageView().getX()+allSkills.get(i).getImageView().getWidth()<miner.getMyImage().getX()+miner.getMyImage().getWidth() )) {
                                            Log.i("skill1",""+allSkills.get(i).getId());
                                            switch (allSkills.get(i).getId()){
                                                case 1:
                                                    Log.i("skill","skill1");
                                                    addSkill1();
                                                    break;
                                                case 2:
                                                    addSkill2();
                                                    break;
                                                case 3:
                                                    addSkill3();
                                                    break;
                                                case 4:
                                                    addSkill4();
                                                    break;
                                                case 5:
                                                    addSkill5();
                                                    break;
                                            }
                                            relativeLayout.removeView(allSkills.get(i).getImageView());
                                            allSkills.remove(i);
                                        }
                                    }
                                }
                            }
                        }
                    });
                    o.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            moMove(allMoo);
                        }
                    });
                    o.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            moMove(allMoo);
                        }
                    });

                    o.start();
                    break;
                case 2:
                    float cScrollX1 = (float) (miner.getMyImage().getX() + dm.widthPixels*0.02);
                    //能量盾-错误方式
//                    if(findViewById(magic)!=null) {
//                        float cMScrollX1 = (float) (findViewById(magic).getX() + dm.widthPixels * 0.02);
//                        PropertyValuesHolder propertyValuesHolder6 = PropertyValuesHolder.ofFloat("translationX", cMScrollX1);
//                        final ObjectAnimator j = ofPropertyValuesHolder(findViewById(magic), propertyValuesHolder6);
//                        j.setDuration(100);
//                        j.start();
//                    }
                    Log.i("信息", cScrollX1 + "");
                    PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("translationX", cScrollX1);
                    final ObjectAnimator k = ofPropertyValuesHolder(miner.getMyImage(), propertyValuesHolder2);
                    k.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            moMove(allMoo);
                        }
                    });
                    k.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            moMove(allMoo);
                        }
                    });
                    k.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            if(allSkills!=null){
                                for(int i=0;i<allSkills.size();i++){
                                    if(miner.getMyImage().getY()==allSkills.get(i).getImageView().getY()) {
                                        if ((allSkills.get(i).getImageView().getX() < (miner.getMyImage().getX() + miner.getMyImage().getWidth())&&(allSkills.get(i).getImageView().getX() > miner.getMyImage().getX()))){
                                            Log.i("skill",""+allSkills.get(i).getId());
                                            switch (allSkills.get(i).getId()){
                                                case 1:
                                                    Log.i("skill","skill1");
                                                    addSkill1();
                                                    break;
                                                case 2:
                                                    addSkill2();
                                                    break;
                                                case 3:
                                                    addSkill3();
                                                    break;
                                                case 4:
                                                    addSkill4();
                                                    break;
                                                case 5:
                                                    addSkill5();
                                                    break;
                                            }
                                            relativeLayout.removeView(allSkills.get(i).getImageView());
                                            allSkills.remove(i);
                                        }
                                    }
                                }
                            }
                        }
                    });
                    k.setDuration(100);
                    k.start();
                    break;
                case 3:
//                    float cScrollY = miner.getY();
//                    float cScrollY1 = miner.getY() - 300;
                    typeJump=0;
                    typeJump1=0;
                    float cScrollY = miner.getMyImage().getY();
                    float cScrollY1 = (float) (miner.getMyImage().getY()-dm.heightPixels*0.2);
//                    Log.i("信息", cScrollY + "");
//                    Log.i("信息", cScrollY1 + "");
                    PropertyValuesHolder propertyValuesHolder3 = PropertyValuesHolder.ofFloat("translationY", cScrollY);
                    PropertyValuesHolder propertyValuesHolder4 = PropertyValuesHolder.ofFloat("translationY", cScrollY1);
                    final ObjectAnimator p = ofPropertyValuesHolder(miner.getMyImage(), propertyValuesHolder3);
                    final ObjectAnimator l = ofPropertyValuesHolder(miner.getMyImage(),propertyValuesHolder4);
                    p.setDuration(1000);
                    p.setInterpolator(new AccelerateInterpolator());
                    l.setDuration(1000);
                    p.setStartDelay(1000);
                    l.setInterpolator(new DecelerateInterpolator());
                    p.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            typeJump1=1;
                        }
                    });
                    //能量盾-错误方式
//                    if(findViewById(magic)!=null){
//                        float mcScrollY = miner.getMyImage().getY();
//                        float mcScrollY1 = (float) (miner.getMyImage().getY()-dm.heightPixels*0.2);
//                        PropertyValuesHolder propertyValuesHolderm3 = PropertyValuesHolder.ofFloat("translationY", mcScrollY);
//                        PropertyValuesHolder propertyValuesHolderm4 = PropertyValuesHolder.ofFloat("translationY", mcScrollY1);
//                        final ObjectAnimator pm = ofPropertyValuesHolder(findViewById(magic), propertyValuesHolderm3);
//                        final ObjectAnimator lm = ofPropertyValuesHolder(findViewById(magic),propertyValuesHolderm4);
//                        pm.setDuration(1000);
//                        pm.setInterpolator(new AccelerateInterpolator());
//                        lm.setDuration(1000);
//                        pm.setStartDelay(1000);
//                        lm.setInterpolator(new DecelerateInterpolator());
//                        pm.start();
//                        lm.start();
//                    }
                    p.start();
                    l.start();
                    break;
                case 4:
                    addAmmo();

                    break;

            }

        }
    };

    private void addSkill1() {
        Log.i("ok","ican");
        if(findViewById(skill1)==null){
            Log.i("ok","ican");
            float skillX = (float) (dm.widthPixels * 0.2);
            float skillY = (float) (dm.heightPixels * 0.2);
            RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(160, 100);
            Button mmageView = new Button(MainActivity.this);//new 一个ImageView
            mmageView.setId(skill1);
            mmageView.setX(skillX);
            mmageView.setY(skillY);

            relativeLayout.addView(mmageView, llp);
        }

    }
    private void addSkill2() {
        Log.i("ok","ican");
        if(findViewById(skill2)==null){
            Log.i("ok","ican");
            float skillX = (float) (dm.widthPixels * 0.3);
            float skillY = (float) (dm.heightPixels * 0.2);
            RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(160, 100);
            Button mmageView = new Button(MainActivity.this);//new 一个ImageView
            mmageView.setId(skill2);
            mmageView.setX(skillX);
            mmageView.setY(skillY);
            relativeLayout.addView(mmageView, llp);
        }

    }
    private void addSkill3() {
        Log.i("ok","ican");
        if(findViewById(skill3)==null){
            Log.i("ok","ican");
            float skillX = (float) (dm.widthPixels * 0.4);
            float skillY = (float) (dm.heightPixels * 0.2);
            RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(160, 100);
            Button mmageView = new Button(MainActivity.this);//new 一个ImageView
            mmageView.setId(skill3);
            mmageView.setX(skillX);
            mmageView.setY(skillY);
            relativeLayout.addView(mmageView, llp);
        }

    }
    private void addSkill4() {
        Log.i("ok","ican");
        if(findViewById(skill4)==null){
            Log.i("ok","ican");
            float skillX = (float) (dm.widthPixels * 0.5);
            float skillY = (float) (dm.heightPixels * 0.2);
            RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(160, 100);
            Button mmageView = new Button(MainActivity.this);//new 一个ImageView
            mmageView.setId(skill4);
            mmageView.setX(skillX);
            mmageView.setY(skillY);
            relativeLayout.addView(mmageView, llp);
        }
    }
    private void addSkill5() {
        Log.i("ok","ican");
        if(findViewById(skill5)==null){
            Log.i("ok","ican");
            float skillX = (float) (dm.widthPixels * 0.6);
            float skillY = (float) (dm.heightPixels * 0.2);
            RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(160, 100);
            Button mmageView = new Button(MainActivity.this);//new 一个ImageView
            mmageView.setId(skill5);
            mmageView.setX(skillX);
            mmageView.setY(skillY);
            relativeLayout.addView(mmageView, llp);
        }

    }


    final Handler handler1 = new Handler(){
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.arg1){
                case 1:
                    //子弹
                    final ImageView imageView = (ImageView) msg.obj;
                    ammoMove(imageView);
                    break;
                case 2:
                    //怪物移动
                    final ImageView imageView1 = (ImageView) msg.obj;
                    final float myx = myX;
                    final float myy = myY;
                    final float imX = imageView1.getX();
                    final float imW = imageView1.getWidth();
                    final float imY = imageView1.getY();
                    final float imH = imageView1.getHeight();

//                    Log.i("信息", cScrollX + "");
                    PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("X",myX);
//                    PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("Y",myY);
                    final ObjectAnimator o = ofPropertyValuesHolder(imageView1, propertyValuesHolder1);
                    if(imageView1.getX()>myX) {
                        o.setDuration((long) ((imageView1.getX() - myX)/ msg.arg2)*20);
                    }else {
                        o.setDuration((long) ((myX-imageView1.getX())/msg.arg2)*20);
                    }
                    o.setInterpolator(new LinearInterpolator());
                    o.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            if(myy!=myY||myx!=myX){
                                o.cancel();
                            }
                            if(myY<dm.heightPixels*0.7){
                                PropertyValuesHolder propertyValuesHolder3 = PropertyValuesHolder.ofFloat("translationY", (float) (dm.heightPixels*0.7-dm.heightPixels*0.2));
                                PropertyValuesHolder propertyValuesHolder4 = PropertyValuesHolder.ofFloat("translationY", (float) (dm.heightPixels*0.7));
                                final ObjectAnimator o = ofPropertyValuesHolder(imageView1, propertyValuesHolder3);
                                final ObjectAnimator p = ofPropertyValuesHolder(imageView1,propertyValuesHolder4);
                                o.setDuration(500);
                                o.setInterpolator(new DecelerateInterpolator());
                                p.setInterpolator(new AccelerateInterpolator());
                                p.setStartDelay(500);
                                p.setDuration(500);
                                if(o.isStarted()==false&&p.isStarted()==false&&myY>dm.heightPixels*0.7-dm.heightPixels*0.2) {
                                    o.start();
                                    p.start();
                                }
                            }

                        }
                    });
                    o.start();
                    break;
                case 3:
                    //能量盾
                    magicTime -= 1;
                    Log.i("magicTime", String.valueOf(magicTime));
                    if(magicTime==0&&findViewById(magic)!=null){
                        //消除能量盾
                    }
                    //错误方式
//                    if(magicTime!=0&&findViewById(magic)!=null&&(findViewById(magic).getX()!=miner.getMyImage().getX()+dm.widthPixels*0.02)&&(findViewById(magic).getY()!=miner.getMyImage().getY()+dm.heightPixels*0.02)) {
//                        findViewById(magic).setX((float) (miner.getMyImage().getX()-dm.widthPixels*0.02));
//                        findViewById(magic).setY((float) (miner.getMyImage().getY()-dm.heightPixels*0.02));
//                    }
                    break;
            }
        }
    };
}
