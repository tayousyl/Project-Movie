package com.example.sonel.moviesproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Movie> movies;
    private MovieArrayAdapter movieAdapter;
    private ListView lvMovies;
    private SwipeRefreshLayout swipeContainer;



   // private final String URL = Utils.getBaseURL()+"now_playing?api_key="+Utils.getMovieDBAPIkey();
   private final String URL = Utils.getBaseURLmovie();//+"now_playing?api_key="+Utils.getMovieDBAPIkey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set up adapter
        lvMovies = ButterKnife.findById(this, R.id.lstmovies);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvMovies.setAdapter(movieAdapter);

        // setup refresh listener which triggers new data loading
        swipeContainer = ButterKnife.findById(this, R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //CLEAR OUT old items before appending in the new ones:
                movieAdapter.clear();
                movies.clear();
                movieAdapter.notifyDataSetChanged();

               // //fetchData(Utils.getClient());
                //fetchHardcodedData();

                //signal refresh has finished:
                swipeContainer.setRefreshing(false);
            }
        });


        //fetchHardcodedData();
        fetchData(Utils.getClient());

        //Call method to show View of details
       setUpClickListener();
    }

    //Set up Method details view
   private void setUpClickListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //view is an instance of MovieView
                //Expose details of movie (ratings (out of 10), popularity, and synopsis
                //ratings using RatingBar
                Movie movie = movies.get(position);

                Intent intent = null;

                if (movie.getRating() > 5.0) {
                    //launch video activity
                   // intent = new Intent(MainActivity.this, YouTubeActivity.class);
                    intent = new Intent(MainActivity.this, DetailsMovies.class);
                } else {
                    intent = new Intent(MainActivity.this, DetailsMovies.class);
                }

                if(intent != null){
                    // put movie as "extra" into the bundle for access in the second activity
                    intent.putExtra("movie", movie);
                    startActivity(intent);
                }
            }
        });
    }





    private void fetchData(AsyncHttpClient client) {

        //make sure there's access to the web
        boolean connectivity = Utils.checkForConnectivity(this);

        if (!connectivity) {
            Toast.makeText(this, "Unable to continue, no connection detected", Toast.LENGTH_LONG).show();
        } else {
            client.get(URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray movieJsonResults = null;

                    try {
                        movieJsonResults = response.getJSONArray("results");
                        movies.addAll(Movie.fromJSONArray(movieJsonResults));
                        movieAdapter.notifyDataSetChanged();

                        //signal refresh has finished:
                        swipeContainer.setRefreshing(false);

                        Log.d("DEBUG", movies.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }

    }

}


