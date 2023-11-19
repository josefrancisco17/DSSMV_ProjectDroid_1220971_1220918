package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.models.Book;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;

public class LibraryActivity extends AppCompatActivity {
    private Library library;
    private String selectedLibraryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        Intent intent = getIntent();
        selectedLibraryId = intent.getStringExtra("selectedLibraryId");

        getLibraryFromWs();
    }

    private void getLibraryFromWs() {
        new Thread(() -> {
            Library selectedLibrary = RequestsService.getLibrary(LibraryActivity.this, selectedLibraryId);
            if (selectedLibrary  == null){
                return;
            };
            library = selectedLibrary;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView libraryName = findViewById(R.id.libraryName);
                    TextView  libraryAddress = findViewById(R.id.libraryAddress);
                    TextView libraryOpenTime = findViewById(R.id.libraryOpenTime);
                    TextView libraryCloseTime = findViewById(R.id.libraryCloseTime);
                    TextView libraryOpenDays = findViewById(R.id.libraryOpenDays);
                    TextView libraryOpenStatement = findViewById(R.id.libraryOpenStatement);
                    TextView libraryOpen = findViewById(R.id.libraryOpen);

                    libraryName.setText(library.getName());
                    libraryAddress.setText(library.getAddress());
                    libraryOpenTime.setText(library.getOpenTime());
                    libraryCloseTime.setText(library.getCloseTime());
                    libraryOpenDays.setText(library.getOpenDays());
                    libraryOpenStatement.setText(library.getOpenStatement());
                    libraryOpen.setText(library.isOpen());
                }
            });
        }).start();
    }
    public void backMainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}