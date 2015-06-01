package com.example.ashish.leftshiftchallenge;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashish on 27-05-2015.
 */
public class CityWeatherForecastData implements Serializable {

    @Expose
    private Cities city = new Cities();
    @Expose
    private List<WeatherForecastData> list = new ArrayList<WeatherForecastData>();

    public Cities getCity() {
        return city;
    }

    public void setCity(Cities city) {
        this.city = city;
    }

    public List<WeatherForecastData> getList() {
        return list;
    }

    public void setList(List<WeatherForecastData> list) {
        this.list = list;
    }
}
