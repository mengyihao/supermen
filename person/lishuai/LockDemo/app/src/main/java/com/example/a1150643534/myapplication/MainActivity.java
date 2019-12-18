package com.example.a1150643534.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {

    //四个按钮
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    //已解锁的关数
    private int level = 3;
    //最大关数
    private int maxLevel = 4;
    //最小关数
    private int minLevel = 1;
    //关数min-max
    private String[] levelList = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    //sd卡权限
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    private String DEFAULT_FILENAME = "level.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);


        //点击跳转到关卡
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
//        button1.setClickable(false);
//        button1.setBackgroundResource(R.drawable.lock);
//        button2.setClickable(false);
//        button2.setBackgroundResource(R.drawable.lock);
//        button3.setClickable(false);
//        button3.setBackgroundResource(R.drawable.lock);
//        button4.setClickable(false);
//        button4.setBackgroundResource(R.drawable.lock);


//        if(level==1){
//            button1.setClickable(true);
//            button1.setBackgroundResource(R.drawable.empty);
//        }else if(level==2){
//            button1.setClickable(true);
//            button1.setBackgroundResource(R.drawable.empty);
//            button2.setClickable(true);
//            button2.setBackgroundResource(R.drawable.empty);
//        }else if(level==3){
//            button1.setClickable(true);
//            button1.setBackgroundResource(R.drawable.empty);
//            button2.setClickable(true);
//            button2.setBackgroundResource(R.drawable.empty);
//            button3.setClickable(true);
//            button3.setBackgroundResource(R.drawable.empty);
//        }else if(level==4){
//            button1.setClickable(true);
//            button1.setBackgroundResource(R.drawable.empty);
//            button2.setClickable(true);
//            button2.setBackgroundResource(R.drawable.empty);
//            button3.setClickable(true);
//            button3.setBackgroundResource(R.drawable.empty);
//            button4.setClickable(true);
//            button4.setBackgroundResource(R.drawable.empty);
//
//        }


        //设置关卡按钮
        buttonSet();
    }

    public void buttonSet() {
        verifyStoragePermissions(MainActivity.this);
        //读取sd卡中的关卡信息
        try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    DEFAULT_FILENAME);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String readline = "";
            StringBuffer sb = new StringBuffer();
            while ((readline = br.readLine()) != null) {
                System.out.println("readline:" + readline);
                sb.append(readline);
            }
            br.close();
            System.out.println("读取成功：" + sb.toString());
            int i = 0;
            while (sb.toString().equals(levelList[i]) == false&&levelList[i]!=null) {
                i++;
            }
            if(i+1>=level) {
                level = i + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //遍历已解锁关卡
        Resources res = getResources();
        for (int i = minLevel; i <= level; i++) {
            int id = res.getIdentifier("button" + i, "id", getPackageName());
            findViewById(id).setBackground(getDrawable(R.drawable.empty));
            findViewById(id).setClickable(true);
        }
        //遍历未解锁关卡
        for (int i = level + 1; i <= maxLevel; i++) {
            int id = res.getIdentifier("button" + i, "id", getPackageName());
            findViewById(id).setBackground(getDrawable(R.drawable.lock));
            findViewById(id).setClickable(false);
        }

    }


    //权限检测与申请
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//    /**
//     * 判断SDCard是否存在 [当没有外挂SD卡时，内置ROM也被识别为存在sd卡]
//     *
//     * @return
//     */
//    public static boolean isSdCardExist() {
//        return Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED);
//    }
//    /**
//     * 获取SD卡根目录路径
//     *
//     * @return
//     */
//    public static String getSdCardPath() {
//        boolean exist = isSdCardExist();
//        String sdpath = "";
//        if (exist) {
//            sdpath = Environment.getExternalStorageDirectory()
//                    .getAbsolutePath();
//        } else {
//            sdpath = "不适用";
//        }
//        return sdpath;
//
//    }
//
//    /**
//     * 获取默认的文件路径
//     *
//     * @return
//     */
//    public static String getDefaultFilePath() {
//        String filepath = "";
//        File file = new File(Environment.getExternalStorageDirectory(),
//                "abc.txt");
//        if (file.exists()) {
//            filepath = file.getAbsolutePath();
//        } else {
//            filepath = "不适用";
//        }
//        return filepath;
//    }
//}
