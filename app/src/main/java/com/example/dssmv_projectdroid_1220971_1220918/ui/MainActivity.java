package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchLibrariesActivity(View v) {
        Intent i = new Intent(this, LibrariesActivity.class);
        startActivity(i);
    }

    public void launchReviewsActivity(View v) {
        Intent i = new Intent(this, ReviewsActivity.class);
        startActivity(i);
    }

    public void launchCheckedOutActivity(View v) {
        Intent i = new Intent(this, CheckedOutActivity.class);
        startActivity(i);
    }

    public void launchHistoryActivity(View v) {
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
    }

    public void launchSettingsActivity(View v) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }
}