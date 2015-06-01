package com.example.ashish.leftshiftchallenge;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by ashish on 27-05-2015.
 */
public class Weather implements Serializable{

    @Expose
    private String main;
    @Expose
    private String description;
    @Expose
    private String icon;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
