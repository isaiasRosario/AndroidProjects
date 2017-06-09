package com.example.android.newsyapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by irosaro on 9/8/16.
 */
public final class Utils {

    public static final String TAG = "UTILS_CLASS";

    public static ArrayList<Article> getNewsData(String urlRequest){
        // Create URL object
        URL url = createUrl(urlRequest);

        // Perform HTTP request to the URL and receive a JSON response
        String jsonResponse = "";
        try {
            jsonResponse = httpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, String.valueOf(R.string.respone_error), e);
        }

        return parseNewsArticlesJson(jsonResponse);
    }

    //create url for the response
    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(TAG, String.valueOf(R.string.url_error), exception);
            return null;
        }
        return url;
    }

    //http request method
    private static String httpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }

        } catch (IOException e) {

            Log.e(TAG, String.valueOf(R.string.request_error), e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    // Input and output stream method
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    // setting array list from json respone
    private static ArrayList<Article> parseNewsArticlesJson(String bookJSON) {
        ArrayList<Article> articles = new ArrayList<Article>();
        try {
            JSONObject baseJsonResponse = new JSONObject(bookJSON);
            JSONObject responseObject = baseJsonResponse.getJSONObject("response");
            JSONArray articleArray = responseObject.getJSONArray("results");

            if (articleArray.length() > 0) {

                for (int i = 0; i < articleArray.length(); i++) {
                    JSONObject firstArticle = articleArray.getJSONObject(i);

                    // get the title, section, and url for article
                    String title = firstArticle.getString("webTitle");
                    String section = firstArticle.getString("sectionName");
                    String url = firstArticle.getString("webUrl");
                    String img = firstArticle.getJSONObject("fields").getString("thumbnail");
                    String author;
                    if (firstArticle.getJSONArray("tags").length() == 0){
                        //if no author then use default value
                        author = "The Guardian";
                    }else {
                        //get author name if there is one
                        author = firstArticle.getJSONArray("tags").getJSONObject(0).getString("webTitle");
                    }
                    articles.add(new Article(title, section, url, img, author));
                }
                return articles;
            }
        } catch (JSONException e) {
            Log.e(TAG, String.valueOf(R.string.json_error), e);
        }
        return null;
    }
}