package com.example.android.musicalstructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MusicGenre extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_genre);


        //Find and set the button views for each category
        Button genreHomeButton = (Button)findViewById(R.id.genreHomeButton);
        Button genreNowPlayingButton = (Button)findViewById(R.id.genreNowPlayingButton);

        //Set click listener for the now playing activity
        //create and start explicit intent to open NowPlaying
        genreNowPlayingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nowPlayingIntent = new Intent(MusicGenre.this, NowPlaying.class);

                startActivity(nowPlayingIntent);
                finish();
            }
        });

        //Set click listener for the to close activity
        genreHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
