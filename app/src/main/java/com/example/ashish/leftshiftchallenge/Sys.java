package com.example.ashish.leftshiftchallenge;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by ashish on 27-05-2015.
 */
public class Sys implements Serializable{

    @Expose
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
