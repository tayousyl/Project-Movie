package com.example.sonel.moviesproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.example.sonel.moviesproject.R.id.ivMovieImage;
/**
 * Created by sonel on 7/19/2017.
 */

public class DetailsMovies extends AppCompatActivity {

    TextView tvTitle;
    TextView tvReleaseDate;
    RatingBar ratingBar;
    TextView tvSynopsis;
    ImageView ivImage;
    Movie movie;
    private Button bt;

    private ArrayList<Trailer> trailers;
   private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailsmovies);


        //retrieve movie that's been 'sent' from main activity
         movie = (Movie) getIntent().getSerializableExtra("movie");

        int movieID = movie.getID();

        url=Utils.getBaseURLtrailer()+ Integer.toString(movieID);
        trailers = new ArrayList<>();

        //retrieve all fields and set their value
       tvTitle = ButterKnife.findById(this, R.id.title);
        tvTitle.setText(movie.getOriginalTitle());

        tvReleaseDate = ButterKnife.findById(this, R.id.release_date);
        tvReleaseDate.setText("Release date: " + movie.getReleaseDate());

        tvSynopsis = ButterKnife.findById(this, R.id.synopsis);
        tvSynopsis.setText(movie.getOverview());

        ratingBar = ButterKnife.findById(this, R.id.rating_bar);
        ratingBar.setRating((float) movie.getRating());

        ivImage = ButterKnife.findById(this, ivMovieImage);

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;

                //launch video activity
                //intent = new Intent(DetailsMovies.this, YouTubeActivity.class);

                if (intent != null) {
                    // put movie as "extra" into the bundle for access in YouTubeActivity
                    intent.putExtra("movie", movie);
                    startActivity(intent);
                }

            }
        });

        Picasso.with(this).load(movie.getBackdropPath())
                .transform(new RoundedCornersTransformation(20, 20))
                .placeholder(R.drawable.placeholder)
                .into(ivImage);

        bt = (Button) findViewById(R.id.videoplayer);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  Intent intent= new Intent(DetailsMovies.this,YouTubeActivity.class);
                   intent.putExtra("movie", movie);
                //intent.putExtra("item",adapter.getItem(position));
                startActivity(intent);


            }
        });
        fetchMovieVideos(url);

            }

    private void fetchMovieVideos(String url) {
        //make sure there's access to the web
        boolean connectivity = Utils.checkForConnectivity(this);

        if (!connectivity) {
            Toast.makeText(this, "Unable to continue, no connection detected", Toast.LENGTH_LONG).show();
        } else {
            Utils.getClient().get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray trailersJsonResults = null;

                    try {
                        trailersJsonResults = response.getJSONArray("results");
                        trailers.addAll(Trailer.fromJSONArray(trailersJsonResults));
                        //setUpLayout();
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
