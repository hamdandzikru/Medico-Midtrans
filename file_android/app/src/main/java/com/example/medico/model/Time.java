package com.example.medico.model;

public class Time {
    private int hour;
    private int minutes;
    private int second;

    public Time() {
    }

    public Time(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    public Time(int hour, int minutes, int second) {
        this.hour = hour;
        this.minutes = minutes;
        this.second = second;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
