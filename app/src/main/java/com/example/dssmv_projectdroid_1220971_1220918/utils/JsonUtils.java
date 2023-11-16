package com.example.dssmv_projectdroid_1220971_1220918.utils;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {
    public static JSONObject readJsonFile(Context context, String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            return new JSONObject(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
