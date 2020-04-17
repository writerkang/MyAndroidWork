package com.lec.android.a006_widget2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    TextView tvResult;
    Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvResult = findViewById(R.id.tvResult);
        spinner1 = findViewById(R.id.spinner1);

        //아이템을 선택했을때 호출되는 콜백
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //아이템을 선택했을때
            //position: 몇번째 item인지, 0~
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvResult.setText("position: " + position + parent.getItemAtPosition(position));
                Toast.makeText(getApplicationContext(), (String)parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }

            // 선택한거 없이 화면에서 사라질때
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "선택 안했어요", Toast.LENGTH_LONG).show();
            }
        });
    }
}
