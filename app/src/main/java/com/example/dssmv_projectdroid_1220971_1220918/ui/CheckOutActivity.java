package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.handler.JsonHandler;
import com.example.dssmv_projectdroid_1220971_1220918.models.Book;
import com.example.dssmv_projectdroid_1220971_1220918.models.Checkout;
import com.example.dssmv_projectdroid_1220971_1220918.models.LibraryBook;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;
import com.squareup.picasso.Picasso;
import android.widget.TextView;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

public class CheckOutActivity extends AppCompatActivity {
    private String selectedLibraryBookIsbn;
    private String selectedLibraryId;
    private String selectedLibraryName;
    private LibraryBook libraryBook;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Intent intent = getIntent();
        selectedLibraryBookIsbn = intent.getStringExtra("selectedLibraryBookIsbn");
        selectedLibraryId = intent.getStringExtra("selectedLibraryId");
        selectedLibraryName = intent.getStringExtra("selectedLibraryName");

        Button submitButton = (Button) findViewById(R.id.submitcheckoutButton);
        submitButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               checkOutBook();
               postCheckOutBooktoWs(CheckOutActivity.this, selectedLibraryId, selectedLibraryBookIsbn, userName);
           }
        });
    }

    public void checkOutBook() {
        userName = ((TextView) findViewById(R.id.textinputName)).getText().toString();
        if (userName.isEmpty()) {
            userName = "Wonderful User";
        }
    }

    private void postCheckOutBooktoWs(Activity activity, String libraryId, String bookIsbn, String userName) {
        new Thread(() -> {
            RequestsService.postCheckOutBook(activity, libraryId, bookIsbn, userName);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity,"Successfully CheckOut",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CheckOutActivity.this, MainActivity.class);
                    startActivity(i);
                }
            });
        }).start();
    }

    public void backMainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}