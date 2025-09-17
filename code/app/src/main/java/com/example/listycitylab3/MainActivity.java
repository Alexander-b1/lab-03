package com.example.listycitylab3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddOrEditCityFragment.AddOrEditCityDialogListner {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {
                "Edmonton", "Vancouver", "Toronto"
        };
        String[] provinces = {"AB", "BC", "ON"};

        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }
        
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            AddOrEditCityFragment.newInstance(new City())
                    .show(getSupportFragmentManager(), "Add City");
        });


        /*
         * Creates an onItemClickListner for the cityList. When a city is pressed,
         * it shows brings up a window to edit the City.
         */
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Fetches the selected city from the city adapter
                City city = cityAdapter.getItem(i);

                // Creates an instance of the AddOrEditCityFragment, with the current city
                AddOrEditCityFragment.newInstance(city)
                        .show(getSupportFragmentManager(), "Edit City");
            }
        });

    }


    @Override
    public void addOrEditCity(City city) {

        // If the city is already in the list, then we are editing,
        // and do not need to add to the list
        if (!dataList.contains(city)) {
            cityAdapter.add(city);
        }

        // Tells the city adapter that the underlying data has changed, so
        // it needs to refresh
        cityAdapter.notifyDataSetChanged();
    }
}