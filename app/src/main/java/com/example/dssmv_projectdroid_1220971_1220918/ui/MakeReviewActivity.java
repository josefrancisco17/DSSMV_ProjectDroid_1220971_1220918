package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;

public class MakeReviewActivity extends AppCompatActivity {
    private String selectedLibraryBookIsbn;
    private String userName;

    private String reviewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_review);

        Intent intent = getIntent();
        selectedLibraryBookIsbn = intent.getStringExtra("selectedLibraryBookIsbn");
        reviewId = intent.getStringExtra("reviewId");

        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        userName = preferences.getString("userName", "defaultValue");


        Button submitReviewButton = (Button) findViewById(R.id.buttonSubmitReview);
        submitReviewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextView reviewText = (TextView) findViewById(R.id.textInputReview);
                Switch recommended = (Switch) findViewById(R.id.switchRecommended);
                if (reviewId.equals("null")) {
                    postReviewtoWs(MakeReviewActivity.this, selectedLibraryBookIsbn, userName,reviewText.getText().toString(),recommended.isChecked());
                } else {
                    updateReviewToWs(MakeReviewActivity.this, selectedLibraryBookIsbn, userName, reviewText.getText().toString(),recommended.isChecked(), reviewId);
                }
            }
        });
    }

    private void postReviewtoWs(Activity activity, String bookIsbn, String userName, String reviewText, boolean recommended) {
        new Thread(() -> {
            RequestsService.postReviewBook(activity, bookIsbn, userName, reviewText, recommended);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MakeReviewActivity.this, ReviewsActivity.class);
                    i.putExtra("selectedLibraryBookIsbn", bookIsbn);
                    startActivity(i);
                }
            });
        }).start();
    }

    private void updateReviewToWs(Activity activity, String bookIsbn, String userName, String reviewText, boolean recommended, String reviewId) {
        new Thread(() -> {
            RequestsService.updateReviewBook(activity, bookIsbn, userName, reviewText, recommended, reviewId);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MakeReviewActivity.this, ReviewsActivity.class);
                    i.putExtra("selectedLibraryBookIsbn", bookIsbn);
                    startActivity(i);
                }
            });
        }).start();
    }

    public void backBookReviews(View v) {
        Intent i = new Intent(MakeReviewActivity.this, ReviewsActivity.class);
        i.putExtra("selectedLibraryBookIsbn", selectedLibraryBookIsbn);
        startActivity(i);
    }


}