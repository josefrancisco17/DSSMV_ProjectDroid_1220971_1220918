package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button adminLogOutButton = (Button) findViewById(R.id.adminLogOutButton);
        adminLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        Button adminCreateLibraryButton = (Button) findViewById(R.id.adminCreateLibraryButton);

        adminCreateLibraryButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               Intent i = new Intent(AdminActivity.this, MakeLibraryActivity.class);
               startActivity(i);
           }
        });

        Button adminUpdateLibraryButton = (Button) findViewById(R.id.adminUpdateLibraryButton);

        adminUpdateLibraryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, UpdateLibraryActivity.class);
                startActivity(i);
            }
        });

        Button adminDeleteLibraryButton = (Button) findViewById(R.id.adminDeleteLibraryButton);

        adminDeleteLibraryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, DeleteLibraryActivity.class);
                startActivity(i);
            }
        });

        Button adminCreateBookButton = (Button) findViewById(R.id.adminCreateBookButton);

        adminCreateBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        Button adminUpdateBookButton = (Button) findViewById(R.id.adminUpdateBookButton);

        adminUpdateBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }


}