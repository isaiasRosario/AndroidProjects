package com.example.android.mytourguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by irosaro on 8/31/16.
 */
public class RestaurantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        // Array list for storing restaurants locations.
        ArrayList<LocationInfo> restaurants = new ArrayList<LocationInfo>();

        // Adding location info for restaurants to array list
        restaurants.add(new LocationInfo(getString(R.string.rest_name1),getString(R.string.rest_add1),getString(R.string.rest_num1),R.drawable.rest_icon));
        restaurants.add(new LocationInfo(getString(R.string.rest_name2),getString(R.string.rest_add2),getString(R.string.rest_num2),R.drawable.rest_icon));
        restaurants.add(new LocationInfo(getString(R.string.rest_name3),getString(R.string.rest_add3),getString(R.string.rest_num3),R.drawable.rest_icon));
        restaurants.add(new LocationInfo(getString(R.string.rest_name4),getString(R.string.rest_add4),getString(R.string.rest_num4),R.drawable.rest_icon));
        restaurants.add(new LocationInfo(getString(R.string.rest_name5),getString(R.string.rest_add5),getString(R.string.rest_num5),R.drawable.rest_icon));

        // Load restaurants array list into customer adapter
        LocationAddapter itemsAdapter = new LocationAddapter(this, restaurants);

        // Set up listview component
        ListView listView = (ListView) findViewById(R.id.list);

        //Set customer adapter to listview
        listView.setAdapter(itemsAdapter);
    }
}
