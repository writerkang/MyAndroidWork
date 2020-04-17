package com.lec.android.a006_widget2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    TextView tvResult;
    SeekBar seekBar;

    int value = 0;
    int add = 2;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tvResult = findViewById(R.id.tvResult);
        seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //값의 변화가 생겼을때 콜백
            //progress: 진행값 0~max
            //fromUser: 사용자에의한 진행값 변화인 경우 true
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvResult.setText("onProgressChanged: " + progress + "(" + fromUser + ")");

            }

            //tracking 시작할때 콜백
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "트래킹시작", Toast.LENGTH_SHORT).show();

            }

            //tracking 끝날때 콜백
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "트래킹종료", Toast.LENGTH_SHORT).show();

            }
        });
        // 앱 시작시 Thread... SeekBar 증가 시키기
        new Thread(new Runnable() {
            @Override
            public void run() {
                int max = seekBar.getMax();
                while (true){
                    value = seekBar.getProgress() + add;

                    if (value > max || value < 0) {
                        add = -add;
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setProgress(value);
                        }
                    });


                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }
}
