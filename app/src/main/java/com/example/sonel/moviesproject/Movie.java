package com.example.sonel.moviesproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sonel on 7/19/2017.
 */

public class Movie implements Serializable {

    int    id;
    String posterPath;
    String backdropPath;
    String originalTitle;
    String overview;
    String releaseDate;
    double rating;


    //constructor to retrieve all the attributes we need
    public Movie(JSONObject jsonObject) throws JSONException {
        id            = jsonObject.getInt("id");
        posterPath    = jsonObject.getString("poster_path");
        backdropPath  = jsonObject.getString("backdrop_path");
        originalTitle = jsonObject.getString("original_title");
        overview      = jsonObject.getString("overview");
        rating        = jsonObject.getDouble("vote_average");
        releaseDate   = jsonObject.getString("release_date");
    }


    //getters

    public int getID() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780%s", backdropPath);//300?
    }

    public double getRating(){
        //the API returns a x out of 10 rating, so we convert it to the equivalent out of 5
        return rating*0.5;
    }

    public String getReleaseDate(){
        return releaseDate;
    }


    //convert Json object into ArrayList
    public static ArrayList<Movie> fromJSONArray(JSONArray array){
        ArrayList<Movie> results = new ArrayList<>();

        for(int i=0; i<array.length(); i++){
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
