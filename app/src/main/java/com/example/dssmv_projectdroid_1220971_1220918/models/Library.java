package com.example.dssmv_projectdroid_1220971_1220918.models;

public class Library {
    private String address;
    private String closeTime;
    private String id;
    private String name;
    private String open;
    private String openDays;
    private String openStatement;
    private String openTime;

    public Library(String address, String closeTime, String id, String name, String open, String openDays, String openStatement, String openTime) {
        this.address = address;
        this.closeTime = closeTime;
        this.id = id;
        this.name = name;
        this.open = open;
        this.openDays = openDays;
        this.openStatement = openStatement;
        this.openTime = openTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String isOpen() {
        return open;
    }


    public void setOpen(String open) {
        this.open = open;
    }


    public String getOpenDays() {
        return openDays;
    }

    public void setOpenDays(String openDays) {
        this.openDays = openDays;
    }

    public String getOpenStatement() {
        return openStatement;
    }

    public void setOpenStatement(String openStatement) {
        this.openStatement = openStatement;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }
}
