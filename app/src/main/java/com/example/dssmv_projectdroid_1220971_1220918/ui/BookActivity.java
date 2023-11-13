package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.widget.ImageViewCompat;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.models.Book;
import com.example.dssmv_projectdroid_1220971_1220918.models.LibraryBook;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookActivity extends AppCompatActivity {

    private String selectedLibraryBookIsbn;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Intent intent = getIntent();
        selectedLibraryBookIsbn = intent.getStringExtra("selectedLibraryBookIsbn");

        book = RequestsService.getBook(this, selectedLibraryBookIsbn);
        getBooksFromWs();
    }

    private void getBooksFromWs() {
        new Thread(() -> {
            Book selectedBook = RequestsService.getBook(BookActivity.this, selectedLibraryBookIsbn);
            if (selectedBook  == null){
                return;
            };
            book = selectedBook;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView titleBook = findViewById(R.id.titleBook);
                    TextView statementBookmarklet = findViewById(R.id.byStatementBook);
                    TextView publishDateBook = findViewById(R.id.publishDateBook);
                    TextView numberOfPagesBook = findViewById(R.id.numberofPagesBook);
                    TextView descriptionBook = findViewById(R.id.descriptionBook);
                    ImageView coverBook = findViewById(R.id.coverBook);
                    titleBook.setText(book.getTitle());
                    statementBookmarklet.setText(book.getByStatement());
                    publishDateBook.setText("Publish Date: " + book.getPublishDate());
                    numberOfPagesBook.setText("Number of Pages " + book.getNumberOfPages());
                    descriptionBook.setText(book.getDescription());
                    String extractedString = book.getCover().getLargeUrl().substring(book.getCover().getLargeUrl().indexOf("/api/v1/") + "/api/v1/".length());
                    Picasso.get().load("http://193.136.62.24/v1/" + extractedString).into(coverBook);
                }
            });
        }).start();
    }

    public void backMainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}