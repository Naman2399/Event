package com.example.myapplication.Data;

public class Event_desc_data {
    private String event_name , date , desc , time ;

    public Event_desc_data(){}

    public Event_desc_data(String event_name, String date, String desc, String time) {
        this.event_name = event_name;
        this.date = date;
        this.desc = desc;
        this.time = time;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
