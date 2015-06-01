package com.example.ashish.leftshiftchallenge;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashish on 27-05-2015.
 */
public class PlaceAPI {

    private static final String PLACES_API_BASE = "http://api.openweathermap.org/data/2.5";
    private static final String TYPE = "/find";
    private static final String APPID = "24ebdfbaa74292d369b93ebf56f748fc";
    private static final String UNIT = "metric";
    private OkHttpClient client;
    private String responseText;

    public ArrayList<CityOneDayWeatherData> autocomplete (String input) throws UnsupportedEncodingException {
        ArrayList<CityOneDayWeatherData> cityOneDayWeatherDataList = null;

        StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE);
        sb.append("?APPID=" + APPID);
        sb.append("&type=like");
        sb.append("&units=" + UNIT);
        sb.append("&q=" + URLEncoder.encode(input, "utf8"));

        client = new OkHttpClient();
        try {
            responseText = run(sb.toString());
            JsonParser parser = new JsonParser();
            JsonObject object = (JsonObject) parser.parse(responseText);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            cityOneDayWeatherDataList = gson.fromJson(object.get("list"), new TypeToken<List<CityOneDayWeatherData>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityOneDayWeatherDataList;
    }

    public String run(String sURL) throws IOException {
        Request request = new Request.Builder()
                .url(sURL)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
