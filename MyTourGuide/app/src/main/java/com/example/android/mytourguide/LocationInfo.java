package com.example.android.mytourguide;

/**
 * Created by irosaro on 8/31/16.
 */
public class LocationInfo {

    //Custom class object for tour locations


    // Strings for the location info
    private String mLocationName;

    private String mLocationAddress;

    private String mLocationNumber;

    private int mLocationImageUrl;

    // custom constructor for adding location object
    public LocationInfo(String name, String address, String number, int imgUrl){
        mLocationName = name;
        mLocationAddress = address;
        mLocationNumber = number;
        mLocationImageUrl = imgUrl;
    }

    // Location info get methods
    public String getLocationName(){
        return mLocationName;
    }

    public String getLocationAddress(){
        return mLocationAddress;
    }

    public String getLocationNumber(){
        return mLocationNumber;
    }

    public int getLocationImageUrl(){
        return mLocationImageUrl;
    }
}
