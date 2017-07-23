package com.example.sonel.moviesproject;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.example.sonel.moviesproject.R.layout.item_movie;

/**
 * Created by sonel on 7/19/2017.
 */

public class MoviView extends RelativeLayout {

    private ImageView ivPicture;
    private TextView tvTitle;
    private TextView tvOverview;

    public MoviView(Context c) {
        this(c, null);
    }

    public MoviView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoviView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //LayoutInflater.from(context).inflate(R.layout.item_movie2, this, true);//DELETE THIS LINE

        LayoutInflater.from(context).inflate(item_movie, this, true);
        setupChildren();
    }


    private void setupChildren() {

        ivPicture  = ButterKnife.findById(this, R.id.txtimage);
        tvTitle    = ButterKnife.findById(this, R.id.txttitle);
        tvOverview = ButterKnife.findById(this, R.id.txtoverview);
    }


    public static MoviView inflate(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MoviView movieView = (MoviView) inflater.inflate(R.layout.movie_view, parent, false);
        return movieView;
    }


    private int getOrientation() {
        return getContext().getResources().getConfiguration().orientation;
    }



    private void drawImage(String path) {
        if (path != null && ivPicture != null) {
            Picasso.with(getContext()).load(path)
                    //.fit().centerCrop()
                    .transform(new RoundedCornersTransformation(20, 20))
                    .placeholder(R.drawable.placeholder)
                    .into(ivPicture);
        }
    }


    //this is a TEST method to be deleted once the varying layouts config. is set up
    public void depending(Movie movie){
        String imageToLoad = null;

        // Two possible views depending on popularity:
        // - image(poster/backdrop depending on orientation) + title + overview --> item_movie.xml
        // - full backdrop (for both orientations) --> item_popular_movie.xml
        if (movie.getRating() <= 3.0) {
            LayoutInflater.from(getContext()).inflate(R.layout.item_movie, this, true);
            setupChildren();

            //determine which image to load depending on screen orientation:
            int orientation = getOrientation();
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                imageToLoad = movie.getPosterPath();
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageToLoad = movie.getBackdropPath();
            }

            drawImage(imageToLoad);

            tvTitle.setText(movie.getOriginalTitle());
            tvOverview.setText(truncateOverview(movie.getOverview()));

        } else {
            LayoutInflater.from(getContext()).inflate(R.layout.moviespopular, this, true);
            setupChildren();
            imageToLoad = movie.getBackdropPath();
            drawImage(imageToLoad);
            //tvTitle.setText("DUMMY TITLE");
            //tvOverview.setText("DUMMY TEXT");
        }
    }


    //display data retrieved from the movie
    public void setItem(Movie movie) {

        //depending(movie);

        //determine which image to load depending on screen orientation:
        String imageToLoad = null;
        int orientation = getContext().getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            imageToLoad = movie.getPosterPath();
        }else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageToLoad = movie.getBackdropPath();
        }
        if(imageToLoad != null) {
            Picasso.with(getContext()).load(imageToLoad)
                    //.fit().centerCrop()
                    .transform(new RoundedCornersTransformation(20, 20))
                    .placeholder(R.drawable.placeholder)
                    .into(ivPicture);
        }

        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(truncateOverview(movie.getOverview()));

    }

    //reduce synopsis to tweet-size
    private String truncateOverview(String overview) {
        if(overview.length() > 140){

            overview = overview.substring(0, 137) + "...";
        }
        return overview;
    }

}
