package com.lec.android.a010_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;


//작은데이터들(세팅값) 이용할때 좋음
//SharedPreferences
public class Main5Activity extends AppCompatActivity {

    EditText etInput;
    String sfName = "myFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        etInput = findViewById(R.id.etInput);

        //저장되어 있던값을 꺼내서 보여주기
        SharedPreferences sf = getSharedPreferences(sfName, MODE_PRIVATE);
        String str = sf.getString("name", "");
        String xx = sf.getString("xx", "ABC");
        String yy = sf.getString("yy", "XYZ");

        etInput.setText(str);
        Log.d("myapp", str + " - " + xx + " - " + yy);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Activity가 종료되기 전에 저장
        SharedPreferences sf = getSharedPreferences(sfName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit(); //저장하려면 Editor 객체필요

        String str = etInput.getText().toString(); //사용자가 입력한값

        editor.putString("name", str);
        editor.putString("xx", "가나다");
        editor.commit(); //파일에 최종 반영함

    }
}
