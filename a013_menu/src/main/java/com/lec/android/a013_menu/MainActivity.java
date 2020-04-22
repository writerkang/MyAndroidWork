package com.lec.android.a013_menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

/*  안드로이드의 메뉴
   1. Option Menu :  '메뉴' 버튼을 눌렀을때 나타나는 메뉴
                   각각의 '화면' (액티비티) 마다 설정
   2. Context Menu :  길게 눌렀을때 나타나는 메뉴
                   각각의 '뷰' 마다 설정  (화면도 가능)

*/

public class MainActivity extends AppCompatActivity {

    boolean blog = false; //로그인상태
    LinearLayout ll;

    static final int MENUITEM_YELLOW = 1;
    static final int MENUITEM_ORANGE = 2;
    static final int MENUITEM_CYAN = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll = findViewById(R.id.ll);
    }

    // onCreateOptionsMenu()
// '옵션메뉴'버튼이 '처음' 눌러졌을 때 실행되는 메소드
// 메뉴버튼을 눌렀을 때 보여줄 menu 에 대해서 정의

    // onPrepareOptionsMenu()
// '옵션메뉴'가 화면에 보여질때마다 호출되는 메소드


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main1, menu); //menu 리소스(xml)로부터 menu생성
        Log.d("myapp", "onCreateOptionsMenu - 최초 메뉴키를 눌렀을때 호출됨");

        //MenuItem을 동적으로 추가 가능하다!
        //add(), MenuItem 리턴
        MenuItem item1 = menu.add(1, MENUITEM_YELLOW, 100, "노랑");
        MenuItem item2 = menu.add(1, MENUITEM_ORANGE, 100, "오렌지");
        MenuItem item3 = menu.add(1, MENUITEM_CYAN, 100, "파랑");

        return true; //false 리턴하면 메뉴는 보여지지 않는다
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("myapp", "onPrepareOptionMenu - 옵션메뉴가 화면에보여질때마다 호출됨");



        //메뉴가보여질때마다 '로그인', '로그아웃'이 enable/disable 토글되어 보이기

        if(blog){ //로그인 상태
            //getItem(index) index번째 MenuItem 객체 리턴
            menu.getItem(0).setEnabled(true);
            menu.getItem(1).setEnabled(false);
        } else{ //로그아웃 상태
            menu.getItem(0).setEnabled(false);
            menu.getItem(1).setEnabled(true);
        }
        blog = !blog; //값을 바꿈


        return super.onPrepareOptionsMenu(menu); //false 리턴하면메뉴 보여지지 않음
    }

    // onOptionsItemSelected()
    // '옵션메뉴의 아이템'이 선택(클릭) 되었을때 호출되는 메소드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("myapp", "onOptionItemSelected - 메뉴항목을 클릭했을때 호출됨");

        showInfo(item);

        //특정 MenuItem에 대한 동작
        //각 색상이 눌렸을때
        switch (item.getItemId()){
            case MENUITEM_YELLOW:
                ll.setBackgroundResource(R.color.bgColorYellow);
                break;
            case MENUITEM_ORANGE:
                ll.setBackgroundResource(R.color.bgColorOrange);
                break;
            case MENUITEM_CYAN:
                ll.setBackgroundResource(R.color.bgColorCyan);
                break;
        }



        return super.onOptionsItemSelected(item);
    }
    public void showInfo(MenuItem item){
        int id = item.getItemId(); //옵션메뉴아이템의 id값(정수)
        String title = item.getTitle().toString(); //옵션 메뉴아이템의 title 값
        int groupId = item.getGroupId(); //옵션메뉴 아이템의 그룹id값(정수)
        int order = item.getOrder(); //메뉴아이템의 순번

        String msg = "id:" + id + " title:" + title + " groupId:" + groupId + " order:" + order;
        Log.d("myapp", msg);
        Toast.makeText(this, title + "메뉴 클릭", Toast.LENGTH_SHORT).show();

    }

}
