package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.models.Book;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;

public class CheckOutActivity extends AppCompatActivity {
    private String selectedLibraryBookIsbn;
    private String selectedLibraryId;
    private String selectedLibraryName;
    private Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Intent intent = getIntent();
        selectedLibraryBookIsbn = intent.getStringExtra("selectedLibraryBookIsbn");
        selectedLibraryId = intent.getStringExtra("selectedLibraryId");
        selectedLibraryName = intent.getStringExtra("selectedLibraryName");

        book = RequestsService.getBook(this, selectedLibraryBookIsbn);
    }

    public void backMainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}