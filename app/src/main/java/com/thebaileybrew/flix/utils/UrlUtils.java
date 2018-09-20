package com.thebaileybrew.flix.utils;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtils {
    private static final String TAG = UrlUtils.class.getSimpleName();

    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/";
    private static final String BASE_IMAGE_LARGE = "w500";

    private static final String BASE_MOVIE_URL = "https://api.themoviedb.org/3";
    private static final String BASE_MOVIE_PATH = "movie";
    private static final String BASE_CREDIT_PATH = "credits";

    private static final String API_KEY = "api_key";

    //Build the URL for querying all movies in the database
    public static URL buildMovieUrl(String apiKey, String sortingOrder){

        Uri movieQueryUri = Uri.parse(BASE_MOVIE_URL).buildUpon()
                .appendPath(BASE_MOVIE_PATH)
                .appendPath(sortingOrder)
                .appendQueryParameter(API_KEY, apiKey)
                .build();
        URL movieQueryURL;
        try {
            movieQueryURL = new URL(movieQueryUri.toString());
            return movieQueryURL;
        } catch (MalformedURLException me) {
            Log.e(TAG, "buildMovieUrl: failed to build full db URL", me);
            return null;
        }
    }
    //Build the URL for querying a single movie in the database
    public static URL buildSingleMovieUrl(String apiKey, String movieID) {

        Uri singleMovieQuery = Uri.parse(BASE_MOVIE_URL).buildUpon()
                .appendPath(BASE_MOVIE_PATH)
                .appendPath(movieID)
                .appendQueryParameter(API_KEY, apiKey)
                .build();
        URL singleMovieURL;
        try {
            singleMovieURL = new URL(singleMovieQuery.toString());
            return singleMovieURL;
        } catch (MalformedURLException me) {
            Log.e(TAG, "buildSingleMovieUrl: failed to build single URL", me);
            return null;
        }
    }

    //Build the poster path url
    public static String buildPosterPathUrl(String posterPath) {
        return BASE_IMAGE_URL + BASE_IMAGE_LARGE + posterPath;
    }
    //Build the backdrop path url
    public static String buildBackdropUrl(String backdropPath, String posterPath) {
        if (backdropPath.equals("null")) {
            return BASE_IMAGE_URL + BASE_IMAGE_LARGE + posterPath;
        } else {
            return BASE_IMAGE_URL + BASE_IMAGE_LARGE + backdropPath;
        }
    }

    //Build the credit details url
    public static URL buildCreditsMovieUrl(String apiKey, String movieID) {
        Uri movieCreditsQuery = Uri.parse(BASE_MOVIE_URL).buildUpon()
                .appendPath(BASE_MOVIE_PATH)
                .appendPath(movieID)
                .appendPath(BASE_CREDIT_PATH)
                .appendQueryParameter(API_KEY, apiKey)
                .build();
        URL creditsQueryURL;
        try {
            creditsQueryURL = new URL(movieCreditsQuery.toString());
            return creditsQueryURL;
        } catch (MalformedURLException me) {
            Log.e(TAG, "buildCreditsMovieUrl: failed to build credits url", me);
            return null;
        }
    }

    //Build the credit image url
    public static String buildCreditImageUrl (String creditPath) {
        return BASE_IMAGE_URL + BASE_IMAGE_LARGE + creditPath;
    }
}
