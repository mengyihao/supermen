package com.example.dreamdemo;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {
        private static MyApplication mAppApplication;

        public ArrayList<Activity> activityList = null;

        @Override
        public void onCreate() {
            // TODO Auto-generated method stub
            super.onCreate();
            mAppApplication = this;
        }

        /** getApplication */
        public static MyApplication getApp() {
            if (mAppApplication == null) {
                mAppApplication = new MyApplication();
            }
            return mAppApplication;
        }

        /** 给集合添加activity */
        public void addActivity(Activity acitivity) {
            if(activityList == null){
                activityList = new ArrayList<Activity>();
            }
            activityList.add(acitivity);
        }

//        // 添加Activity到容器中
//        public void addActivity(Activity activity) {
//            if (activitys != null && activitys.size() > 0) {
//                if (!activitys.contains(activity)) {
//                    activitys.add(activity);
//                }
//            } else {
//                activitys.add(activity);
//            }
//
//        }

        /** 从集合移除activity */
        public void clearActivity(){
            activityList.clear();
        }

        /** 遍历集合，清除集合所有的activity */
        public void exit() {
            for (Activity activity : activityList) {
                activity.finish();
            }
            System.exit(0);
        }
}
