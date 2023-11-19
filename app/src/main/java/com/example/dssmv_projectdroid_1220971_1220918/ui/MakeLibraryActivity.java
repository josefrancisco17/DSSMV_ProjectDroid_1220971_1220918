package com.example.dssmv_projectdroid_1220971_1220918.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.dssmv_projectdroid_1220971_1220918.R;
import android.widget.Button;
import com.example.dssmv_projectdroid_1220971_1220918.service.RequestsService;

public class MakeLibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_library);

        Button submitMakeLibraryButton = (Button) findViewById(R.id.submitMakeLibraryButton);
        submitMakeLibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView libraryName = (TextView) findViewById(R.id.libraryNameMakeLibraryinput);
                EditText libraryAddress = (EditText) findViewById(R.id.libraryAddressMakeLibraryinput);
                EditText libraryOpenTime = (EditText) findViewById(R.id.libraryOpenTimeMakeLibraryinput);
                EditText libraryCloseTime = (EditText) findViewById(R.id.libraryCloseTimeMakeLibraryinput);
                EditText libraryOpenDays = (EditText) findViewById(R.id.libraryOpenDaysMakeLibraryinput);
                postLibraryToWS(MakeLibraryActivity.this, libraryName.getText().toString(), libraryAddress.getText().toString(), libraryOpenTime.getText().toString(), libraryCloseTime.getText().toString(), libraryOpenDays.getText().toString());
            }
        });

        Button backMakeLibraryButton = (Button) findViewById(R.id.backMakeLibraryButton);
        backMakeLibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MakeLibraryActivity.this, AdminActivity.class);
                startActivity(i);
            }
        });
    }

    private void postLibraryToWS(Activity activity, String libraryName, String libraryAddress, String libraryOpenTime, String libraryCloseTime, String libraryOpenDays) {
        new Thread(() -> {
            RequestsService.postLibrary(activity, libraryName, libraryAddress, libraryOpenTime, libraryCloseTime, libraryOpenDays);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MakeLibraryActivity.this,"Succefully created Library", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MakeLibraryActivity.this, AdminActivity.class);
                    startActivity(i);
                }
            });
        }).start();
    }
}

