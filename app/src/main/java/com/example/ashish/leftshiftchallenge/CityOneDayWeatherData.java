package com.example.ashish.leftshiftchallenge;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashish on 27-05-2015.
 */
public class CityOneDayWeatherData implements Serializable{

    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private Main main = new Main();
    @Expose
    private Long dt;
    @Expose
    private Wind wind = new Wind();
    @Expose
    private Sys sys = new Sys();
    @Expose
    private List<Weather> weather = new ArrayList<Weather>();
    @Expose
    private Cloud clouds = new Cloud();

    public Cloud getClouds() {
        return clouds;
    }

    public void setClouds(Cloud clouds) {
        this.clouds = clouds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
