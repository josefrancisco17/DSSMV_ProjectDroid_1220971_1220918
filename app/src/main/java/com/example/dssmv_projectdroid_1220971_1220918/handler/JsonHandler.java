package com.example.dssmv_projectdroid_1220971_1220918.handler;

import android.util.Log;
import com.example.dssmv_projectdroid_1220971_1220918.dto.LibraryDTO;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.models.LocalTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonHandler {
    public static List<LibraryDTO> deSerializeJson2ListLibraryDTO(String json) throws JSONException {
        JSONArray jsonResponse = new JSONArray(json);
        List<LibraryDTO> list = new ArrayList<>();
        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject jsonChildNode = jsonResponse.getJSONObject(i);

            String address = jsonChildNode.optString("address");
            String openTime = jsonChildNode.optString("openTime");
            String closeTime = jsonChildNode.optString("closeTime");
            String id = jsonChildNode.optString("id");
            String name = jsonChildNode.optString("name");
            String open = jsonChildNode.optString("open");
            String openDays = jsonChildNode.optString("openDays");
            String openStatement = jsonChildNode.optString("openStatement");

            list.add(new LibraryDTO(address, closeTime, id, name, open, openDays, openStatement, openTime));
        }
        return list;
    }
}
