package com.lec.android.a004_widget;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    EditText op1, op2;
    Button btnPlus, btnMinus, btnMul, btnDiv;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);
        tvResult = findViewById(R.id.tvResult);

        op1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    v.setBackgroundColor(Color.YELLOW);
                }else{
                    v.setBackgroundColor(Color.parseColor("#00000000"));
                }
            }
        });

        op2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    v.setBackgroundColor(Color.YELLOW);
                }else{
                    v.setBackgroundColor(Color.parseColor("#00000000"));
                }
            }
        });


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double cal1 = Double.valueOf(op1.getText().toString());
                double cal2 = Double.valueOf(op2.getText().toString());
                tvResult.setText((cal1 + cal2) + "");
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double cal1 = Double.valueOf(op1.getText().toString());
                double cal2 = Double.valueOf(op2.getText().toString());
                tvResult.setText((cal1 - cal2) + "");
            }
        });

        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double cal1 = Double.valueOf(op1.getText().toString());
                double cal2 = Double.valueOf(op2.getText().toString());
                tvResult.setText((cal1 * cal2) + "");
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double cal1 = Double.valueOf(op1.getText().toString());
                double cal2 = Double.valueOf(op2.getText().toString());
                tvResult.setText((cal1 / cal2) + "");
            }
        });

    }
}
