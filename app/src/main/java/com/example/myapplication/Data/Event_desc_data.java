package com.example.myapplication.Data;

public class Event_desc_data {
    private String Date , Descr , Event_Name , Key , Speakers , Time;

    public Event_desc_data(){}

    public Event_desc_data(String date, String descr, String event_Name, String key, String speakers, String time) {
        Date = date;
        Descr = descr;
        Event_Name = event_Name;
        Key = key;
        Speakers = speakers;
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }

    public String getEvent_Name() {
        return Event_Name;
    }

    public void setEvent_Name(String event_Name) {
        Event_Name = event_Name;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getSpeakers() {
        return Speakers;
    }

    public void setSpeakers(String speakers) {
        Speakers = speakers;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
