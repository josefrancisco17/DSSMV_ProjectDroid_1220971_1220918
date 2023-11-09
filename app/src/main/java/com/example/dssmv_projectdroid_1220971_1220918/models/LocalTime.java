package com.example.dssmv_projectdroid_1220971_1220918.models;

public class LocalTime {
    private int hour;
    private int minute;
    private int second;
    private int nano;

    public LocalTime(int hour, int minute, int second, int nano) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.nano = nano;
    }

    public LocalTime(String s) {
        String[] timeComponents = s.split(":");
        this.hour = Integer.parseInt(timeComponents[0]);
        this.minute = Integer.parseInt(timeComponents[1]);
        this.second = Integer.parseInt(timeComponents[2]);
        int nano = 0;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getNano() {
        return nano;
    }

    public void setNano(int nano) {
        this.nano = nano;
    }
}
