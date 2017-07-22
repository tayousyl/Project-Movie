package com.example.sonel.moviesproject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by sonel on 7/19/2017.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {


    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //use of subclassing to display every movie as part of the listView
        Movie movie = getItem(position);
        MoviView movieView = (MoviView)convertView;
        if(movieView == null){
            movieView = MoviView.inflate(parent);
        }
        movieView.setItem(movie);
        return movieView;
    }

}
