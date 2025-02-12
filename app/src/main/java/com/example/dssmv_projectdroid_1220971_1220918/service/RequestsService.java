package com.example.dssmv_projectdroid_1220971_1220918.service;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import com.example.dssmv_projectdroid_1220971_1220918.dto.*;
import com.example.dssmv_projectdroid_1220971_1220918.handler.JsonHandler;
import com.example.dssmv_projectdroid_1220971_1220918.handler.NetworkHandler;
import com.example.dssmv_projectdroid_1220971_1220918.models.*;
import com.example.dssmv_projectdroid_1220971_1220918.ui.BookActivity;
import com.example.dssmv_projectdroid_1220971_1220918.ui.CheckInActivity;
import com.example.dssmv_projectdroid_1220971_1220918.ui.DeleteLibraryActivity;
import com.example.dssmv_projectdroid_1220971_1220918.ui.MakeLibraryActivity;
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

        public static Book getBook(Activity activity, String bookIsbn) {
            try {
                String url = BaseUrl + "book/" + bookIsbn;
                String json = NetworkHandler.getDataInStringFromUrl(url);
                BookDTO bookDTO = JsonHandler.deSerializeJson2BookDTO(json);
                Book book = Mapper.bookDTO2book(bookDTO);
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

        public static Library getLibrary(Activity activity, String libraryId) {
            try {
                String url = BaseUrl + "library/" + libraryId;
                String json = NetworkHandler.getDataInStringFromUrl(url);
                LibraryDTO libraryDTO = JsonHandler.deSerializeJson2LibraryDTO(json);
                Library library = Mapper.libraryDTO2library(libraryDTO);
                return library;
            }  catch(Exception e){
                e.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"getLibrary Error" + e.toString(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(activity," " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return null;
        }

        public static List<Checkout> getCheckOutsHistoryList(Activity activity, String userName) {
            try {
                String url = BaseUrl + "user/checkout-history?userId=" + userName;
                String json = NetworkHandler.getDataInStringFromUrl(url);
                List<CheckoutDTO> LibraryBooksDTOList = JsonHandler.deSerializeJson2ListCheckOutDTO(json);
                List<Checkout> checkoutsList = Mapper.listcheckoutDTO2listcheckout(LibraryBooksDTOList);
                return checkoutsList;
            }  catch(Exception e){
                e.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity," " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        public static List<Review> getReviewsList(Activity activity, String bookIsbn) {
            try {
                String url = BaseUrl + "book/" + bookIsbn + "/review";
                String json = NetworkHandler.getDataInStringFromUrl(url);
                List<ReviewDTO> reviewDTOList = JsonHandler.deSerializeJson2ListReviewDTO(json);
                List<Review> reviewList = Mapper.listreviewDTO2listreview(reviewDTOList);
                return reviewList;
            }  catch(Exception e){
                e.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity," " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        public static void postCheckOutBook(Activity activity, String libraryId, String bookIsbn, String userName) {
            try {
                String url = BaseUrl + "library/" + libraryId + "/book/" + bookIsbn + "/checkout" + "?userId=" + userName;
                String body = " ";
                String result = NetworkHandler.addDataInStringFromUrl(url, body);
            } catch (Exception e) {
                e.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (e.toString().contains("400")) {
                            Toast.makeText(activity, "You have already checkedOut or Library is closed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "postCheckOutBook Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        public static void postCheckInBook(Activity activity, String libraryId, String bookIsbn, String userName) {
            try {
                libraryId = String.format("%s-%s-%s-%s-%s", libraryId.substring(0, 8), libraryId.substring(8, 12), libraryId.substring(12, 16), libraryId.substring(16, 20), libraryId.substring(20));
                String url = BaseUrl + "library/" + libraryId + "/book/" + bookIsbn + "/checkin" + "?userId=" + userName;
                String body = " ";
                String result = NetworkHandler.addDataInStringFromUrl(url, body);
            } catch (Exception e) {
                e.printStackTrace();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (e.toString().contains("400")) {
                            Toast.makeText(activity, "You have already checkedIn or Library is closed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "postCheckInBook Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    public static void postReviewBook(Activity activity, String bookIsbn, String userName, String reviewText, boolean recommended) {
        try {
            String url = BaseUrl + "book/" + bookIsbn + "/review?userId=" + userName;
            String body = "{\"recommended\": " + recommended + ", \"review\": \"" + reviewText + "\"}";
            String result = NetworkHandler.addDataInStringFromUrl(url, body);
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (e.toString().contains("400")) {
                        Toast.makeText(activity, "Can only post one Review", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity,  e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static void postLibrary(Activity activity, String name, String address, String openTime, String closeTime, String openDays) {
        try {
            String url = BaseUrl + "library";
            String body = "{\"address\": \"" + address + "\", \"closeTime\": \"" + closeTime + "\", \"name\": \"" + name + "\", \"openDays\": \"" + openDays + "\", \"openTime\": \"" + openTime + "\"}";
            String result = NetworkHandler.addDataInStringFromUrl(url, body);
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (e.toString().contains("400")) {
                        Toast.makeText(activity, "postLibrary" + e.toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity,  e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static void postLibraryBook(Activity activity, String bookIsbn, String libraryID, String stock) {
        try {
            String url = BaseUrl + "library/" + libraryID + "/book/" + bookIsbn;
            String body = "{\"stock\": \"" + stock  + "\"}";
            String result = NetworkHandler.addDataInStringFromUrl(url, body);
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (e.toString().contains("400")) {
                        Toast.makeText(activity, "postBook" + e.toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity,  e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static void updateReviewBook(Activity activity, String bookIsbn, String userName, String reviewText, boolean recommended, String reviewId) {
        try {
            String url = BaseUrl + "book/" + bookIsbn + "/review/" + reviewId + "?userId=" + userName;
            String body = "{\"recommended\": " + recommended + ", \"review\": \"" + reviewText + "\"}";
            String result = NetworkHandler.updateDataInStringFromUrl(url, body);
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (e.toString().contains("400")) {
                        Toast.makeText(activity, "Error Updating Review", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity,  e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static void deleteLibrary(Activity activity, String libraryId) {
        try {
            String url = BaseUrl + "library/" + libraryId;
            boolean result = NetworkHandler.deleteDataInStringFromUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (e.toString().contains("202")) {
                        Toast.makeText(activity, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, "Error Deleting Library", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static String  getWeather(Activity activity, String latitude, String longitude, String apikey) {
        try {
            String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&apikey=" + apikey;
            String json = NetworkHandler.getDataInStringFromUrl(url);
            String weather = JsonHandler.deSerializeJson2Weather(json);
            return weather;
        }  catch(Exception e){
            e.printStackTrace();
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity," " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return null;
    }
}
