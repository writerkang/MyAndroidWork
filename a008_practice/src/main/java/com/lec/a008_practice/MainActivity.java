package com.lec.a008_practice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    PersonAdapter adapter;
    RecyclerView rv;

    Button addBtn;
    EditText etName, etAge, etAdr;

    final static int REQUEST_CODE_MODIFY = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.addBtn);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etAdr = findViewById(R.id.etAdr);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String adr = etAdr.getText().toString();
                if(!name.equals("") && !age.equals("") && !adr.equals("")) {

                        adapter.addItem(0, new Person(name, Integer.parseInt(age), adr));
                        adapter.notifyDataSetChanged();
                        etName.setText("");
                        etAge.setText("");
                        etAdr.setText("");

                } else {
                    Toast.makeText(MainActivity.this, "공란이 있습니다", Toast.LENGTH_SHORT).show();
                }




            }
        });

        rv = findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);

        adapter = new PersonAdapter();

        rv.setAdapter(adapter);

    } // end onCreate()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) { // 정상 반환인 경우

            switch (requestCode){
                case REQUEST_CODE_MODIFY:
                    String name = data.getStringExtra("name");
                    int age = data.getIntExtra("age", 0);
                    String adr = data.getStringExtra("adr");
                    int position = data.getIntExtra("position", 0);

                    adapter.setItem(position, new Person(name, age, adr));
                    adapter.notifyDataSetChanged();
                    break;
            }

        } else{
            // 정상 결과가 아닌 경우 처리
        }
    }
}
