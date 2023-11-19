package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.ListViewAdapterLibrary;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;
import com.example.dssmv_projectdroid_1220971_1220918.utils.JsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private List<Library> librariesList;
    private ListViewAdapterLibrary adapter;
    private Button buttonRefreshLibraries;
    private Button logOut;
    private String userName;
    private boolean isLoggedIn;
    private String weather = " ";
    private String weatherApiKey;
    private String latitude;
    private String longitude;

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
                Toast.makeText(MainActivity.this, "Libraries have been Loaded", Toast.LENGTH_SHORT).show();
            }
        });

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
                    Intent intent = new Intent(MainActivity.this, LibraryActivity.class);
                    intent.putExtra("selectedLibraryId", selectedItem.getId());
                    startActivity(intent);
                }
                return true;
            }
        });

        JSONObject configJson = JsonUtils.readJsonFile(this, "config.json");
        if (configJson != null) {
            try {
                weatherApiKey = configJson.getString("openWeatherApiKey");
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude() + "";
                        longitude = location.getLongitude() + "";
                    }
                }
                getWeatherData();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JsonReadError", "Error reading JSON data");
            }
        } else {
            Log.e("JsonReadError", "Error reading JSON file");
        }
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

    private void getWeatherData() {
        new Thread() {
            public void run() {
                weather = RequestsService.getWeather(MainActivity.this, latitude, longitude, weatherApiKey);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tvweather = (TextView) findViewById(R.id.textViewWeather);
                        tvweather.setText(weather);
                    }
                });
            }
        }.start();
    }

    public void launchLibrariesActivity(View v) {
        Intent i = new Intent(this, SearchLibraryActivity.class);
        startActivity(i);
    }

    public void launchHistoryActivity(View v) {
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
    }

    public void launchCheckInActivity(View v) {
        Intent i = new Intent(this, CheckInActivity.class);
        startActivity(i);
    }
}