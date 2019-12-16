package com.example.dreamdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Bullet extends View {
    private int x;
    private int y;
    private int radius;
    private Paint mPaint;
    public Bullet(Context context) {
        super(context);
        init();
    }

    public Bullet(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Bullet(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);          //抗锯齿
        mPaint.setColor(getResources().getColor(R.color.black));//画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //画笔风格
        mPaint.setTextSize(36);             //绘制文字大小，单位px
        mPaint.setStrokeWidth(5);           //画笔粗细
    }

    @Override
    public  void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.white));
        canvas.drawCircle(80, 60, 30, mPaint);
    }

}

