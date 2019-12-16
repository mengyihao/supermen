package com.example.a1150643534.myapplication;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.a1150643534.myapplication.R.style.NormalDialogStyle;

public class MainActivity extends AppCompatActivity {

    private int chat = 1;
    private int chat1 = 0;
    private List<ChatList> chatList;
    private Button next;
    private int where=0;
    private CustomOnclickListener listener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        next = findViewById(R.id.next1);
        listener = new CustomOnclickListener();
        chatList = new ArrayList<>();
        for(int i = 0;i<10;i++){
            ChatList chat = new ChatList();
            int type = i%2;
            String chatInfo = i+"";
            chat.setChatList(chatInfo);
            chat.setType(type);
            chatList.add(chat);
        }
//        for(int i = 0;i<10;i++){
//            Log.i("list",chatList.get(i).getChatList());
//        }


//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (chatList != null) {
//                    if (chatList.get(where).getType() == 0) {
////                        RelativeLayout.LayoutParams llpp = new RelativeLayout.LayoutParams(80, 80);
//                        final Dialog dialog = new Dialog(MainActivity.this, NormalDialogStyle);
//                        View view = View.inflate(MainActivity.this, R.layout.layout, null);
//                        TextView chatPlace =  view.findViewById(R.id.textPlace);
//                        Button next1 = view.findViewById(R.id.next);
//                        dialog.setContentView(view);
//                        dialog.setCanceledOnTouchOutside(false);
//                        view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(MainActivity.this).getScreenHeight() * 0.23f));
//                        dialog.setCancelable(true);
////                        LinearLayout.LayoutParams llpp1 = new LinearLayout.LayoutParams(ScreenSizeUtils.getInstance(MainActivity.this).getScreenWidth(), (int) (ScreenSizeUtils.getInstance(MainActivity.this).getScreenHeight() * 0.23f));
//                        Window dialogWindow = dialog.getWindow();
//                        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                        lp.width = ScreenSizeUtils.getInstance(MainActivity.this).getScreenWidth();
////                        TextView chatPlace = new TextView(MainActivity.this);
////                        dialog.addContentView(chatPlace, llpp);
//                        chatPlace.setText(chatList.get(where).getChatList());
//                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                        lp.gravity = Gravity.TOP;
//                        dialogWindow.setAttributes(lp);
//                        next1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.dismiss();
//                                where++;
//                            }
//                        });
//                        dialog.show();
//                    } else if (chatList.get(where).getType() == 1) {
//                        final Dialog dialog1 = new Dialog(MainActivity.this, R.style.NormalDialogStyle);
//                        View view1 = View.inflate(MainActivity.this, R.layout.layout, null);
//                        dialog1.setContentView(view1);
//                        dialog1.setCanceledOnTouchOutside(false);
//                        view1.setMinimumHeight((int) (ScreenSizeUtils.getInstance(MainActivity.this).getScreenHeight() * 0.23f));
////                        RelativeLayout.LayoutParams llpp = new RelativeLayout.LayoutParams(ScreenSizeUtils.getInstance(MainActivity.this).getScreenWidth(), (int) (ScreenSizeUtils.getInstance(MainActivity.this).getScreenHeight() * 0.23f));
//                        Window dialogWindow1 = dialog1.getWindow();
//                        WindowManager.LayoutParams lp1 = dialogWindow1.getAttributes();
//                        lp1.width = ScreenSizeUtils.getInstance(MainActivity.this).getScreenWidth();
//                        lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                        TextView chatPlace =  view1.findViewById(R.id.textPlace);
////                        TextView chatPlace = new TextView(MainActivity.this);
////                        dialog1.addContentView(chatPlace, llpp);
//                        chatPlace.setText(chatList.get(where).getChatList());
//                        lp1.gravity = Gravity.BOTTOM;
//                        dialogWindow1.setAttributes(lp1);
//                        Button next1 = view1.findViewById(R.id.next);
//                        next1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog1.dismiss();
//                                where++;
//                            }
//                        });
//
//                        dialog1.show();
//                    }else
//                        where++;
//                }
//            }
//        });
        next.setOnClickListener(listener);


    }

    class CustomOnclickListener implements View.OnClickListener{



        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.next1:
                    if (chatList != null) {
                        if (chatList.get(where).getType() == 0) {
//                        RelativeLayout.LayoutParams llpp = new RelativeLayout.LayoutParams(80, 80);
                            final Dialog dialog = new Dialog(MainActivity.this, NormalDialogStyle);
                            View view = View.inflate(MainActivity.this, R.layout.layout, null);
                            TextView chatPlace =  view.findViewById(R.id.textPlace);
                            final Button next1 = view.findViewById(R.id.next);
                            dialog.setContentView(view);
                            dialog.setCanceledOnTouchOutside(false);
                            view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(MainActivity.this).getScreenHeight() * 0.23f));
                            dialog.setCancelable(false);
//                        LinearLayout.LayoutParams llpp1 = new LinearLayout.LayoutParams(ScreenSizeUtils.getInstance(MainActivity.this).getScreenWidth(), (int) (ScreenSizeUtils.getInstance(MainActivity.this).getScreenHeight() * 0.23f));
                            Window dialogWindow = dialog.getWindow();
                            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                            lp.width = ScreenSizeUtils.getInstance(MainActivity.this).getScreenWidth();
//                        TextView chatPlace = new TextView(MainActivity.this);
//                        dialog.addContentView(chatPlace, llpp);
                            chatPlace.setText(chatList.get(where).getChatList());
                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            lp.gravity = Gravity.TOP;
                            dialogWindow.setAttributes(lp);
                            next1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    where++;
                                    onclick();
                                }
                            });
                            dialog.show();
                        } else if (chatList.get(where).getType() == 1) {
                            final Dialog dialog1 = new Dialog(MainActivity.this, R.style.NormalDialogStyle);
                            View view1 = View.inflate(MainActivity.this, R.layout.layout, null);
                            dialog1.setContentView(view1);
                            dialog1.setCanceledOnTouchOutside(false);
                            dialog1.setCancelable(false);
                            view1.setMinimumHeight((int) (ScreenSizeUtils.getInstance(MainActivity.this).getScreenHeight() * 0.23f));
//                        RelativeLayout.LayoutParams llpp = new RelativeLayout.LayoutParams(ScreenSizeUtils.getInstance(MainActivity.this).getScreenWidth(), (int) (ScreenSizeUtils.getInstance(MainActivity.this).getScreenHeight() * 0.23f));
                            Window dialogWindow1 = dialog1.getWindow();
                            WindowManager.LayoutParams lp1 = dialogWindow1.getAttributes();
                            lp1.width = ScreenSizeUtils.getInstance(MainActivity.this).getScreenWidth();
                            lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            TextView chatPlace =  view1.findViewById(R.id.textPlace);
//                        TextView chatPlace = new TextView(MainActivity.this);
//                        dialog1.addContentView(chatPlace, llpp);
                            chatPlace.setText(chatList.get(where).getChatList());
                            lp1.gravity = Gravity.BOTTOM;
                            dialogWindow1.setAttributes(lp1);
                            Button next1 = view1.findViewById(R.id.next);
                            next1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog1.dismiss();
                                    where++;
                                    onclick();
                                }
                            });

                            dialog1.show();
                        }else
                            where++;
                    }
                    break;

            }
        }
    }
    private void onclick(){
         if (chatList != null) {
            if (chatList.get(where).getType() == 0) {
//                        RelativeLayout.LayoutParams llpp = new RelativeLayout.LayoutParams(80, 80);
                final Dialog dialog = new Dialog(MainActivity.this, NormalDialogStyle);
                View view = View.inflate(MainActivity.this, R.layout.layout, null);
                TextView chatPlace =  view.findViewById(R.id.textPlace);
                Button next1 = view.findViewById(R.id.next);
                dialog.setContentView(view);
                dialog.setCanceledOnTouchOutside(false);
                view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(MainActivity.this).getScreenHeight() * 0.23f));
                dialog.setCancelable(true);
//                        LinearLayout.LayoutParams llpp1 = new LinearLayout.LayoutParams(ScreenSizeUtils.getInstance(MainActivity.this).getScreenWidth(), (int) (ScreenSizeUtils.getInstance(MainActivity.this).getScreenHeight() * 0.23f));
                Window dialogWindow = dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = ScreenSizeUtils.getInstance(MainActivity.this).getScreenWidth();
//                        TextView chatPlace = new TextView(MainActivity.this);
//                        dialog.addContentView(chatPlace, llpp);
                chatPlace.setText(chatList.get(where).getChatList());
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.TOP;
                dialogWindow.setAttributes(lp);
                next1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        where++;
                        onclick();
                    }
                });
                dialog.show();
            } else if (chatList.get(where).getType() == 1) {
                final Dialog dialog1 = new Dialog(MainActivity.this, R.style.NormalDialogStyle);
                View view1 = View.inflate(MainActivity.this, R.layout.layout, null);
                dialog1.setContentView(view1);
                dialog1.setCanceledOnTouchOutside(false);
                view1.setMinimumHeight((int) (ScreenSizeUtils.getInstance(MainActivity.this).getScreenHeight() * 0.23f));
//                        RelativeLayout.LayoutParams llpp = new RelativeLayout.LayoutParams(ScreenSizeUtils.getInstance(MainActivity.this).getScreenWidth(), (int) (ScreenSizeUtils.getInstance(MainActivity.this).getScreenHeight() * 0.23f));
                Window dialogWindow1 = dialog1.getWindow();
                WindowManager.LayoutParams lp1 = dialogWindow1.getAttributes();
                lp1.width = ScreenSizeUtils.getInstance(MainActivity.this).getScreenWidth();
                lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
                TextView chatPlace =  view1.findViewById(R.id.textPlace);
//                        TextView chatPlace = new TextView(MainActivity.this);
//                        dialog1.addContentView(chatPlace, llpp);
                chatPlace.setText(chatList.get(where).getChatList());
                lp1.gravity = Gravity.BOTTOM;
                dialogWindow1.setAttributes(lp1);
                Button next1 = view1.findViewById(R.id.next);
                next1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                        where++;
                        onclick();
                    }
                });

                dialog1.show();
            }else
                where++;
        }
    }


    /**
     * 自定义对话框
     */
    private void customDialog() {
        final Dialog dialog = new Dialog(this, NormalDialogStyle);
        View view = View.inflate(this, R.layout.layout, null);
//        TextView cancel = (TextView) view.findViewById(R.id.cancel);
//        TextView confirm = (TextView) view.findViewById(R.id.confirm);
        // 设置自定义的布局
        dialog.setContentView(view);
        //使得点击对话框外部不消失对话框
        dialog.setCanceledOnTouchOutside(true);
        //设置对话框的大小
        view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(this).getScreenHeight() * 0.23f));
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenSizeUtils.getInstance(this).getScreenWidth() * 0.75f);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;
        dialogWindow.setAttributes(lp);
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        dialog.show();
    }
}
