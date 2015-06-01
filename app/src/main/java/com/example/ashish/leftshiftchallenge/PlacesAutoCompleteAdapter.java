package com.example.ashish.leftshiftchallenge;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by ashish on 27-05-2015.
 */
class PlacesAutoCompleteAdapter extends ArrayAdapter<CityOneDayWeatherData> implements Filterable {

    private ArrayList<CityOneDayWeatherData> resultList;
    private TextView tvWeatherCondition,tvCityName;
    private ImageView ivWeather;
    private ProgressBar mLoadingIndicator;

    private Context mContext;
    private int mResource;

    private PlaceAPI mPlaceAPI = new PlaceAPI();

    public PlacesAutoCompleteAdapter(Context context, int resource, ProgressBar progressBar) {
        super(context, resource);
        mContext = context;
        mResource = resource;
        mLoadingIndicator = progressBar;
    }

    @Override
    public int getCount() {
        // Last item will be the footer
        return resultList.size();
    }

    @Override
    public CityOneDayWeatherData getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.autocomplete_list_item, null);
        initComponent(view);

        CityOneDayWeatherData element = resultList.get(position);
        Weather weather = element.getWeather().get(0);
        ivWeather.setImageResource(getContext().getResources().getIdentifier("drawable/a" + weather.getIcon(), null, getContext().getPackageName()));
        tvWeatherCondition.setText(element.getMain().getTemp()+" \u2103 "+weather.getMain());
        tvCityName.setText(element.getName()+","+element.getSys().getCountry());
        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected Filter.FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                Handler refresh = new Handler(Looper.getMainLooper());
                refresh.post(new Runnable() {
                    public void run() {
                        mLoadingIndicator.setVisibility(View.VISIBLE);
                    }
                });

                if (constraint != null) {
                    try {
                        String[] cons = constraint.toString().split(";");
                        resultList = mPlaceAPI.autocomplete(cons[cons.length-1]);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
                Handler refresh = new Handler(Looper.getMainLooper());
                refresh.post(new Runnable() {
                    public void run() {
                        mLoadingIndicator.setVisibility(View.GONE);
                    }
                });
            }
        };

        return filter;
    }

    private void initComponent(View view){
        tvWeatherCondition = (TextView) view.findViewById(R.id.tvWeatherCondition);
        tvCityName = (TextView) view.findViewById(R.id.tvCityName);
        ivWeather = (ImageView) view.findViewById(R.id.ivWeather);
    }

}
