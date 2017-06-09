package com.isaiasrosario.izzysmovieapp;

// MovieData info object for grid items

import java.io.Serializable;

public class MovieData implements Serializable {

    // MovieData Object values

    String movieTitle;
    String moviePoster;
    String movieOverview;
    String movieVoteAverage;
    String movieReleaseDate;

    public MovieData(String _title, String _poster, String _overview, String _voteAverage, String _releaseDate) {

        this.movieTitle = _title;
        this.moviePoster = _poster;
        this.movieOverview = _overview;
        this.movieVoteAverage = _voteAverage;
        this.movieReleaseDate = _releaseDate;
    }
}
