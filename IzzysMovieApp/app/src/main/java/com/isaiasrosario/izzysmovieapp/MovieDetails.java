package com.isaiasrosario.izzysmovieapp;

// MovieDetails Activity opens up after grid item is clicked

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            //Set container to MovieDetailFragment fragment
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieDetailFragment())
                    .commit();
        }
    }

    // MovieDetailFragment place holder for MovieDetails activity
    public static class MovieDetailFragment extends Fragment {

        public MovieDetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Get intent data object that was passed when item was clicked.
            Intent movieIntent = getActivity().getIntent();
            MovieData movieData = (MovieData) movieIntent.getSerializableExtra("movieData");

            // Set root view for the detail fragment.
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            // Poster image for the custom adapter.
            ImageView moviePoster = (ImageView) rootView.findViewById(R.id.movie_poster_detail);
            // base url for the image retrived fromt he movie db.
            String posterUrl = "http://image.tmdb.org/t/p/w500/";
            // Use picasso library to load and cache images.
            Picasso.with(getContext()).load(posterUrl + movieData.moviePoster).into(moviePoster);

            // Textview for movie title in the detail view
            TextView movieTitle = (TextView) rootView.findViewById(R.id.movie_title_detail);
            movieTitle.setText(movieData.movieTitle);

            // Textview for movie release date in the detail view
            TextView movieRelease = (TextView) rootView.findViewById(R.id.release_date_detail);
            movieRelease.setText(getString(R.string.release_date_text) + movieData.movieReleaseDate);

            //Textview for movie vote average in the detail view
            TextView movieAverage = (TextView) rootView.findViewById(R.id.vote_average__detail);
            movieAverage.setText(getString(R.string.vote_average_text) + movieData.movieVoteAverage);

            // Textview for the moview overview inthe detial view
            TextView movieOverview = (TextView) rootView.findViewById(R.id.movie_overview_detail);
            movieOverview.setText(movieData.movieOverview);

            return rootView;
        }

    }
}
