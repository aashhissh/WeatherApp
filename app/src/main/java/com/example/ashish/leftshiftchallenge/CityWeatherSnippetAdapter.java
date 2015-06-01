package com.example.ashish.leftshiftchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashish on 27-05-2015.
 */
public class CityWeatherSnippetAdapter extends ArrayAdapter<CityOneDayWeatherData> {

    private Context context;
    private ArrayList<CityOneDayWeatherData> cityOneDayWeatherDatas;
    private ImageView ivWeather;
    private TextView tvTemprature, tvMinTemprature, tvMaxTemprature,
            tvCityName, tvDescription, tvWind, tvPressure, tvCloud, tvHumidity;

    public CityWeatherSnippetAdapter(Context context, ArrayList<CityOneDayWeatherData> cityOneDayWeatherDatas){
        super(context, R.layout.city_weather_snippet_list_item, cityOneDayWeatherDatas);
        this.context = context;
        this.cityOneDayWeatherDatas = cityOneDayWeatherDatas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = layoutInflater.inflate(R.layout.city_weather_snippet_list_item, null);
        initComponent(view);

        CityOneDayWeatherData cityOneDayWeatherData = cityOneDayWeatherDatas.get(position);
        populateData(cityOneDayWeatherData);
        return view;
    }

    private void initComponent(View view){
        ivWeather = (ImageView) view.findViewById(R.id.ivWeather);
        tvTemprature = (TextView) view.findViewById(R.id.tvTemprature);
        tvMinTemprature = (TextView) view.findViewById(R.id.tvMinTemprature);
        tvMaxTemprature = (TextView) view.findViewById(R.id.tvMaxTemprature);
        tvCityName = (TextView) view.findViewById(R.id.tvCityName);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        tvWind = (TextView) view.findViewById(R.id.tvWind);
        tvPressure = (TextView) view.findViewById(R.id.tvPressure);
        tvCloud = (TextView) view.findViewById(R.id.tvCloud);
        tvHumidity = (TextView) view.findViewById(R.id.tvHumidity);
    }

    private void populateData(CityOneDayWeatherData cityOneDayWeatherData){
        ivWeather.setImageResource(getContext().getResources().getIdentifier("drawable/a" + cityOneDayWeatherData.getWeather().get(0).getIcon(), null, getContext().getPackageName()));
        tvTemprature.setText(String.valueOf(cityOneDayWeatherData.getMain().getTemp()) + " \u2103");
        tvMinTemprature.setText(String.valueOf(cityOneDayWeatherData.getMain().getTemp_min()) + " \u2103");
        tvMaxTemprature.setText(String.valueOf(cityOneDayWeatherData.getMain().getTemp_max()) + " \u2103");
        tvCityName.setText(cityOneDayWeatherData.getName() + "," + cityOneDayWeatherData.getSys().getCountry());
        tvDescription.setText(cityOneDayWeatherData.getWeather().get(0).getDescription());
        tvWind.setText("Wind : " + cityOneDayWeatherData.getWind().getSpeed() + "m/s");
        tvPressure.setText("Press : "+cityOneDayWeatherData.getMain().getPressure()+"hpa");
        tvCloud.setText("Cloud : "+cityOneDayWeatherData.getClouds().getAll()+" %");
        tvHumidity.setText("humidity : "+cityOneDayWeatherData.getMain().getHumidity()+" %");
    }
}
