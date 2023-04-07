package com.example.myapplication.data;

public class User {
    private String sampleKey;
    private String sampleValue;

    public User(String sampleKey, String value) {
        this.sampleKey = sampleKey;
        this.sampleValue = value;
    }

    public String getSampleKey() {
        return sampleKey;
    }

    public String getSampleValue() {
        return sampleValue;
    }
}