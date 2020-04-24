package com.lec.android.a019_graphic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
        setContentView(new MyView2(Main2Activity.this));
    }

}

class MyView2 extends View {

    int x = 100, y = 100;

    public MyView2(Context context) {
        super(context);
    }

    //화면이 업데이트 될때, '그려주기'
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(); //화면에 그려줄 도구 세팅
        paint.setColor(Color.RED);
        paint.setTextSize(40);

        canvas.drawRect(x-100, y-100, x+100, y+100, paint);

        paint.setColor(Color.BLUE);
        paint.setTextSize(80);
        canvas.drawText("글씨", 300, 300, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                x = (int) event.getX();
                y = (int) event.getY();
                invalidate(); //화면을 다시 그려주기 --> onDraw() 호출함

        }

        return true;
    }
}