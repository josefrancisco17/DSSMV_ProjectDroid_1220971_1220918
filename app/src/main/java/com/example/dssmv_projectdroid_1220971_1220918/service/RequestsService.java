package com.example.dssmv_projectdroid_1220971_1220918.service;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import com.example.dssmv_projectdroid_1220971_1220918.dto.*;
import com.example.dssmv_projectdroid_1220971_1220918.handler.JsonHandler;
import com.example.dssmv_projectdroid_1220971_1220918.handler.NetworkHandler;
import com.example.dssmv_projectdroid_1220971_1220918.models.Book;
import com.example.dssmv_projectdroid_1220971_1220918.models.Checkout;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.models.LibraryBook;
import org.json.JSONException;

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
                List<LibraryBookDTO> LibraryBooksDTOList = JsonHandler.deSerializeJson2ListLibraryBookDTO(json);
                List<LibraryBook> LibraryBooksList = Mapper.listLibraryBookDTO2listLibraryBook(LibraryBooksDTOList);
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

        public static Book getBook(Activity activity, String bookIsbn){
            try {
                String url = BaseUrl + "book/" + bookIsbn;
                String json = NetworkHandler.getDataInStringFromUrl(url);
                Log.d("json", json);
                BookDTO bookDTO = JsonHandler.deSerializeJson2BookDTO(json);
                Log.d("BookDTO", bookDTO.toString());
                Book book = Mapper.bookDTO2book(bookDTO);
                Log.d("Book", book.toString());
                return book;
            }  catch(Exception e){
                e.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"getBook Error" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        public static List<Checkout> getCheckOutsList(Activity activity, String userName) {
                try {
                    String url = BaseUrl + "user/checked-out?userId=" + userName;
                    String json = NetworkHandler.getDataInStringFromUrl(url);
                    List<CheckoutDTO> LibraryBooksDTOList = JsonHandler.deSerializeJson2ListCheckOutDTO(json);
                    List<Checkout> checkoutsList = Mapper.listcheckoutDTO2listcheckout(LibraryBooksDTOList);
                    return checkoutsList;
                }  catch(Exception e){
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity,"getCheckOutsList Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return null;
        }

        public static void postCheckOutBook(Activity activity, String libraryId, String bookIsbn, String userName) {
            try {
                String url = BaseUrl + "library/" + libraryId + "/book/" + bookIsbn + "/checkout" + "?userId=" + userName;
                String json = " ";
                String result = NetworkHandler.addDataInStringFromUrl(url, json);
            } catch (Exception e) {
                e.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (e.toString().contains("400")) {
                            Toast.makeText(activity, "You have already checkedOut", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "postCheckOutBook Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
}
