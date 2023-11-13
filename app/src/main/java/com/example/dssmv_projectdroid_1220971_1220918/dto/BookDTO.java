package com.example.dssmv_projectdroid_1220971_1220918.dto;

import com.example.dssmv_projectdroid_1220971_1220918.models.Author;
import com.example.dssmv_projectdroid_1220971_1220918.models.CoverUrls;

import java.util.List;

public class BookDTO {
    private List<Author> authors;
    private String byStatement;
    private CoverUrls cover;
    private String description;
    private String isbn;
    private int numberOfPages;
    private String publishDate;
    private List<String> subjectPeople;
    private List<String> subjectPlaces;
    private List<String> subjectTimes;
    private List<String> subjects;
    private String title;

    public BookDTO(List<Author> authors, String byStatement, CoverUrls cover, String description, String isbn, int numberOfpages, String publishDate, List<String> subjectPeople, List<String> subjectPlaces, List<String> subjectTimes, List<String> subjects, String title) {
        this.authors = authors;
        this.byStatement = byStatement;
        this.cover = cover;
        this.description = description;
        this.isbn = isbn;
        this.numberOfPages = numberOfpages;
        this.publishDate = publishDate;
        this.subjectPeople = subjectPeople;
        this.subjectPlaces = subjectPlaces;
        this.subjectTimes = subjectTimes;
        this.subjects = subjects;
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }


    public String getByStatement() {
        return byStatement;
    }

    public void setByStatement(String byStatement) {
        this.byStatement = byStatement;
    }


    public CoverUrls getCover() {
        return cover;
    }

    public void setCover(CoverUrls cover) {
        this.cover = cover;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }


    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }


    public List<String> getSubjectPeople() {
        return subjectPeople;
    }

    public void setSubjectPeople(List<String> subjectPeople) {
        this.subjectPeople = subjectPeople;
    }


    public List<String> getSubjectPlaces() {
        return subjectPlaces;
    }

    public void setSubjectPlaces(List<String> subjectPlaces) {
        this.subjectPlaces = subjectPlaces;
    }


    public List<String> getSubjectTimes() {
        return subjectTimes;
    }

    public void setSubjectTimes(List<String> subjectTimes) {
        this.subjectTimes = subjectTimes;
    }


    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

