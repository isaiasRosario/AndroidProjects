package com.example.android.mytourguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by irosaro on 8/31/16.
 */
public class BarsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bars);

        // Array list for storing bars locations.
        ArrayList<LocationInfo> bars = new ArrayList<LocationInfo>();

        // Adding location info for bars to array list
        bars.add(new LocationInfo(getString(R.string.bar_name1),getString(R.string.bar_add1),getString(R.string.bar_num1),R.drawable.bar_icon));
        bars.add(new LocationInfo(getString(R.string.bar_name2),getString(R.string.bar_add2),getString(R.string.bar_num2),R.drawable.bar_icon));
        bars.add(new LocationInfo(getString(R.string.bar_name3),getString(R.string.bar_add3),getString(R.string.bar_num3),R.drawable.bar_icon));
        bars.add(new LocationInfo(getString(R.string.bar_name4),getString(R.string.bar_add4),getString(R.string.bar_num4),R.drawable.bar_icon));
        bars.add(new LocationInfo(getString(R.string.bar_name5),getString(R.string.bar_add5),getString(R.string.bar_num5),R.drawable.bar_icon));

        // Load bars array list into customer adapter
        LocationAddapter itemsAdapter = new LocationAddapter(this, bars);

        // Set up listview component
        ListView listView = (ListView) findViewById(R.id.list);

        //Set customer adapter to listview
        listView.setAdapter(itemsAdapter);
    }
}
