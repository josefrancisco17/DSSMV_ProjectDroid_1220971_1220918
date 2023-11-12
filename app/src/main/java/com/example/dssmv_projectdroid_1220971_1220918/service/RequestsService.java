package com.example.dssmv_projectdroid_1220971_1220918.service;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import com.example.dssmv_projectdroid_1220971_1220918.dto.LibraryBookDTO;
import com.example.dssmv_projectdroid_1220971_1220918.dto.LibraryDTO;
import com.example.dssmv_projectdroid_1220971_1220918.dto.Mapper;
import com.example.dssmv_projectdroid_1220971_1220918.handler.JsonHandler;
import com.example.dssmv_projectdroid_1220971_1220918.handler.NetworkHandler;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.models.LibraryBook;

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

        public static List<LibraryBook> getLibraryBooksList(Activity activity, String selectedLibraryId){
            try {
                String url = BaseUrl + "library/" + selectedLibraryId + "/book";
                String json = NetworkHandler.getDataInStringFromUrl(url);
                Log.d("json", json);
                List<LibraryBookDTO> LibraryBooksDTOList = JsonHandler.deSerializeJson2ListLibraryBookDTO(json);
                Log.d("LibraryBooksDTOList", LibraryBooksDTOList.toString());
                List<LibraryBook> LibraryBooksList = Mapper.listLibraryBookDTO2listLibraryBook(LibraryBooksDTOList);
                Log.d("LibraryBooksList", LibraryBooksList.toString());
                return LibraryBooksList;
            }  catch(Exception e){
                e.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"getLibraryBooksList Error" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
}
