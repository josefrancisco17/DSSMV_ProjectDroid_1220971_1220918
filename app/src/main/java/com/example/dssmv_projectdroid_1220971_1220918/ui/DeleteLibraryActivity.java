package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.ListViewAdapterLibrary;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;

import java.util.ArrayList;
import java.util.List;

public class DeleteLibraryActivity extends AppCompatActivity {
    private ListView lv;
    private List<Library> librariesList;
    private ListViewAdapterLibrary adapter;
    private String libraryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_library);

        librariesList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewDeleteLibrary);

        getLibrariesFromWs();

        adapter = new ListViewAdapterLibrary(DeleteLibraryActivity.this, librariesList);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Library selectedItem = null;
                for (Library item : librariesList) {
                    if (item.getId().toLowerCase().equals(librariesList.get(position).getId().toLowerCase())) {
                        selectedItem = item;
                        break;
                    }
                }
                if (selectedItem != null) {
                    libraryId = selectedItem.getId();
                    deleteLibraryFromWs();
                    Intent intent = new Intent(DeleteLibraryActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        Button backButton = (Button) findViewById(R.id.backButtonDeleteLibraryActivity);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeleteLibraryActivity.this, AdminActivity.class);
                startActivity(i);
            }
        });
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
                List<Library> libraries = RequestsService.getLibrariesList(DeleteLibraryActivity.this);
                librariesList.clear();
                if (libraries == null){
                    return;
                }
                librariesList.addAll(libraries);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }

    private void deleteLibraryFromWs() {
        new Thread() {
            public void run() {
                RequestsService.deleteLibrary(DeleteLibraryActivity.this, libraryId);

            }
        }.start();
    }
}