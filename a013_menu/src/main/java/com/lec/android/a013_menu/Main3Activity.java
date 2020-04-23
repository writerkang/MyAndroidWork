package com.lec.android.a013_menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//ActionBar = 타이틀바 + 옵션메뉴
public class Main3Activity extends AppCompatActivity {

    ActionBar abar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        abar = getSupportActionBar(); //ActionBar 객체 가져오기

//        abar.hide(); //액션바 감추기
//        abar.show(); //액션바 보이기

        Button btnActionIcon = findViewById(R.id.btnActionIcon);
        Button btnActionTitle = findViewById(R.id.btnActionTitle);

        btnActionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abar.setLogo(R.drawable.home); //아이콘바꾸기
                abar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);
            }
        });
        /*
   setDisplayOptions 의 디스플레이 옵션 상수
   DISPLAY_SHOW_HOME : 홈아이콘 표시
   DISPLAY_USE_LOGO : 홈아이콘 부분에 로고 아이콘 사용
   DISPLAY_HOME_AS_UP : 홈아이콘에 뒤로가기 모향의 < 아이콘 같이 표시
   DISPLAY_SHOW_TITLE : 타이틀을 표시하도록 함
*/

        btnActionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main3, menu);

        //액션바의 EditText로 입력 가능
        View v = menu.findItem(R.id.menu_search).getActionView();

        if(v != null){
            EditText editText = v.findViewById(R.id.editText);

            if(editText != null){
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        Toast.makeText(getApplicationContext(), v.getText() + ":입력", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        showInfo(item);

        return super.onOptionsItemSelected(item);
    }

    public void showInfo(MenuItem item){
        int id = item.getItemId();   // 옵션메뉴 아이템의 id 값
        String title = item.getTitle().toString();   // 옵션 메뉴의 title
        int groupId = item.getGroupId();   // 옵션 메뉴의 그룹아이디
        int order = item.getOrder();

        String msg = "id:" + id + " title:" + title + " groupid:" + groupId + " order:" + order;
        Log.d("myapp", msg);
        Toast.makeText(getApplicationContext(), title + " 메뉴 클릭", Toast.LENGTH_SHORT).show();
    }

}
