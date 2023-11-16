package com.example.dssmv_projectdroid_1220971_1220918.handler;

import android.util.Log;
import com.example.dssmv_projectdroid_1220971_1220918.dto.*;
import com.example.dssmv_projectdroid_1220971_1220918.models.*;
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

    public static BookDTO deSerializeJson2BookDTO(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        // Extracting Book information
        String isbn = jsonObject.getString("isbn");
        String title = jsonObject.getString("title");
        String description = jsonObject.getString("description");

        // Extracting authors
        JSONArray jsonAuthors = jsonObject.getJSONArray("authors");
        List<Author> authors = new ArrayList<>();

        for (int j = 0; j < jsonAuthors.length(); j++) {
            JSONObject jsonAuthor = jsonAuthors.getJSONObject(j);
            String authorName = jsonAuthor.getString("name");
            String authorBio = jsonAuthor.optString("bio", null);
            String authorBirthDate = jsonAuthor.optString("birthDate", null);
            String authorDeathDate = jsonAuthor.optString("deathDate", null);
            String authorId = jsonAuthor.optString("id", null);

            // Extracting alternateNames
            JSONArray jsonAlternateNames = jsonAuthor.optJSONArray("alternateNames");
            List<String> alternateNames = new ArrayList<>();
            if (jsonAlternateNames != null) {
                for (int k = 0; k < jsonAlternateNames.length(); k++) {
                    String alternateName = jsonAlternateNames.getString(k);
                    alternateNames.add(alternateName);
                }
            }

            // Creating an Author object and adding it to the list
            Author author = new Author(alternateNames, authorBio, authorBirthDate, authorDeathDate, authorId, authorName);
            authors.add(author);
        }

        // Extracting subjectPeople, subjectPlaces, subjectTimes, and subjects as List<String>
        List<String> subjectPeople = extractStringList(jsonObject.optJSONArray("subjectPeople"));
        List<String> subjectPlaces = extractStringList(jsonObject.optJSONArray("subjectPlaces"));
        List<String> subjectTimes = extractStringList(jsonObject.optJSONArray("subjectTimes"));
        List<String> subjects = extractStringList(jsonObject.optJSONArray("subjects"));

        // Extracting other BookDTO information
        String byStatement = jsonObject.optString("byStatement", null);

        // Extracting Cover information
        JSONObject jsonCover = jsonObject.optJSONObject("cover");
        String mediumUrl = "";
        String smallUrl = "";
        String largeUrl = "";

        if (jsonCover != null) {
            mediumUrl = jsonCover.optString("mediumUrl", "");
            smallUrl = jsonCover.optString("smallUrl", "");
            largeUrl = jsonCover.optString("largeUrl", "");
        }

        CoverUrls coverUrls = new CoverUrls(mediumUrl, smallUrl, largeUrl);
        int numberOfPages = jsonObject.optInt("numberOfPages", 0); // Provide a default value
        String publishDate = jsonObject.optString("publishDate", null);

        // Creating a BookDTO object
        return new BookDTO(authors, byStatement, coverUrls, description, isbn, numberOfPages, publishDate, subjectPeople, subjectPlaces, subjectTimes, subjects, title);
    }

    public static List<LibraryBookDTO> deSerializeJson2ListLibraryBookDTO(String json) throws JSONException {
        JSONArray jsonResponse = new JSONArray(json);
        List<LibraryBookDTO> list = new ArrayList<>();
        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject jsonLibraryBook = jsonResponse.getJSONObject(i);

            int available = jsonLibraryBook.getInt("available");
            int checkedOut = jsonLibraryBook.getInt("checkedOut");
            String isbn = jsonLibraryBook.getString("isbn");
            int stock = jsonLibraryBook.getInt("stock");

            // Extracting Book information
            JSONObject jsonBook = jsonLibraryBook.getJSONObject("book");
            String bookIsbn = jsonBook.getString("isbn");
            String title = jsonBook.getString("title");
            String description = jsonBook.getString("description");
            String byStatement = jsonBook.getString("byStatement");
            String publishDate = jsonBook.getString("publishDate");
            int numberOfPages = jsonBook.getInt("numberOfPages");

            // Extracting authors
            JSONArray jsonAuthors = jsonBook.getJSONArray("authors");
            List<Author> authors = new ArrayList<>();

            for (int j = 0; j < jsonAuthors.length(); j++) {
                JSONObject jsonAuthor = jsonAuthors.getJSONObject(j);
                String authorName = jsonAuthor.getString("name");
                String authorBio = jsonAuthor.optString("bio", null); // bio is optional
                String authorBirthDate = jsonAuthor.optString("birthDate", null); // birthDate is optional
                String authorDeathDate = jsonAuthor.optString("deathDate", null); // deathDate is optional
                String authorId = jsonAuthor.optString("id", null); // id is optional

                // Extracting alternateNames
                JSONArray jsonAlternateNames = jsonAuthor.optJSONArray("alternateNames");
                List<String> alternateNames = new ArrayList<>();
                if (jsonAlternateNames != null) {
                    for (int k = 0; k < jsonAlternateNames.length(); k++) {
                        String alternateName = jsonAlternateNames.getString(k);
                        alternateNames.add(alternateName);
                    }
                }
                Author author = new Author(alternateNames, authorBio, authorBirthDate, authorDeathDate, authorId, authorName);
                authors.add(author);
            }

            // Extracting Cover information
            JSONObject jsonCover = jsonBook.optJSONObject("cover");
            String mediumUrl = jsonCover.optString("mediumUrl");
            String smallUrl = jsonCover.optString("smallUrl");
            String largeUrl = jsonCover.optString("largeUrl");
            CoverUrls coverUrls = new CoverUrls(mediumUrl, smallUrl, largeUrl);

            // Extracting subjectPeople, subjectPlaces, subjectTimes, and subjects
            List<String> subjectPeople = extractStringList(jsonBook.optJSONArray("subjectPeople"));
            List<String> subjectPlaces = extractStringList(jsonBook.optJSONArray("subjectPlaces"));
            List<String> subjectTimes = extractStringList(jsonBook.optJSONArray("subjectTimes"));
            List<String> subjects = extractStringList(jsonBook.optJSONArray("subjects"));

            Book book = new Book(authors,byStatement,coverUrls,description,isbn,numberOfPages,publishDate,subjectPeople,subjectPlaces,subjectTimes,subjects,title);

            // Extracting Library
            JSONObject jsonLibrary = jsonLibraryBook.getJSONObject("library");
            String libraryId = jsonLibrary.getString("id");
            String libraryName = jsonLibrary.getString("name");
            String libraryAddress = jsonLibrary.getString("address");
            String libraryOpen = jsonLibrary.getString("open");
            String libraryOpenDays = jsonLibrary.getString("openDays");
            String libraryOpenStatement = jsonLibrary.getString("openStatement");
            String libraryOpenTime = jsonLibrary.getString("openTime");
            String libraryCloseTime = jsonLibrary.getString("closeTime");
            Library library = new Library(libraryAddress, libraryCloseTime, libraryId, libraryName, libraryOpen, libraryOpenDays, libraryOpenStatement, libraryOpenTime);

            LibraryBookDTO libraryBookDTO = new LibraryBookDTO(available, book, checkedOut, bookIsbn, library, stock);
            list.add(libraryBookDTO);
        }
        return list;
    }

    public static List<CheckoutDTO> deSerializeJson2ListCheckOutDTO(String json) throws JSONException {
        JSONArray jsonResponse = new JSONArray(json);
        List<CheckoutDTO> list = new ArrayList<>();

        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject jsonObject = jsonResponse.getJSONObject(i);

            // Extracting Checkout information
            String id = jsonObject.getString("id");
            String bookId = jsonObject.getString("bookId");
            String libraryId = jsonObject.getString("libraryId");
            String userId = jsonObject.getString("userId");
            boolean active = jsonObject.getBoolean("active");
            String dueDate = jsonObject.getString("dueDate");
            String createTimestamp = jsonObject.getString("createTimestamp");
            String updateTimestamp = jsonObject.getString("updateTimestamp");
            String libraryName = jsonObject.getString("libraryName");
            String libraryAddress = jsonObject.getString("libraryAddress");
            String libraryOpenTime = jsonObject.getString("libraryOpenTime");
            String libraryCloseTime = jsonObject.getString("libraryCloseTime");

            // Extracting Book information
            JSONObject jsonBook = jsonObject.getJSONObject("book");
            String bookIsbn = jsonBook.getString("isbn");
            String title = jsonBook.getString("title");
            String description = jsonBook.getString("description");
            String byStatement = jsonBook.getString("byStatement");
            String publishDate = jsonBook.getString("publishDate");
            int numberOfPages = jsonBook.getInt("numberOfPages");

            // Extracting authors
            JSONArray jsonAuthors = jsonBook.getJSONArray("authors");
            List<Author> authors = new ArrayList<>();

            for (int j = 0; j < jsonAuthors.length(); j++) {
                JSONObject jsonAuthor = jsonAuthors.getJSONObject(j);
                String authorName = jsonAuthor.getString("name");
                String authorBio = jsonAuthor.optString("bio", null); // bio is optional
                String authorBirthDate = jsonAuthor.optString("birthDate", null); // birthDate is optional
                String authorDeathDate = jsonAuthor.optString("deathDate", null); // deathDate is optional
                String authorId = jsonAuthor.optString("id", null); // id is optional

                // Extracting alternateNames
                JSONArray jsonAlternateNames = jsonAuthor.optJSONArray("alternateNames");
                List<String> alternateNames = new ArrayList<>();
                if (jsonAlternateNames != null) {
                    for (int k = 0; k < jsonAlternateNames.length(); k++) {
                        String alternateName = jsonAlternateNames.getString(k);
                        alternateNames.add(alternateName);
                    }
                }
                Author author = new Author(alternateNames, authorBio, authorBirthDate, authorDeathDate, authorId, authorName);
                authors.add(author);
            }

            // Extracting Cover information
            JSONObject jsonCover = jsonBook.optJSONObject("cover");
            String mediumUrl = jsonCover.optString("mediumUrl");
            String smallUrl = jsonCover.optString("smallUrl");
            String largeUrl = jsonCover.optString("largeUrl");
            CoverUrls coverUrls = new CoverUrls(mediumUrl, smallUrl, largeUrl);

            // Extracting subjectPeople, subjectPlaces, subjectTimes, and subjects
            List<String> subjectPeople = extractStringList(jsonBook.optJSONArray("subjectPeople"));
            List<String> subjectPlaces = extractStringList(jsonBook.optJSONArray("subjectPlaces"));
            List<String> subjectTimes = extractStringList(jsonBook.optJSONArray("subjectTimes"));
            List<String> subjects = extractStringList(jsonBook.optJSONArray("subjects"));

            Library library = new Library(libraryAddress, libraryCloseTime, libraryId, libraryName, "true", libraryOpenTime," " ,libraryOpenTime);
            Book book = new Book(authors,byStatement,coverUrls,description,bookIsbn,numberOfPages,publishDate,subjectPeople,subjectPlaces,subjectTimes,subjects,title);
            LibraryBook libraryBook = new LibraryBook(1, book, 1, bookIsbn, library, 1);

            CheckoutDTO checkoutDTO = new CheckoutDTO(active, libraryBook, createTimestamp, dueDate, id, false, updateTimestamp, userId);
            list.add(checkoutDTO);
        }

        return list;
    }

    public static List<ReviewDTO> deSerializeJson2ListReviewDTO(String json) throws JSONException {
        JSONArray jsonResponse = new JSONArray(json);
        List<ReviewDTO> list = new ArrayList<>();

        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject jsonChildNode = jsonResponse.getJSONObject(i);

            String createdDate = jsonChildNode.optString("createdDate");
            String id = jsonChildNode.optString("id");
            String isbn = jsonChildNode.optString("isbn");
            boolean recommended = jsonChildNode.optBoolean("recommended");
            String review = jsonChildNode.optString("review");
            String reviewer = jsonChildNode.optString("reviewer");

            list.add(new ReviewDTO(createdDate, id, isbn, recommended, review, reviewer));
        }

        return list;
    }

    private static List<String> extractStringList(JSONArray jsonArray) {
        List<String> stringList = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                stringList.add(jsonArray.optString(i, null));
            }
        }
        return stringList;
    }

    public static String deSerializeJson2Weather(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        JSONObject mainObject = jsonObject.getJSONObject("main");
        double temperature = mainObject.getDouble("temp");
        temperature = (temperature - 273.15);
        temperature = Math.round(temperature * 10.0) / 10.0;

        String cityName = jsonObject.getString("name");
        return cityName + ", " + temperature + " ºC";
    }
}
