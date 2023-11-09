package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.ListViewAdapterLibrary;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;

import java.util.ArrayList;
import java.util.List;

public class LibrariesActivity extends AppCompatActivity {

    private ListView lv;
    private List<Library> librariesList;
    private ListViewAdapterLibrary adapter;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private Button getLibrariesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libraries);

        librariesList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewLibraries);

        getLibrariesButton = (Button) findViewById(R.id.button);
        getLibrariesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLibrariesFromWs();
            }
        });

        adapter = new ListViewAdapterLibrary(LibrariesActivity.this, librariesList);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);
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
                List<Library> libraryDTOS = RequestsService.getLibrariesList(LibrariesActivity.this);
                librariesList.clear();
                if (libraryDTOS == null){
                    return;
                }
                librariesList.addAll(libraryDTOS);
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