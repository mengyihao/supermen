package com.example.dreamdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ServiceActivity extends AppCompatActivity {
    private Button btn_name;
    private Button btn_phone;

    private CustomeClickListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        MyApplication.getApp().addActivity(this);
        getViews();
        registerListeners();
    }

    private void registerListeners() {
        listener = new CustomeClickListener();
        btn_phone.setOnClickListener(listener);
        btn_name.setOnClickListener(listener);
    }

    private void getViews() {
        btn_name = findViewById(R.id.btn_name);
        btn_phone = findViewById(R.id.btn_phone);
    }

    class CustomeClickListener implements View.OnClickListener {
        Intent intent = new Intent();
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_name:
                    finish();
                    break;
                case R.id.btn_phone:
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:18531268991"));
                    startActivity(intent);
                    break;
            }
        }
    }
}