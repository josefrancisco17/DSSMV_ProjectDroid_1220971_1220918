package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.models.Book;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;
import com.squareup.picasso.Picasso;

public class BookActivity extends AppCompatActivity {

    private String selectedLibraryBookIsbn;
    private String selectedLibraryId;
    private String selectedLibraryName;
    private Book book;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        userName = preferences.getString("userName", "defaultValue");

        Intent intent = getIntent();
        selectedLibraryBookIsbn = intent.getStringExtra("selectedLibraryBookIsbn");
        selectedLibraryId = intent.getStringExtra("selectedLibraryId");
        selectedLibraryName = intent.getStringExtra("selectedLibraryName");

        getBookFromWs();

        Button checkOutButton = (Button) findViewById(R.id.checkOutButton);
        checkOutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                postCheckOutBooktoWs(BookActivity.this, selectedLibraryId, selectedLibraryBookIsbn, userName);
            }
        });

        Button reviewButton = (Button) findViewById(R.id.reviewButton);
        reviewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookActivity.this, ReviewsActivity.class);
                i.putExtra("selectedLibraryBookIsbn", selectedLibraryBookIsbn);
                i.putExtra("selectedLibraryId", selectedLibraryId);
                i.putExtra("selectedLibraryName", selectedLibraryName);
                startActivity(i);
            }
        });

    }

    private void getBookFromWs() {
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
                    TextView nameLibrary = findViewById(R.id.libraryBook);
                    TextView statementBookmarklet = findViewById(R.id.byStatementBook);
                    TextView publishDateBook = findViewById(R.id.publishDateBook);
                    TextView numberOfPagesBook = findViewById(R.id.numberofPagesBook);
                    TextView descriptionBook = findViewById(R.id.descriptionBook);
                    ImageView coverBook = findViewById(R.id.coverBook);
                    titleBook.setText(book.getTitle());
                    nameLibrary.setText("Library: " + selectedLibraryName);
                    statementBookmarklet.setText(book.getByStatement());
                    publishDateBook.setText("Publish Date: " + book.getPublishDate());
                    numberOfPagesBook.setText("Number of Pages: " + book.getNumberOfPages());
                    descriptionBook.setText(book.getDescription());
                    String bookImageUrl = book.getCover().getLargeUrl();
                    String extractedString = bookImageUrl.substring(bookImageUrl.indexOf("/api/v1/") + "/api/v1/".length());
                    Picasso.get().load("http://193.136.62.24/v1/" + extractedString).into(coverBook);
                }
            });
        }).start();
    }

    private void postCheckOutBooktoWs(Activity activity, String libraryId, String bookIsbn, String userName) {
        new Thread(() -> {
            RequestsService.postCheckOutBook(activity, libraryId, bookIsbn, userName);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(BookActivity.this, MainActivity.class);
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