package com.example.ashish.leftshiftchallenge;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by ashish on 26-05-2015.
 */
public class Coord implements Serializable{

    @Expose
    private Long lon;
    @Expose
    private Long lat;

    public Long getLon() {
        return lon;
    }

    public void setLon(Long lon) {
        this.lon = lon;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

}
