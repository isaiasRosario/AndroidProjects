package com.example.android.musicalstructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ArtistInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_info);

        //Find and set the button views for each category
        Button artistHomeButton = (Button)findViewById(R.id.artistHomeButton);
        Button artistNowPlayingButton = (Button)findViewById(R.id.artistNowPlayingButton);

        //Set click listener for the now playing activity
        //create and start explicit intent to open NowPlaying
        artistNowPlayingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nowPlayingIntent = new Intent(ArtistInfo.this, NowPlaying.class);

                startActivity(nowPlayingIntent);
                finish();
            }
        });

        //Set click listener for the to close activity
        artistHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
