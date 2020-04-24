package com.lec.android.a019_graphic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main); //XML 레이아웃 사용하지 않고

        //사용자가 작성한 View로 액티비티 레이아웃 세팅
        MyView v = new MyView(MainActivity.this);
        setContentView(v);
    }
}

class MyView extends View {

    public MyView(Context context) {
        super(context);
    }

    //화면이 업데이트 될때, '그려주기'
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(); //화면에 그려줄 도구 세팅
        paint.setColor(Color.RED);

        setBackgroundColor(Color.GREEN);

        //사각형의 좌상, 우하 좌표
        canvas.drawRect(100, 100, 200, 200, paint);

        //원의 중심 x, y, 반지름
        canvas.drawCircle(300, 300, 40, paint);

        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(10f); //선의 굵기
        //직선
        canvas.drawLine(400, 100, 800, 150, paint);

        //Path 자취(?) 만들기
        Path path = new Path(); // android.graphic.Path
        path.moveTo(20, 100); //자취 이동
        path.lineTo(100, 200); //직선
        path.cubicTo(150, 40, 200, 300, 300, 200); //베지어 곡선
        paint.setColor(Color.CYAN);
        canvas.drawPath(path, paint);


    }
}
