package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.ListViewAdapterLibrary;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private List<Library> librariesList;
    private ListViewAdapterLibrary adapter;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;

    private Button buttonRefreshLibraries;

    private Button logOut;

    private String userName;

    private boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        userName = preferences.getString("userName", "defaultValue");
        isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        TextView loginName = (TextView) findViewById(R.id.loggedInName);
        loginName.setText("User Name: " + userName);

        librariesList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewLibraries);

        getLibrariesFromWs();

        adapter = new ListViewAdapterLibrary(MainActivity.this, librariesList);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);

        logOut = (Button) findViewById(R.id.logOutButton);

        logOut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = preferences.edit();
               editor.putBoolean("isLoggedIn", false);
               editor.apply();
               Intent intent = new Intent(MainActivity.this, LoginActivity.class);
               startActivity(intent);
           }
        });

        buttonRefreshLibraries = (Button) findViewById(R.id.buttonRefreshLibraries);

        buttonRefreshLibraries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLibrariesFromWs();
                Toast.makeText(getApplicationContext(), "Libraries have been Loaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void launchLibrariesActivity(View v) {
        Intent i = new Intent(this, SearchLibraryActivity.class);
        startActivity(i);
    }

    public void launchMyReviewsActivity(View v) {
        Intent i = new Intent(this, MyReviewsActivity.class);
        startActivity(i);
    }

    public void launchCheckInActivity(View v) {
        Intent i = new Intent(this, CheckInActivity.class);
        startActivity(i);
    }

    public void launchHistoryActivity(View v) {
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
    }

    public void launchSettingsActivity(View v) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
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
                List<Library> libraries = RequestsService.getLibrariesList(MainActivity.this);
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
}

