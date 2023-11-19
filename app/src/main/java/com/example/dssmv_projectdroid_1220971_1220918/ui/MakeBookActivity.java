package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.RecyclerViewAdapterLibrary;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class MakeBookActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterLibrary adapter;
    private List<Library> librariesList;

    private ArrayList<String> filteredList = new ArrayList<>();

    private String libraryId;
    private String stock;
    private String bookIsbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_book);

        SearchView searchView = findViewById(R.id.searchViewMAkeBook);
        recyclerView = findViewById(R.id.recyclerViewMakeBook);

        librariesList = new ArrayList<>();
        getLibrariesFromWs();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> librariesNamesList = new ArrayList<>();
        for (Library item : librariesList) {
            librariesNamesList.add(item.getName());
        }

        adapter = new RecyclerViewAdapterLibrary(librariesNamesList, new RecyclerViewAdapterLibrary.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Library selectedLibrary = null;
                for (Library library : librariesList) {
                    if (library.getName() != null && library.getName().toLowerCase().equals(filteredList.get(position))) {
                        selectedLibrary = library;
                        break;
                    }
                }
                if (selectedLibrary != null) {
                    libraryId = selectedLibrary.getId();
                    Toast.makeText(MakeBookActivity.this,  selectedLibrary.getName(), Toast.LENGTH_SHORT).show();
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

        Button submitButtonMakeBook = (Button) findViewById(R.id.submitButtonMakeBook);
        submitButtonMakeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView stockView = (TextView) findViewById(R.id.bookStockInputMakeBook);
                        TextView bookIsbnView = (TextView) findViewById(R.id.bookIsbnInputMakeBook);
                        stock = stockView.getText().toString().trim();
                        bookIsbn = bookIsbnView.getText().toString().trim();
                    }
                });
                postLibraryBooktoWs(MakeBookActivity.this, libraryId, bookIsbn, stock);
                Intent i = new Intent(MakeBookActivity.this, AdminActivity.class);
                startActivity(i);
            }
        });

        Button backButtonMakeBook = (Button) findViewById(R.id.backButtonMakeBook);
        backButtonMakeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MakeBookActivity.this, AdminActivity.class);
                startActivity(i);
            }
        });
    }

    private void filter(String text) {
        filteredList = new ArrayList<>();
        for (Library item : librariesList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item.getName().toLowerCase());
            }
        }
        adapter.filterList(filteredList);
    }

    private void postLibraryBooktoWs(Activity activity, String libraryID, String bookIsbn, String stock) {
        new Thread(() -> {
            RequestsService.postLibraryBook(activity, bookIsbn, libraryID, stock);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MakeBookActivity.this,  "Succefully Created Book", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MakeBookActivity.this, AdminActivity.class);
                    startActivity(i);
                }
            });
        }).start();
    }

    private void getLibrariesFromWs() {
        new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                List<Library> libraryList = RequestsService.getLibrariesList(MakeBookActivity.this);
                librariesList.clear();
                if (libraryList == null){
                    return;
                }
                librariesList.addAll(libraryList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }
}