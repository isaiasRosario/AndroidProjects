package com.example.android.mytourguide;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by irosaro on 8/31/16.
 */
public class LocationAddapter extends ArrayAdapter<LocationInfo> {

    //custom Location adapter constructor
    public LocationAddapter(Activity context, ArrayList<LocationInfo> locations){
        super(context,0,locations);
    }

    // Components for the list item set up using getView customer adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocationInfo currentLocationInfo = getItem(position);

        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.textView);
        nameTextView.setText(currentLocationInfo.getLocationName());

        TextView numberTextView = (TextView) listItemView.findViewById(R.id.textView2);
        numberTextView.setText(currentLocationInfo.getLocationNumber());

        TextView addressTextView = (TextView) listItemView.findViewById(R.id.textView3);
        addressTextView.setText(currentLocationInfo.getLocationAddress());

        ImageView locationImage = (ImageView) listItemView.findViewById(R.id.imageView);
        Picasso.with(getContext()).load(currentLocationInfo.getLocationImageUrl()).resize(200,200).into(locationImage);

        return listItemView;
    }
}
