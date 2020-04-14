package com.lec.android.a003_widget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.graphics.Color.YELLOW;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvResult;
    EditText et;


    // onCreate()
    // 액티비티가 생성될때 호출되는 메소드
    // 액티비티 초기화 하는 코드 작성
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);

        tvResult = findViewById(R.id.tvResult);
        et = findViewById(R.id.et);
        final LinearLayout ll = findViewById(R.id.ll);

        //방법2: 익명클래스(anonymous class) 사용
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //클릭되었을때 호출디ㅗ는 메소드(콜백 메소드 callback metod)
                Log.d("myapp","버튼2가 클릭 되었습니다");
                tvResult.setText("버튼2가 클릭됨");
                tvResult.setBackgroundColor(YELLOW);
            }
        });

        //다양한 이벤트, 다양한 리스너 등록 가능
        btn2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) { // 롱클릭 발생시 수행하는 콜백 메소드
                Log.d("myapp", "버튼2가 롱클릭 되었습니다.");
                tvResult.setText("버튼2가 롱클릭 되었습니다.");
                tvResult.setBackgroundColor(Color.CYAN);
//                return false;  // false 리턴하면 이벤트가 click까지 간다.
                return true; // true 리턴하면 이벤트가 Long click으로 소멸(consume)된다.
            }
        });

        // 방법3: Lamda - expression 사용하기
        // AndroidStudio의 세팅 필요! ppt 참조!
        btn3.setOnClickListener((v) -> { //onClick(View v)
            Log.d("myapp", "버튼3가 클릭되었다");
            tvResult.setText("버튼3 클릭됨");
            ll.setBackgroundColor(Color.rgb(164, 198, 57));
        });

        //방법4: implement한 클래스 사용
        Button btnA = findViewById(R.id.btnA);
        Button btnB = findViewById(R.id.btnB);
        Button btnC = findViewById(R.id.btnC);
        Button btnD = findViewById(R.id.btnD);
        Button btnE = findViewById(R.id.btnE);
        Button btnF = findViewById(R.id.btnF);

        class MyListener implements View.OnClickListener{

            String name;

            public MyListener(String name) {this.name = name;}

            @Override
            public void onClick(View v) {
                String tag = (String)v.getTag();
                String text = (String)((Button)v).getText(); // getText()는 CharSequence 객체 리턴
                String msg = String.format("%s 버튼 %s이 클릭[%s]", name, text, tag);
                Log.d("myapp",msg);
                tvResult.setText(msg);
                et.setText(et.getText().append(name));
            }
        }
        btnA.setOnClickListener(new MyListener("안녕1"));
        btnB.setOnClickListener(new MyListener("안녕2"));
        btnC.setOnClickListener(new MyListener("안녕3"));
        btnD.setOnClickListener(new MyListener("안녕4"));
        btnE.setOnClickListener(new MyListener("안녕5"));
        btnF.setOnClickListener(new MyListener("안녕6"));

        //방법5: 액티비티가 implement
        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        //연습
        //+, - 버튼 누르면 tvResult의 글씨가 점점 커지고/작아지게 하기
        //getTextSize(): float값 리턴
        //TODO

        Button btnInc = findViewById(R.id.btnInc);
        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float size = tvResult.getTextSize();
                Log.d("myapp","글꼴사이즈: " + size);
                tvResult.setTextSize(0,size + 5);
            }
        });

        Button btnDec = findViewById(R.id.btnDec);
        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float size = tvResult.getTextSize();
                Log.d("myapp","글꼴사이즈: " + size);
                tvResult.setTextSize(0,size - 5);
            }
        });

    }

    //방법1:레이아웃 xml의 onXXX 속성으로 지정
    public void changeText(View v) {
        Log.d("myapp", "버튼 1이 클릭되었습니다");
        tvResult.setText("버튼 1이 클릭 되었습니다.");

    }

    //방법5: 액티비티가 implement 한 것을 사용
    @Override
    public void onClick(View v) {
        Log.d("myapp","Clear버튼이 클릭되었습니다");
        tvResult.setText("Clear버튼이 클릭되었씁니다");
        et.setText("");

    }
}
