package com.example.ashish.leftshiftchallenge;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

//24ebdfbaa74292d369b93ebf56f748fc
public class MainActivity extends ActionBarActivity{

    private ArrayList<CityOneDayWeatherData> cityOneDayWeatherDatas;
    private AutoCompleteTextView autoCompleteTextView;
    private File cacheDir;
    private static StringBuilder sb;
    private Button btFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityOneDayWeatherDatas = new ArrayList<CityOneDayWeatherData>();
        sb = new StringBuilder("");
        initComponent();
    }

    private void initComponent(){
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        final ArrayAdapter<CityOneDayWeatherData> cityOneDayWeatherDataArrayAdapter = new PlacesAutoCompleteAdapter(getApplicationContext(),R.layout.autocomplete_list_item, (ProgressBar) findViewById(R.id.mLoadingIndicator));
        autoCompleteTextView.setAdapter(cityOneDayWeatherDataArrayAdapter);

        btFinish = (Button) findViewById(R.id.btFinish);
        btFinish.setOnClickListener(finish_button_click_listner);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityOneDayWeatherData temp = cityOneDayWeatherDataArrayAdapter.getItem(position);
                cityOneDayWeatherDatas.add(temp);
                sb.append(temp.getName()+","+temp.getSys().getCountry()+";");
                Toast.makeText(getApplicationContext(), String.valueOf(temp.getId()), Toast.LENGTH_SHORT).show();
                autoCompleteTextView.setText(sb.toString());
                autoCompleteTextView.setSelection(autoCompleteTextView.getText().length());
            }
        });
    }

    private Button.OnClickListener finish_button_click_listner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(cityOneDayWeatherDatas.size() == 0){
                autoCompleteTextView.setError("Enter some valid city names.");
            }else{
                Intent intent = new Intent(getApplicationContext(), CityWeatherSnippetActivity.class);
                intent.putExtra("cityDataList", cityOneDayWeatherDatas);
                startActivity(intent);
            }
        }
    };
}
