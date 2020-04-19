package com.lec.a008_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PersonDetail extends AppCompatActivity {
    EditText detName, detAge, detAdr;
    Button dbackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        detName = findViewById(R.id.detName);
        detAge = findViewById(R.id.detAge);
        detAdr = findViewById(R.id.detAdr);
        dbackBtn = findViewById(R.id.dbackBtn);

        final Intent intent = getIntent();
        Person person = (Person) intent.getSerializableExtra("person");
        final int position = intent.getIntExtra("position", 0);

        detName.setText(person.getName());
        detAge.setText(person.getAge() + "");
        detAdr.setText(person.getAdr());

        dbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = detName.getText().toString();
                String age = detAge.getText().toString();
                String adr = detAdr.getText().toString();


                if(!name.equals("") && !age.equals("") && !adr.equals("")) {

                    intent.putExtra("name", name);
                    intent.putExtra("age", Integer.parseInt(age));
                    intent.putExtra("adr", adr);
                    intent.putExtra("position", position);


                    setResult(RESULT_OK, intent);

                    finish();
                } else {
                    Toast.makeText(PersonDetail.this, "공란이 있습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
