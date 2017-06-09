package com.example.android.newsyapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // test guardian API url
    private static final String NEWS_REQUEST_URL =
            "http://content.guardianapis.com/search?q=android&api-key=test&show-fields=thumbnail&order-by=newest&show-tags=contributor";
    private static final String TAG = "MAIN_ACTIVITY";
    private Handler handler;
    TextView emptyView;
    ListView listView;
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        emptyView = (TextView) findViewById(R.id.empty_view);
        listView = (ListView) findViewById(R.id.list);

        // hide empty view while list loads
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }

        showIndicator();
        new NewsAsyncTask().execute(NEWS_REQUEST_URL);

        //added swipe to fresh list feature as well as an action button
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        if (swipeRefresh != null){
            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
                @Override
                public void onRefresh(){
                    showIndicator();
                    new NewsAsyncTask().execute(NEWS_REQUEST_URL);
                }
            });
        }

        //run asyntask handler every 15 seconds to update news
        handler = new Handler();
        handler.postDelayed(runnable, 15000);
    }

    // runnable method that calls news feed every 15 seconds
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            showIndicator();
            new NewsAsyncTask().execute(NEWS_REQUEST_URL);

            handler.postDelayed(this, 15000);
        }
    };

    // update ui method called when task is done
    private void updateUi(ArrayList<Article> articles) {

        final ArticleListAdapter articleAdapter = new ArticleListAdapter(this, articles);

        if (listView != null) {
            listView.setVisibility(View.VISIBLE);
            listView.setAdapter(articleAdapter);
            listView.setEmptyView(emptyView);

            //set onclick listener for each list item
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // current article that was clicked on
                    Article current = articleAdapter.getItem(position);

                    // convert the String URL into a URI
                    Uri articleUri = Uri.parse(current.getArticleUrl());

                    // new intent to view the guardian web URI
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                    // launch a new activity calls website url
                    startActivity(webIntent);
                }
            });
        }
    }

    private void showIndicator(){
        //show load indicator icon while getting data
        View loadingIndicator = findViewById(R.id.loading_indicator);
        if (loadingIndicator != null) {
            loadingIndicator.setVisibility(View.VISIBLE);
        }
    }

    private void hideIndicator(){
        //hide load indicator icon when done getting data
        View loadingIndicator = findViewById(R.id.loading_indicator);
        if (loadingIndicator != null) {
            loadingIndicator.setVisibility(View.GONE);
            swipeRefresh.setRefreshing(false);
        }
    }

    // async task method to get news info
    private class NewsAsyncTask extends AsyncTask<String, Void, ArrayList<Article>> {

        @Override
        protected ArrayList<Article> doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            return Utils.getNewsData(urls[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Article> articles) {

            hideIndicator();

            if (articles == null) {
                //show and set empty view text if no data is returned

                emptyView.setVisibility(View.VISIBLE);
                emptyView.setText(R.string.empty_text);
                listView.setVisibility(View.GONE);
                return;
            }

            //update ui list with array of book info
            updateUi(articles);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get item id
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            showIndicator();

            //refresh news artile list by calling async task using button
            new NewsAsyncTask().execute(NEWS_REQUEST_URL);
            Log.e(TAG, String.valueOf(R.string.respone_error));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}