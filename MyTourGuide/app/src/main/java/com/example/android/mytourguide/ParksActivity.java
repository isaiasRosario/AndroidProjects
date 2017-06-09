package com.example.android.mytourguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by irosaro on 8/31/16.
 */
public class ParksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parks);

        // Array list for storing parks locations.
        ArrayList<LocationInfo> parks = new ArrayList<LocationInfo>();

        // Adding location info for parks to array list
        parks.add(new LocationInfo(getString(R.string.park_name1),getString(R.string.park_add1),getString(R.string.park_num1),R.drawable.parks_icon));
        parks.add(new LocationInfo(getString(R.string.park_name2),getString(R.string.park_add2),getString(R.string.park_num2),R.drawable.parks_icon));
        parks.add(new LocationInfo(getString(R.string.park_name3),getString(R.string.park_add3),getString(R.string.park_num3),R.drawable.parks_icon));
        parks.add(new LocationInfo(getString(R.string.park_name4),getString(R.string.park_add4),getString(R.string.park_num4),R.drawable.parks_icon));
        parks.add(new LocationInfo(getString(R.string.park_name5),getString(R.string.park_add5),getString(R.string.park_num5),R.drawable.parks_icon));

        // Load parks array list into customer adapter
        LocationAddapter itemsAdapter = new LocationAddapter(this, parks);

        // Set up listview component
        ListView listView = (ListView) findViewById(R.id.list);

        //Set customer adapter to listview
        listView.setAdapter(itemsAdapter);
    }
}
