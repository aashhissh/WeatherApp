package com.example.ashish.leftshiftchallenge;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ashish on 29-05-2015.
 */
public class CityWeatherForecastAdapter extends ArrayAdapter<WeatherForecastData>{

    private Context context;
    private CityWeatherForecastData cityWeatherForecastData;
    private ImageView ivItemWeather;
    private TextView tvItemDate;
    private Date date;
    private Calendar cal;

    public CityWeatherForecastAdapter(Context context, CityWeatherForecastData cityWeatherForecastData){
        super(context, R.layout.weather_date_list_item, cityWeatherForecastData.getList()   );
        this.context = context;
        this.cityWeatherForecastData = cityWeatherForecastData;
        cal = Calendar.getInstance(Locale.ENGLISH);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.weather_date_list_item, null);
        initComponent(view);
        ivItemWeather.setImageResource(getContext().getResources().getIdentifier("drawable/a" + cityWeatherForecastData.getList().get(position).getWeather().get(0).getIcon(), null, getContext().getPackageName()));
        cal.setTimeInMillis(cityWeatherForecastData.getList().get(position).getDt()*1000);
        tvItemDate.setText(DateFormat.format("dd-MM-yyyy", cal).toString());
        return view;
    }

    public void initComponent(View view){
        ivItemWeather = (ImageView) view.findViewById(R.id.ivItemWeather);
        tvItemDate = (TextView) view.findViewById(R.id.tvItemDate);
    }

}
