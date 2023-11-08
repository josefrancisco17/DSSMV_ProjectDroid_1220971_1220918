package com.example.dssmv_projectdroid_1220971_1220918.models;

public class CheckedOutBook {
    private boolean active;
    private Book book;
    private String bookId;
    private String createTimestamp;
    private String dueDate;
    private String id;
    private String libraryAddress;
    private LocalTime libraryCloseTime;
    private String libraryId;
    private String libraryName;
    private LocalTime libraryOpenTime;
    private String updateTimestamp;
    private String userId;

    public CheckedOutBook(boolean active, Book book, String bookId, String createTimestamp, String dueDate, String id,String libraryAddress, LocalTime libraryCloseTime, String libraryId, String libraryName,LocalTime libraryOpenTime, String updateTimestamp, String userId) {
        this.active = active;
        this.book = book;
        this.bookId = bookId;
        this.createTimestamp = createTimestamp;
        this.dueDate = dueDate;
        this.id = id;
        this.libraryAddress = libraryAddress;
        this.libraryCloseTime = libraryCloseTime;
        this.libraryId = libraryId;
        this.libraryName = libraryName;
        this.libraryOpenTime = libraryOpenTime;
        this.updateTimestamp = updateTimestamp;
        this.userId = userId;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }


    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }


    public String getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(String createTimestamp) {
        this.createTimestamp = createTimestamp;
    }


    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLibraryAddress() {
        return libraryAddress;
    }

    public void setLibraryAddress(String libraryAddress) {
        this.libraryAddress = libraryAddress;
    }


    public LocalTime getLibraryCloseTime() {
        return libraryCloseTime;
    }

    public void setLibraryCloseTime(LocalTime libraryCloseTime) {
        this.libraryCloseTime = libraryCloseTime;
    }


    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }


    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }


    public LocalTime getLibraryOpenTime() {
        return libraryOpenTime;
    }

    public void setLibraryOpenTime(LocalTime libraryOpenTime) {
        this.libraryOpenTime = libraryOpenTime;
    }


    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
