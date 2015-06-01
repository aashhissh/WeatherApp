package com.example.ashish.leftshiftchallenge;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class CityWeatherSnippetActivity extends ActionBarActivity {

    private ListView listView;
    private CityWeatherSnippetAdapter cityWeatherSnippetAdapter;
    private ArrayList<CityOneDayWeatherData> cityOneDayWeatherDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather_snippet);

        Intent intent = getIntent();
        cityOneDayWeatherDatas = (ArrayList<CityOneDayWeatherData>) intent.getSerializableExtra("cityDataList");
        initComponent();
    }

    private void initComponent(){
        listView = (ListView) findViewById(R.id.listView);
        cityWeatherSnippetAdapter = new CityWeatherSnippetAdapter(getApplicationContext(), cityOneDayWeatherDatas);
        listView.setAdapter(cityWeatherSnippetAdapter);
        listView.setOnItemClickListener(list_item_click_listner);
    }

    private ListView.OnItemClickListener list_item_click_listner = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(), CityWeatherForecastActivity.class);
            intent.putExtra("cityId", cityOneDayWeatherDatas.get(i).getId());
            startActivity(intent);
        }
    };

}
