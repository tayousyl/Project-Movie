package com.example.sonel.moviesproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by sonel on 7/19/2017.
 */

public class Utils {


    private final static String movieDBAPIkey = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private final static String youTubeAPIkey = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private final static String baseURLmovie = " https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private final static String baseURLtrailer = "https://api.themoviedb.org/3/movie/209112/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private final static String baseURL = "https://api.themoviedb.org/3/movie/";
    private final static AsyncHttpClient client = new AsyncHttpClient();


    public static String getMovieDBAPIkey() {
        return movieDBAPIkey;
    }

    public static String getBaseURLmovie() {
        return baseURLmovie;
    }

    public static String getBaseURLtrailer() {
        return baseURLtrailer;
    }


    public static String getYouTubeAPIkey() {
        return youTubeAPIkey;
    }

    public static AsyncHttpClient getClient() {
        return client;
    }

    public static String getBaseURL() {
        return baseURL;
    }


    public static boolean checkForConnectivity(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}