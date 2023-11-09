package com.example.dssmv_projectdroid_1220971_1220918.service;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import com.example.dssmv_projectdroid_1220971_1220918.dto.LibraryDTO;
import com.example.dssmv_projectdroid_1220971_1220918.dto.Mapper;
import com.example.dssmv_projectdroid_1220971_1220918.handler.JsonHandler;
import com.example.dssmv_projectdroid_1220971_1220918.handler.NetworkHandler;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;

import java.io.IOException;
import java.util.List;

public class RequestsService {
    private static String BaseUrl = "http://193.136.62.24/v1/";
        public static List<Library> getLibrariesList(Activity activity){
            try {
                String url = BaseUrl + "library/";
                String json = NetworkHandler.getDataInStringFromUrl(url);
                List<LibraryDTO> LibrariesDTOList = JsonHandler.deSerializeJson2ListLibraryDTO(json);
                List<Library> LibrariesList = Mapper.listLibraryDTO2listLibrary(LibrariesDTOList);
                return LibrariesList;
            }  catch(Exception e){
                e.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"getLibrariesList Error" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
}
