package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

        checkoutList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewCheckIn);

        Button submitButton = (Button) findViewById(R.id.submitcheckinButton);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                hideKeyboard();
                getUser();
                getCheckOutBooksListFromWs();
                adapter = new ListViewAdapterCheckOut(CheckInActivity.this, checkoutList);
                lv.setAdapter(adapter);
                registerForContextMenu(lv);
            }
        });

        //on listView item click postCheckInBooktoWs(CheckOutActivity.this, selectedLibraryId, selectedLibraryBookIsbn, userName);
    }
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void getUser() {
        userName = ((TextView) findViewById(R.id.textinputNameCheckIn)).getText().toString();
        if (userName.isEmpty()) {
            userName = "Wonderful User";
        }
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
        //
    }



    public void backMainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}