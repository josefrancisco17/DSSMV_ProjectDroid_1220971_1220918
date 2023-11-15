package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.ListViewAdapterLibrary;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.ListViewAdapterReviews;
import com.example.dssmv_projectdroid_1220971_1220918.models.Checkout;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.models.Review;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;

import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

    private ListView lv;
    private List<Review> reviewsList;
    private ListViewAdapterReviews adapter;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private String selectedLibraryBookIsbn;
    private String selectedLibraryId;
    private String selectedLibraryName;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        Intent intent = getIntent();
        selectedLibraryBookIsbn = intent.getStringExtra("selectedLibraryBookIsbn");
        selectedLibraryId = intent.getStringExtra("selectedLibraryId");
        selectedLibraryName = intent.getStringExtra("selectedLibraryName");

        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        userName = preferences.getString("userName", "defaultValue");

        reviewsList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewReviews);

        getReviewsFromWs();

        adapter = new ListViewAdapterReviews(ReviewsActivity.this, reviewsList);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);

        Button reviewButton = (Button) findViewById(R.id.createReviewButton);
        reviewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReviewsActivity.this, MakeReviewActivity.class);
                i.putExtra("selectedLibraryBookIsbn", selectedLibraryBookIsbn);

                for (Review review : reviewsList) {
                    if(review != null && review.getReviewer().equals(userName)) {
                        i.putExtra("reviewId", review.getId());
                        break;
                    } else {
                        i.putExtra("reviewId", "null");
                    }
                }
                startActivity(i);
            }
        });
    }

    private void getReviewsFromWs() {
        new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                List<Review> reviews = RequestsService.getReviewsList(ReviewsActivity.this, selectedLibraryBookIsbn);
                reviewsList.clear();
                if (reviews == null){
                    return;
                }
                reviewsList.addAll(reviews);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }

    public void backMainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}