package com.example.android.musicalstructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NowPlaying extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        //Find and set the button views for each category
        Button nowPlayingHomeButton = (Button)findViewById(R.id.nowPlayingHomeButton);
        Button nowPlayingGenreButton = (Button)findViewById(R.id.nowPlayingGenreButton);

        //Set click listener for the now playing activity
        //create and start explicit intent to open NowPlaying
        nowPlayingGenreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent musicGenreIntent = new Intent(NowPlaying.this, MusicGenre.class);

                startActivity(musicGenreIntent);
                finish();
            }
        });

        //Set click listener for the to close activity
        nowPlayingHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
