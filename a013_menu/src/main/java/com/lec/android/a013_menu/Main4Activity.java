package com.lec.android.a013_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
  액션바 (Action Bar)
  사용자에게 친숙한 인터페이스 제공
  안드로이드 3.0 에서 추가,  (3.0 이전버젼은 Support Library 로 가능)

  [기능]
  - 앱 로고 / 앱에서 사용자의 현재 위치
  - 탭이나 드롭다운 메뉴 제공
  - 사용자 예측 가능한 방식으로 가능한 작업 제공(ex: 검색, 만들기, 공유..)

  [구성]
  - 앱아이콘 : 로고 혹은 다른 이미지
  - 뷰컨트롤 (View Control) : 뷰 전환 가능 영역
  - 액션버튼 (Action Button)
  - 액션오버플로우 (Action Overflow) :  액션바 공간이 부족하면 자동적으로 오버플로우 영역으로 이동.

  ※ 참고
  액션바에 많이 사용되는 아이콘은 구글 개발자 페이지에서 무료 제공
  https://material.io/resources/icons
*/

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        final Button btnChild1 = findViewById(R.id.btnChild1);
        btnChild1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChildActivity1.class);
                startActivity(intent);
            }
        });
    }
}
