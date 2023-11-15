package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.ListViewAdapterCheckOut;
import com.example.dssmv_projectdroid_1220971_1220918.adapter.ListViewAdapterHistory;
import com.example.dssmv_projectdroid_1220971_1220918.models.Checkout;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private String userName;
    private ListView lv;
    private List<Checkout> checkoutHistoryList;
    private ListViewAdapterHistory adapter;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        userName = preferences.getString("userName", "defaultValue");

        checkoutHistoryList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewHistory);

        getCheckOutHistoryListFromWs();
        adapter = new ListViewAdapterHistory(HistoryActivity.this, checkoutHistoryList);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Checkout selectedItem = null;
                for (Checkout item : checkoutHistoryList) {
                    if (item.getBook().getBook().getTitle() != null && item.getBook().getBook().getTitle().toLowerCase().equals(checkoutHistoryList.get(position).getBook().getBook().getTitle().toLowerCase())) {
                        selectedItem = item;
                        break;
                    }
                }
                if (selectedItem != null) {
                    Intent intent = new Intent(HistoryActivity.this, BookActivity.class);
                    intent.putExtra("selectedLibraryBookIsbn", selectedItem.getBook().getIsbn());
                    //necessario uma vez que a api retorna bookisbn assim bb385aa2866f419b85fd202ecec8cfde e para fzr checckout é preciso assimbb385aa2-866f-419b-85fd-202ecec8cfde
                    String libraryId = selectedItem.getBook().getLibrary().getId();
                    libraryId = String.format("%s-%s-%s-%s-%s", libraryId.substring(0, 8), libraryId.substring(8, 12), libraryId.substring(12, 16), libraryId.substring(16, 20), libraryId.substring(20));
                    intent.putExtra("selectedLibraryId", libraryId);
                    intent.putExtra("selectedLibraryName", selectedItem.getBook().getLibrary().getName());
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    public void getCheckOutHistoryListFromWs() {
        new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                List<Checkout> checkouts = RequestsService.getCheckOutsHistoryList(HistoryActivity.this, userName);
                checkoutHistoryList.clear();
                if (checkouts == null){
                    return;
                }
                checkoutHistoryList.addAll(checkouts);
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