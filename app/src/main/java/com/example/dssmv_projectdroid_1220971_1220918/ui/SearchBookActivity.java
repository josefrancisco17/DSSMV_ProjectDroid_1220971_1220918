package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.RecyclerViewAdapterLibrary;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.RecyclerViewAdapterLibraryBook;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.models.LibraryBook;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;

import java.util.ArrayList;
import java.util.List;

public class SearchBookActivity extends AppCompatActivity {

    private String selectedLibraryId;

    private RecyclerView recyclerView;
    private RecyclerViewAdapterLibraryBook adapter;
    private List<LibraryBook> libraryBooksList;

    private ArrayList<String> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        Intent intent = getIntent();
        selectedLibraryId = intent.getStringExtra("selectedLibraryId");
        Log.d("selectedLibraryId", selectedLibraryId);

        SearchView searchView = findViewById(R.id.searchViewBooks);
        recyclerView = findViewById(R.id.recyclerViewBooks);

        libraryBooksList = new ArrayList<>();
        getlibraryBooksListFromWs();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> libraryBooksNamesList = new ArrayList<>();
        for (LibraryBook item : libraryBooksList) {
            libraryBooksNamesList.add(item.getBook().getTitle());
        }

        adapter = new RecyclerViewAdapterLibraryBook(libraryBooksNamesList, new RecyclerViewAdapterLibraryBook.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LibraryBook selectedLibraryBook = null;
                for (LibraryBook libraryBook : libraryBooksList) {
                    if (libraryBook.getBook().getTitle() != null && libraryBook.getBook().getTitle().toLowerCase().equals(filteredList.get(position))) {
                        selectedLibraryBook = libraryBook;
                        break;
                    }
                }
                if (selectedLibraryBook != null) {
                    Toast.makeText(SearchBookActivity.this, "Clicked: " + selectedLibraryBook.getBook().getTitle(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SearchBookActivity.this, BookActivity.class);
                    intent.putExtra("selectedLibraryBookIsbn", selectedLibraryBook.getIsbn());
                    startActivity(intent);
                }
            }
        });

        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String text) {
        filteredList = new ArrayList<>();
        for (LibraryBook item : libraryBooksList) {
            if (item.getBook().getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item.getBook().getTitle().toLowerCase());
            }
        }
        adapter.filterList(filteredList);
    }

    private void getlibraryBooksListFromWs() {
        new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                List<LibraryBook> libraryBooks = RequestsService.getLibraryBooksList(SearchBookActivity.this, selectedLibraryId);
                libraryBooksList.clear();
                if (libraryBooks  == null){
                    return;
                }
                libraryBooksList.addAll(libraryBooks);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }

    public void backSearchLibraryActivity(View v) {
        Intent i = new Intent(this, SearchLibraryActivity.class);
        startActivity(i);
    }
}