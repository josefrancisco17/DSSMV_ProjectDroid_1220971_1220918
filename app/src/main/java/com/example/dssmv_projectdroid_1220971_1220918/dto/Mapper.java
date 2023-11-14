package com.example.dssmv_projectdroid_1220971_1220918.dto;

import android.util.Log;
import com.example.dssmv_projectdroid_1220971_1220918.models.Book;
import com.example.dssmv_projectdroid_1220971_1220918.models.Checkout;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;
import com.example.dssmv_projectdroid_1220971_1220918.models.LibraryBook;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static List<Library> listLibraryDTO2listLibrary(List<LibraryDTO> LibrariesDTOList){
        List<Library> LibraryList = new ArrayList<>();
        for(LibraryDTO obj : LibrariesDTOList){
            Library i = libraryDTO2Library(obj);
            LibraryList.add(i);
        }
        return LibraryList;
    }

    public static Library libraryDTO2Library(LibraryDTO obj){
        return new Library(obj.getAddress(), obj.getCloseTime(), obj.getId(), obj.getName(), obj.isOpen(), obj.getOpenDays(), obj.getOpenStatement(), obj.getOpenTime());
    }

    public static List<LibraryBook> listLibraryBookDTO2listLibraryBook(List<LibraryBookDTO> LibraryBooksDTOList) {
        List<LibraryBook> LibraryBooksList = new ArrayList<>();
        for(LibraryBookDTO obj : LibraryBooksDTOList){
            LibraryBook i = libraryBookDTO2LibraryBook(obj);
            LibraryBooksList.add(i);
        }
        return LibraryBooksList;
    }

    public static List<Checkout> listcheckoutDTO2listcheckout(List<CheckoutDTO> checkOutDTOList) {
        List<Checkout> checkoutList = new ArrayList<>();
        for(CheckoutDTO obj : checkOutDTOList){
            Checkout i = checkOutDTO2checkout(obj);
            checkoutList.add(i);
        }
        return checkoutList;
    }

    public static Checkout checkOutDTO2checkout(CheckoutDTO obj){
        return new Checkout(obj.isActive(), obj.getBook(), obj.getCreateTimestamp(), obj.getDueDate(), obj.getId(), obj.isOverdue(), obj.getUpdateTimestamp(), obj.getUserId());
    }
    public static LibraryBook libraryBookDTO2LibraryBook(LibraryBookDTO obj){
        return new LibraryBook(obj.getAvailable(), obj.getBook(), obj.getCheckedOut(), obj.getIsbn(), obj.getLibrary(), obj.getStock());
    }

    public static Book bookDTO2book(BookDTO obj){
        return new Book(obj.getAuthors(), obj.getByStatement(), obj.getCover(), obj.getDescription(), obj.getIsbn(), obj.getNumberOfPages(), obj.getPublishDate(), obj.getSubjectPeople(), obj.getSubjectPlaces(), obj.getSubjectTimes(), obj.getSubjects(), obj.getTitle());
    }
}
