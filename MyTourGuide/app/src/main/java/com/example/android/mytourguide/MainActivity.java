package com.example.android.mytourguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //  onCLick method that is called when any button is touched
    @Override
    public void onClick(View v) {

        //Case method that checks for id and
        //calls an intent based on the button touched
        switch (v.getId()){

            case R.id.attractionsButton:
                Intent attractionIntent = new Intent(MainActivity.this, AttractionsActivity.class);
                startActivity(attractionIntent);
                break;

            case  R.id.barsButton:
                Intent barsIntent = new Intent(MainActivity.this, BarsActivity.class);
                startActivity(barsIntent);
                break;

            case  R.id.parksButton:
                Intent parksIntent = new Intent(MainActivity.this, ParksActivity.class);
                startActivity(parksIntent);
                break;

            case  R.id.restaurantsButton:
                Intent restaurantsIntent = new Intent(MainActivity.this, RestaurantsActivity.class);
                startActivity(restaurantsIntent);
                break;
        }

    }
}
