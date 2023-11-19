package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.ListViewAdapterCheckOut;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.ListViewAdapterLibrary;
import com.example.dssmv_projectdroid_1220971_1220918.models.Checkout;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.models.LibraryBook;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;
import android.view.inputmethod.InputMethodManager;
import com.example.dssmv_projectdroid_1220971_1220918.models.Checkout;

import java.util.ArrayList;
import java.util.List;

public class CheckInActivity extends AppCompatActivity {
    private ListView lv;
    private List<Checkout> checkoutList;
    private String userName;
    private ListViewAdapterCheckOut adapter;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        userName = preferences.getString("userName", "defaultValue");

        checkoutList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewCheckIn);

        getCheckOutBooksListFromWs();
        adapter = new ListViewAdapterCheckOut(CheckInActivity.this, checkoutList);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Checkout selectedItem = null;
                for (Checkout item : checkoutList) {
                    //duplo getbook() visto que e checkout -> LibraryBook -> Book
                    if (item.getBook().getBook().getTitle() != null && item.getBook().getBook().getTitle().toLowerCase().equals(checkoutList.get(position).getBook().getBook().getTitle().toLowerCase())) {
                        selectedItem = item;
                        break;
                    }
                }
                if (selectedItem != null) {
                    postCheckInBooktoWs(CheckInActivity.this, selectedItem.getBook().getLibrary().getId(), selectedItem.getBook().getIsbn(), userName);
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Checkout selectedItem = null;
                for (Checkout item : checkoutList) {
                    if (item.getBook().getBook().getTitle() != null && item.getBook().getBook().getTitle().toLowerCase().equals(checkoutList.get(position).getBook().getBook().getTitle().toLowerCase())) {
                        selectedItem = item;
                        break;
                    }
                }
                if (selectedItem != null) {
                    Intent intent = new Intent(CheckInActivity.this, BookActivity.class);
                    intent.putExtra("selectedLibraryBookIsbn", selectedItem.getBook().getIsbn());
                    intent.putExtra("selectedLibraryId", selectedItem.getBook().getLibrary().getId());
                    intent.putExtra("selectedLibraryName", selectedItem.getBook().getLibrary().getName());
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    public void getCheckOutBooksListFromWs() {
        new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                List<Checkout> checkouts = RequestsService.getCheckOutsList(CheckInActivity.this, userName);
                checkoutList.clear();
                if (checkouts == null){
                    return;
                }
                checkoutList.addAll(checkouts);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }

    public void postCheckInBooktoWs(Activity activity, String libraryId, String bookIsbn, String userName) {
        new Thread(() -> {
            RequestsService.postCheckInBook(activity, libraryId, bookIsbn, userName);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(CheckInActivity.this, MainActivity.class);
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