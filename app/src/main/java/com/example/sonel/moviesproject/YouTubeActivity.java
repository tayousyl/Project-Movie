package com.example.sonel.moviesproject;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by sonel on 7/21/2017.
 */

public class YouTubeActivity extends YouTubeBaseActivity {


    private ArrayList<Trailer> trailers;
    private Movie movie;
    private String url;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_youtube);

        //retrieve movie that's been 'sent' from main activity
        movie = (Movie) getIntent().getSerializableExtra("movie");

        //retrieve ID of movie
        int movieID = movie.getID();
 url=Utils.getBaseURLtrailer()+ Integer.toString(movieID);
        trailers = new ArrayList<>();

       // String url = Utils.getBaseURL() + Integer.toString(movieID) + "/videos?api_key=" + Utils.getMovieDBAPIkey();

        String url=Utils.getBaseURLtrailer()+ Integer.toString(movieID);

        //fetchMovieVideos(url);
        setUpLayout();

    }


   /* private void fetchMovieVideos(String url) {
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
                        setUpLayout();
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
    }*/

    private void setUpLayout() {

        TextView tvTitle = ButterKnife.findById(this, R.id.tv_title);
        tvTitle.setText(movie.getOriginalTitle());

        setUpVideoPlayer();
    }

    private void setUpVideoPlayer() {
        //trailers contains now all the trailers.
        //Let's randomly select the first one that is type 'Trailer'
        String selected = null;

        Trailer trailer;
        for (int i = 0; i < trailers.size() && selected == null; i++) {
            trailer = trailers.get(i);
            if (trailer.getTrailerType().equals("Trailer")) {
                selected = trailer.getTrailerID();
            }
        }

        final String trailerID = selected;
        if (trailerID != null) {

            YouTubePlayerView youTubePlayerView =
                    (YouTubePlayerView) findViewById(R.id.youtube_player);

           // youTubePlayerView.initialize(Utils.getYouTubeAPIkey(),
                    youTubePlayerView.initialize(url,
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            YouTubePlayer youTubePlayer, boolean b) {
                            // do any work here to cue video, play video, etc.
                            youTubePlayer.setFullscreen(true);
                            youTubePlayer.loadVideo(trailerID);
                            // or to not play immediately call cueVideo instead
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {

                        }
                    });
        }

    }

}
