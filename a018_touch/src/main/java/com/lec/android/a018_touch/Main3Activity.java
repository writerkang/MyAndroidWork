package com.lec.android.a018_touch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    TextView tvResult;

    //3개까지의 멀티터치를 다루기 위한 배열
    int id [] = new int[3];
    int x [] = new int[3];
    int y [] = new int[3];
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tvResult = findViewById(R.id.tvResult);
    }

    //액티비티에서 발생한 터치이벤트 처리
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int pointCount = event.getPointerCount();//현재터치 발생한 포인트 개수를 얻어온다
        if(pointCount > 3) pointCount = 3; //4개 이상의 포인트 터치했더라도 3개까지만 처리

        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN: //한개 포인트에 DOWN이 발생했을때

                break;
            case MotionEvent.ACTION_POINTER_DOWN: //두개 이상의 포인트에 대한 DOWN이 발생했을때

                break;
            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:

                break;
        }

        return super.onTouchEvent(event);
    }
}
