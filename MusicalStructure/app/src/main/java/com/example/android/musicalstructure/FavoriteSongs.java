package com.example.android.musicalstructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FavoriteSongs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_songs);

        //Find and set the button views for each category
        Button favoriteHomeButton = (Button)findViewById(R.id.favoriteHomeButton);
        Button favoriteNowPlayingButton = (Button)findViewById(R.id.favoriteNowPlayingButton);

        //Set click listener for the now playing activity
        //create and start explicit intent to open NowPlaying
        favoriteNowPlayingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nowPlayingIntent = new Intent(FavoriteSongs.this, NowPlaying.class);

                startActivity(nowPlayingIntent);
                finish();
            }
        });

        //Set click listener for the to close activity
        favoriteHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
