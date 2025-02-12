package com.example.dssmv_projectdroid_1220971_1220918.models;

import java.util.List;

public class TypeheadResponse {
    private List<String> authors;
    private List<String> subjects;
    private List<String> titles;

    public TypeheadResponse(List<String> authors, List<String> subjects, List<String> titles) {
        this.authors = authors;
        this.subjects = subjects;
        this.titles = titles;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }


    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }


    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }
}
