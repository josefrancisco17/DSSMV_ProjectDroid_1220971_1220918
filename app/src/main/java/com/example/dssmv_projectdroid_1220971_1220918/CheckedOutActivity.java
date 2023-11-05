package com.example.dssmv_projectdroid_1220971_1220918;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class CheckedOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_out);
    }

    public void backMainActivity(View v) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}