package com.lec.android.practice_kang1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);

        try {
            InputStream in = getResources().openRawResource(R.raw.abc);
            InputStreamReader stream = new InputStreamReader(in, "utf-8");
            BufferedReader br = new BufferedReader(stream);

            String read;
            StringBuilder sb = new StringBuilder("");

            while((read = br.readLine()) != null){
                sb.append(read + "\n");
            }
            tv1.setText(sb);
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
