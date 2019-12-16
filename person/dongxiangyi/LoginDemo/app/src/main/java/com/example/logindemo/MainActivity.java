package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    
    private EditText ed_number;
    private EditText ed_password;
    private Button btn_login;
    private Button btn_register;
    private TextView tv_back;
    private static Handler handler = new Handler();

    private CustomeClickListener listener;
    private OkHttpClient okHttpClient;
    private String path = "10.7.89.216";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        registerListener();

        okHttpClient = new OkHttpClient();

    }

    private void registerListener() {
        listener = new CustomeClickListener();
        btn_login.setOnClickListener(listener);
        btn_register.setOnClickListener(listener);
    }

    private void getViews() {
        ed_number = findViewById(R.id.ed_number);
        ed_password = findViewById(R.id.ed_password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        tv_back = findViewById(R.id.tv_back);
    }

    class CustomeClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_login:
                    new Thread(){
                        @Override
                        public void run() {
                            logintest();
                        }
                    }.start();
            }
        }
    }

    private void logintest(){
        String number = ed_number.getText().toString();
        String pwd = ed_password.getText().toString();
        FormBody body = new FormBody.Builder()
                .add("name", "lww")
                .add("pwd", "1233")
                .build();

        Request request = new Request.Builder()
                .url("http://"+path+":8080/LoginDemo/LoginServlet")
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("dxy", "登陆失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.i("dxy", result);
            }
        });
    }
}
