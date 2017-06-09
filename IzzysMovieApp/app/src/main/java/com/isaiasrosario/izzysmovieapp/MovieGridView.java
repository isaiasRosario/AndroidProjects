package com.isaiasrosario.izzysmovieapp;

// MovieGridView is a fragment with a grid view for the main activity

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieGridView extends Fragment {

    // Array list for the MovieData objetcs
    private ArrayList<MovieData> mMovieArray = new ArrayList<>();
    // Grid view value
    private GridView mMovieGrid;

    public MovieGridView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate view for movie grid view fragment
        View rootView = inflater.inflate(R.layout.movie_grid_view, container, false);

        // Cats grid view to layout
        mMovieGrid = (GridView) rootView.findViewById(R.id.movieGrid);
        //Set on item click listener
        mMovieGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent opens up detail view
                Intent intent = new Intent(getActivity(), MovieDetails.class);
                // Pass movieData object that was clicked on
                intent.putExtra("movieData", mMovieArray.get(position));
                // Start intent
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // call update grid on fragment start
        updateGrid();
    }

    // Update grid view method
    public void updateGrid() {
        // Get sort key from shared preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort = prefs.getString(getString(R.string.sort_key),
                getString(R.string.sort_popular_value));
        // Clear movie array to avoid duplicates.
        mMovieArray.clear();
        // Call AsyncTask method passing sort value
        new GetMovieData().execute(sort);
    }

    // Get movie data async task method
    public class GetMovieData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            // Set up http connection values
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieDataJSON = null;

            //API Uri set up
            String baseURL = "http://api.themoviedb.org/3/movie/";
            String sortBy = params[0]; // or top_rated
            String apiKey = "/?api_key=c90b7db1d35b08bba937c2d3eaa9be58";

            // Call and parse API data
            try {
                URL apiURL = new URL(Uri.parse(baseURL + sortBy + apiKey).toString());

                urlConnection = (HttpURLConnection) apiURL.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                movieDataJSON = buffer.toString();

                JSONObject movieData = new JSONObject(movieDataJSON);
                JSONArray movieDataArray = movieData.getJSONArray("results");

                // Loop through JSON data and add objects to mMovieArray list

                for (int i = 0; i < movieDataArray.length(); i++) {

                    JSONObject movieObject = movieDataArray.getJSONObject(i);
                    String posterPath = movieObject.getString("poster_path");
                    String overview = movieObject.getString("overview");
                    String releaseDate = movieObject.getString("release_date");
                    String title = movieObject.getString("title");
                    String voteAverage = movieObject.getString("vote_average");
                    mMovieArray.add(new MovieData(title, posterPath, overview, voteAverage, releaseDate));

                }

            } catch (IOException e) {
                Log.e("MovieGridView", "Error ", e);

                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MovieGridView", "Error closing stream", e);
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Customer adapter value set up
            MovieDataAdapter mMovieDataAdapter = new MovieDataAdapter(getActivity(), mMovieArray);
            // Set movie grid adapter to mMovieAdapter
            mMovieGrid.setAdapter(mMovieDataAdapter);

        }
    }
}
