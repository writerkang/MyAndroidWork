package com.lec.a008_practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    PersonAdapter adapter;
    RecyclerView rv;

    Button addBtn;
    EditText etName, etAge, etAdr;

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
                adapter.addItem(0, new Person(etName.getText().toString(), Integer.parseInt(etAge.getText().toString()), etAdr.getText().toString()));
                adapter.notifyDataSetChanged();
            }
        });

        rv = findViewById(R.id.rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);

        adapter = new PersonAdapter();
        initAdapter(adapter);

        rv.setAdapter(adapter);

    } // end onCreate()

    protected void initAdapter(PersonAdapter adapter) {

    }
}
