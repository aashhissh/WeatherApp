package com.example.ashish.leftshiftchallenge;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by ashish on 26-05-2015.
 */
public class City implements Serializable{
    @Expose
    private Long id;
    @Expose
    private String name;
}
