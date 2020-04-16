package com.lec.android.a004_widget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    CheckBox cb1, cb2, cb3, cb4;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        cb1 = findViewById(R.id.cd1);
        cb2 = findViewById(R.id.cd2);
        cb3 = findViewById(R.id.cd3);
        cb4 = findViewById(R.id.cd4);
        tvResult = findViewById(R.id.tvResult);

        Button btnComplete = findViewById(R.id.btnComplete);

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                if(cb1.isChecked()) result += cb1.getText();
                if(cb2.isChecked()) result += cb2.getText();
                if(cb3.isChecked()) result += cb3.getText();
                if(cb4.isChecked()) result += cb4.getText();
                tvResult.setText("선택결과: " + result);
            }
        });

        //버튼을 클릭하면 체크된내용들만 결과 출력하기
        cb1.setOnCheckedChangeListener(new CbListener());
        cb2.setOnCheckedChangeListener(new CbListener());
        cb3.setOnCheckedChangeListener(new CbListener());
        cb4.setOnCheckedChangeListener(new CbListener());
    }

    class CbListener implements CompoundButton.OnCheckedChangeListener{

        // CheckBox의 '상태'가 변할때마다 호출되는 메소드
        // isChecked: true <- 체크된 상태, false <- uncheck 상태
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int count = 0;
            if(cb1.isChecked()) count++;
            if(cb2.isChecked()) count++;
            if(cb3.isChecked()) count++;
            if(cb4.isChecked()) count++;
            tvResult.setText(count + "개 선택");
        }
    }
}
