package com.thebaileybrew.flix.utils;

import android.util.Log;

import com.thebaileybrew.flix.model.Movie;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class jsonUtils {
    private static  final String TAG = jsonUtils.class.getSimpleName();

    //static resource references for querying API results
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_NAME = "title";
    private static final String MOVIE_VOTE_COUNT = "vote_count";
    private static final String MOVIE_AVERAGE = "vote_average";
    private static final String MOVIE_POPULARITY = "popularity";
    private static final String MOVIE_ORIG_LANGUAGE = "original_language";
    private static final String MOVIE_POSTER_PATH = "poster_path";
    private static final String MOVIE_SYNOPSIS = "overview";
    private static final String MOVIE_RELEASE_DATE = "release_date";


    private jsonUtils(){}

    public static List<Movie> fetchMovieData (String requestUrl) {

        //Create URL object
        URL movieUrl = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpsRequest(movieUrl);
        } catch (IOException ioe) {
            Log.e(TAG, "fetchMovieData: Problem with HTTP req", ioe);
        }

        List<Movie> movieList = extractMoviesFromJson(jsonResponse);
        return movieList;
    }


}
