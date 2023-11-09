package com.example.dssmv_projectdroid_1220971_1220918.dto;

import android.util.Log;
import com.example.dssmv_projectdroid_1220971_1220918.models.Library;

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
        Library data = new Library(obj.getName());
        return data;
    }
}
