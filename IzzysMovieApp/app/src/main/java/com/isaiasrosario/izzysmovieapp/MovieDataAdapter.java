package com.isaiasrosario.izzysmovieapp;

// MovieDataAdapter custom array adapter for the movie grid view

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDataAdapter extends ArrayAdapter<MovieData> {

    // Customer array adapter that uses MovieData object to populate the data for the gridview.

    public MovieDataAdapter(Activity context, List<MovieData> movieData) {
        super(context, 0, movieData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item_layout, parent, false);
        }

        if (convertView != null){
            MovieData movieData = getItem(position);
            // Poster image for the custom adapter.
            ImageView moviePoster = (ImageView) convertView.findViewById(R.id.movie_poster_image);
            // base url for the image retrieved from the movie db.
            String posterUrl = "http://image.tmdb.org/t/p/w342/";
            // Use picasso library to load and cache images.
            Picasso.with(getContext()).load(posterUrl + movieData.moviePoster).placeholder(R.mipmap.ic_launcher).into(moviePoster);

            // Textview for title that will overlay the poster image for each grid item.
            TextView movieTitle = (TextView) convertView.findViewById(R.id.movie_title_text);
            movieTitle.setText(movieData.movieTitle);
        }

        return convertView;
    }
}
