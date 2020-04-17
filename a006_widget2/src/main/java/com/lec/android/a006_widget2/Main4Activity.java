package com.lec.android.a006_widget2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {
    RatingBar rb;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        rb = findViewById(R.id.ratingBar);
        tvResult = findViewById(R.id.tvResult);

        // RatingBar의 값이 변할때 호출되는 콜백
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvResult.setText("rating: " + rating);
            }
        });
    }
}
