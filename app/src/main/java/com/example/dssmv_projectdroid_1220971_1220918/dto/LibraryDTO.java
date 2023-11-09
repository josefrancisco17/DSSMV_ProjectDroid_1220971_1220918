package com.example.dssmv_projectdroid_1220971_1220918.dto;

import com.example.dssmv_projectdroid_1220971_1220918.models.LocalTime;

public class LibraryDTO {
    private String address;
    private LocalTime closeTime;
    private String id;
    private String name;
    private boolean open;
    private String openDays;
    private String openStatement;
    private LocalTime openTime;

    public LibraryDTO(String address, LocalTime closeTime, String id, String name, boolean open, String openDays, String openStatement, LocalTime openTime) {
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


    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
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


    public boolean isOpen() {
        return open;
    }


    public void setOpen(boolean open) {
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

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }
}
