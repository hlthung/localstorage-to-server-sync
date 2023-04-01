package com.example.myapplication.data;

public class User {
    private long id;
    private String sampleKey;
    private String sampleValue;

    public User(long id, String sampleKey, String value) {
        this.id = id;
        this.sampleKey = sampleKey;
        this.sampleValue = value;
    }

    public long getId() {
        return id;
    }

    public String getSampleKey() {
        return sampleKey;
    }

    public String getSampleValue() {
        return sampleValue;
    }
}