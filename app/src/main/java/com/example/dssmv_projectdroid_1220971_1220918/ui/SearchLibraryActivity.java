package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.RecyclerViewAdapterLibrary;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;

import java.util.ArrayList;
import java.util.List;

public class SearchLibraryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapterLibrary adapter;
    private List<Library> librariesList;

    private ArrayList<String> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_library);

        SearchView searchView = findViewById(R.id.searchViewLibraries);
        recyclerView = findViewById(R.id.recyclerViewLibraries);

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
                    Toast.makeText(SearchLibraryActivity.this, "Clicked: " + selectedLibrary.getName(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(SearchLibraryActivity.this, SearchBookActivity.class);
                    i.putExtra("selectedLibraryId", selectedLibrary.getId());
                    startActivity(i);
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
        for (Library item : librariesList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item.getName().toLowerCase());
            }
        }
        adapter.filterList(filteredList);
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
                List<Library> libraryList = RequestsService.getLibrariesList(SearchLibraryActivity.this);
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

    public void backMainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
