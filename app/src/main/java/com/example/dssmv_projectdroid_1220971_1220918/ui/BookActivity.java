package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Intent intent = getIntent();
        selectedLibraryBookIsbn = intent.getStringExtra("selectedLibraryBookIsbn");
        selectedLibraryId = intent.getStringExtra("selectedLibraryId");
        selectedLibraryName = intent.getStringExtra("selectedLibraryName");

        book = RequestsService.getBook(this, selectedLibraryBookIsbn);
        getBookFromWs();
    }

    private void getBookFromWs() {
        new Thread(() -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //
                }
            });
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

    public void launchCheckOutActivity(View v) {
        Intent i = new Intent(this, CheckOutActivity.class);
        i.putExtra("selectedLibraryBookIsbn", selectedLibraryBookIsbn);
        i.putExtra("selectedLibraryId", selectedLibraryId);
        i.putExtra("selectedLibraryName", selectedLibraryName);
        startActivity(i);
    }

    public void launchReviewsActivity(View v) {
        Intent i = new Intent(this, ReviewsActivity.class);
        i.putExtra("selectedLibraryBookIsbn", selectedLibraryBookIsbn);
        i.putExtra("selectedLibraryId", selectedLibraryId);
        i.putExtra("selectedLibraryName", selectedLibraryName);
        startActivity(i);
    }

    public void backMainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}