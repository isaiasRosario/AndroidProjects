package com.example.android.musicalstructure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find and set the button views for each category
        Button nowPlayingButton = (Button)findViewById(R.id.nowPlayingButton);
        Button musicGenreButton = (Button)findViewById(R.id.musicGenreButton);
        Button artistInfoButton = (Button)findViewById(R.id.artistInfoButton);
        Button favoriteSongsButton = (Button)findViewById(R.id.favortiteSongsButton);


        //Set click listener for the now playing activity
        //create and start explicit intent to open NowPlaying
        nowPlayingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nowPlayingIntent = new Intent(MainActivity.this, NowPlaying.class);

                startActivity(nowPlayingIntent);
            }
        });

        //Set click listener for the music genre activity
        //create and start explicit intent to open MusicGenre
        musicGenreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent musicGenreIntent = new Intent(MainActivity.this, MusicGenre.class);

                startActivity(musicGenreIntent);
            }
        });

        //Set click listener for the artist info activity
        //create and start explicit intent to open ArtistInfo
        artistInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent artistInfoIntent = new Intent(MainActivity.this, ArtistInfo.class);

                startActivity(artistInfoIntent);
            }
        });

        //Set click listener for the favorite songs activity
        //create and start explicit intent to open FavoriteSongs
        favoriteSongsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favSongIntent = new Intent(MainActivity.this, FavoriteSongs.class);

                startActivity(favSongIntent);
            }
        });

    }
}
