package com.example.dssmv_projectdroid_1220971_1220918.dto;

import com.example.dssmv_projectdroid_1220971_1220918.models.Library;

import java.util.List;

public class Mapper {
    public static List<Library> listLibraryDTO2listLibrary(List<LibraryDTO> LibrariesDTOList){
        List<Library> LibraryList = null;
        for (LibraryDTO libraryDTO : LibrariesDTOList) {
            Library library = new Library(
                    libraryDTO.getAddress(),
                    libraryDTO.getCloseTime(),
                    libraryDTO.getId(),
                    libraryDTO.getName(),
                    libraryDTO.isOpen(),
                    libraryDTO.getOpenDays(),
                    libraryDTO.getOpenStatement(),
                    libraryDTO.getOpenTime()
            );
            LibraryList.add(library);
        }
        return LibraryList;
    }
}
