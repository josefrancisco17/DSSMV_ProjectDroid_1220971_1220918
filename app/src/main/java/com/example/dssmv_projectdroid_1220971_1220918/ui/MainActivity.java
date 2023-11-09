package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.handler.NetworkHandler;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView) findViewById(R.id.listViewLibraries);

        ArrayList<String> list = new ArrayList<String>();
        List<Library> libraries = RequestsService.getLibrariesList(this);

        if (libraries != null) {
            String[] listLibraries = new String[libraries.size()];

            for (int i = 0; i < libraries.size(); i++) {
                Library library = libraries.get(i);
                listLibraries[i] = library.getName();
            }

            Collections.addAll(list, listLibraries);
        } else {
            Log.d("error","libraries is null");
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        registerForContextMenu(lv);
    }

    public void launchSearchActivity(View v) {
        Intent i = new Intent(this, SearchActivity.class);
        startActivity(i);
    }

    public void launchReviewsActivity(View v) {
        Intent i = new Intent(this, ReviewsActivity.class);
        startActivity(i);
    }

    public void launchCheckedOutActivity(View v) {
        Intent i = new Intent(this, CheckedOutActivity.class);
        startActivity(i);
    }

    public void launchHistoryActivity(View v) {
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
    }
}