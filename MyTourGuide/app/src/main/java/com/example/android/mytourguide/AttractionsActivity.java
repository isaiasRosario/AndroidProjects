package com.example.android.mytourguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by irosaro on 8/30/16.
 */
public class AttractionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        // Array list for storing attraction locations.
        ArrayList<LocationInfo> attractions = new ArrayList<LocationInfo>();

        // Adding location info for attractions to array list
        attractions.add(new LocationInfo(getString(R.string.att_name1),getString(R.string.att_add1),getString(R.string.att_num1),R.drawable.attraction_icon1));
        attractions.add(new LocationInfo(getString(R.string.att_name2),getString(R.string.att_add2),getString(R.string.att_num2),R.drawable.attraction_icon2));
        attractions.add(new LocationInfo(getString(R.string.att_name3),getString(R.string.att_add3),getString(R.string.att_num3),R.drawable.attraction_icon3));
        attractions.add(new LocationInfo(getString(R.string.att_name4),getString(R.string.att_add4),getString(R.string.att_num4),R.drawable.attraction_icon4));
        attractions.add(new LocationInfo(getString(R.string.att_name5),getString(R.string.att_add5),getString(R.string.att_num5),R.drawable.attraction_icon5));

        // Load attractions array list into customer adapter
        LocationAddapter itemsAdapter = new LocationAddapter(this, attractions);

        // Set up listview component
        ListView listView = (ListView) findViewById(R.id.list);

        //Set customer adapter to listview
        listView.setAdapter(itemsAdapter);
    }
}
