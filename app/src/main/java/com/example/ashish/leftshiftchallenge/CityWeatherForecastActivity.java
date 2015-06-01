package com.example.ashish.leftshiftchallenge;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.lucasr.twowayview.TwoWayView;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Locale;

public class CityWeatherForecastActivity extends ActionBarActivity {

    private TextView tvCityName, tvMinTemprature, tvMaxTemprature, tvDate, tvMain, tvDescription,
            tvMorning, tvDay, tvEvening, tvNight, tvPressure, tvHumidity, tvWind, tvDegree, tvClouds ;
    private ImageView ivWeather;
    private TwoWayView listview;
    private Long cityId;
    private CityWeatherForecastData cityWeatherForecastData;
    private File cacheDir;
    private ProgressDialog progress;
    private LatLng coordinates;
    private static final String PLACES_API_BASE = "http://api.openweathermap.org/data/2.5";
    private static final String TYPE = "/forecast/daily";
    private static final String APPID = "24ebdfbaa74292d369b93ebf56f748fc";
    private static final String UNIT = "metric";
    private static final String COUNT = "14";
    private String url;
    private int type = 0;
    private CityWeatherForecastAdapter cityWeatherForecastAdapter;
    private Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather_forecast);

        Intent intent = getIntent();
        if(intent.hasExtra("cityId")){
            cityId = intent.getLongExtra("cityId", -1L);
            type = 0;
        }else{
            coordinates = intent.getParcelableExtra("currentCoordinates");
            type = 1;
        }
        initComponent();
        new MyAsyncTask().execute();
    }

    private void initComponent(){
        tvCityName = (TextView) findViewById(R.id.tvCityName);
        tvMinTemprature = (TextView) findViewById(R.id.tvMinTemprature);
        tvMaxTemprature = (TextView) findViewById(R.id.tvMaxTemprature);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvMain = (TextView) findViewById(R.id.tvMain);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvMorning = (TextView) findViewById(R.id.tvMorning);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvEvening = (TextView) findViewById(R.id.tvEvening);
        tvNight = (TextView) findViewById(R.id.tvNight);
        tvPressure = (TextView) findViewById(R.id.tvPressure);
        tvHumidity = (TextView) findViewById(R.id.tvHumidity);
        tvWind = (TextView) findViewById(R.id.tvWind);
        tvDegree = (TextView) findViewById(R.id.tvDegree);
        tvClouds = (TextView) findViewById(R.id.tvClouds);
        ivWeather = (ImageView) findViewById(R.id.ivWeather);
        listview = (TwoWayView) findViewById(R.id.listView);
        listview.setOnItemClickListener(horizontal_list_item_click_listner);

        cacheDir = getApplicationContext().getDir("service_api_cache", Context.MODE_PRIVATE);
        cal = Calendar.getInstance(Locale.ENGLISH);

        progress = new ProgressDialog(this);
        progress.setTitle("Stay with us !");
        progress.setMessage("Forecasting Weather...  :P");
        progress.show();

        StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE);
        sb.append("?APPID=" + APPID);
        sb.append("&units=" + UNIT);
        sb.append("&cnt=" + COUNT);
        try {
            if (type == 0) {
                sb.append("&id=" + URLEncoder.encode(String.valueOf(cityId), "utf8"));
            } else {
                sb.append("&lat=" + URLEncoder.encode(String.valueOf(coordinates.latitude), "utf8"));
                sb.append("&lon=" + URLEncoder.encode(String.valueOf(coordinates.longitude), "utf8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url = sb.toString();
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {
        String responseText = null;
        OkHttpClient client;

        @Override
        protected Double doInBackground(String... params) {
            postData();
            return null;
        }

        protected void onPostExecute(Double result) {
            if (responseText != null) {
                JsonParser parser = new JsonParser();
                JsonObject object = (JsonObject) parser.parse(responseText);
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                cityWeatherForecastData = gson.fromJson(object, new TypeToken<CityWeatherForecastData>(){
                }.getType());
                cityWeatherForecastAdapter = new CityWeatherForecastAdapter(getApplicationContext(), cityWeatherForecastData);
                listview.setAdapter(cityWeatherForecastAdapter);
                tvCityName.setText(cityWeatherForecastData.getCity().getName()+","+cityWeatherForecastData.getCity().getCountry());
                updateData(cityWeatherForecastData.getList().get(0));
                progress.dismiss();
            } else {
                finish();
            }
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        public void postData() {
            client = new OkHttpClient();
            try {
                CacheResponse();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                responseText = run(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String run(String sURL) throws IOException {
            Request request = new Request.Builder()
                    .url(sURL)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        public void CacheResponse() throws Exception {
            //File cacheDir = context.getDir("service_api_cache", Context.MODE_PRIVATE);
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(cacheDir, cacheSize);

            client = new OkHttpClient();
            client.setCache(cache);
        }

    }

    private TwoWayView.OnItemClickListener horizontal_list_item_click_listner = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            updateData(cityWeatherForecastData.getList().get(i));
        }
    };

    private void updateData(WeatherForecastData weatherForecastData){
        ivWeather.setImageResource(getApplicationContext().getResources().getIdentifier("drawable/a" + weatherForecastData.getWeather().get(0).getIcon(), null, getApplicationContext().getPackageName()));
        cal.setTimeInMillis(weatherForecastData.getDt() * 1000);
        tvDate.setText(DateFormat.format("dd-MM-yyyy", cal).toString());
        tvMain.setText(weatherForecastData.getWeather().get(0).getMain());
        tvDescription.setText(weatherForecastData.getWeather().get(0).getDescription());
        tvMinTemprature.setText(String.valueOf(weatherForecastData.getTemp().getMin()));
        tvMaxTemprature.setText(String.valueOf(weatherForecastData.getTemp().getMax()));
        tvMorning.setText(String.valueOf(weatherForecastData.getTemp().getMorn()));
        tvDay.setText(String.valueOf(weatherForecastData.getTemp().getDay()));
        tvEvening.setText(String.valueOf(weatherForecastData.getTemp().getEve()));
        tvNight.setText(String.valueOf(weatherForecastData.getTemp().getNight()));
        tvPressure.setText(String.valueOf(weatherForecastData.getPressure()));
        tvHumidity.setText(String.valueOf(weatherForecastData.getHumidity()));
        tvWind.setText(String.valueOf(weatherForecastData.getSpeed()));
        tvDegree.setText(String.valueOf(weatherForecastData.getDeg()));
        tvClouds.setText(String.valueOf(weatherForecastData.getClouds()));
    }

}
