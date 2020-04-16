package com.lec.android.a005_image;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {
    ImageView iv1, iv2, iv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);

        // 방법1. 프로젝트 내의 res/drawable 폴더에 있는 파일을 보여주는 방법
        iv1.setImageResource(R.drawable.a2);

        // 방법2. Drawable 객체를 이용해서 보여주는 방법
//        Drawable drawable = getResources().getDrawable(R.drawable.a3);// deprecated 됨.
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.a3);
        // ContextCompat : API23부터 도입

        iv2.setImageDrawable(drawable);

        // 방법3 bitmap 객체를 이용해서 보여주는 방법
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.a4);
        iv3.setImageBitmap(bm);
    }
}
