package com.thebaileybrew.flix.utils;

import android.text.TextUtils;
import android.util.Log;

import com.thebaileybrew.flix.interfaces.MovieData;
import com.thebaileybrew.flix.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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
    private static final String MOVIE_BACKDROP = "backdrop_path";
    private static final String MOVIE_SYNOPSIS = "overview";
    private static final String MOVIE_RELEASE_DATE = "release_date";

    private jsonUtils(){}

    //Make the HTTPS Request
    public static String makeHttpsRequest(URL url) throws IOException {
        String jsonResponse = "";
        //Check for null URL
        if (url == null) {
            return jsonResponse;
        }
        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            Log.e(TAG, "makeHttpsRequest: full url is:" + String.valueOf(url));
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(12000);
            urlConnection.setConnectTimeout(20000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //If successful request
            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse= readFromStream(inputStream);
            } else {
                Log.e(TAG, "makeHttpsRequest: Error Code: " +urlConnection.getResponseCode());
            }
        } catch (IOException ioe) {
            Log.e(TAG, "makeHttpsRequest: Cound not retrieve JSON result", ioe);
        } finally {
            if (urlConnection !=null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //Return the list<> of Movie from the JSON data
    public static ArrayList<Movie> extractMoviesFromJson(String jsonData) {
        int movieID;
        int movieVoteCount;
        long movieVoteAverage;
        String movieTitle;
        long moviePopularity;
        String movieLanguage;
        String moviePosterPath;
        String movieBackdrop;
        String movieOverview;
        String movieReleaseDate;

        //Check for NULL jsonData
        if (TextUtils.isEmpty(jsonData)) {
            return null;
        }
        ArrayList<Movie> movieCollection = new ArrayList<>();
        try {
            JSONObject baseJSONResponse = new JSONObject(jsonData);

            JSONArray baseJSONArray = baseJSONResponse.getJSONArray("results");
            //Loop through film results
            for (int m = 0; m < baseJSONArray.length(); m++) {
                JSONObject currentFilm = baseJSONArray.getJSONObject(m);
                //Extract the movie ID
                movieID = currentFilm.getInt(MOVIE_ID);
                //Extract the movie vote count
                movieVoteCount = currentFilm.getInt(MOVIE_VOTE_COUNT);
                //Extract the movie vote average
                movieVoteAverage = currentFilm.getLong(MOVIE_AVERAGE);
                //Extract the movie title
                movieTitle = currentFilm.getString(MOVIE_NAME);
                //Extract the movie popularity
                moviePopularity = currentFilm.getLong(MOVIE_POPULARITY);
                //Extract the movie language
                movieLanguage = currentFilm.getString(MOVIE_ORIG_LANGUAGE);
                //Extract the movie poster path and pass through UrlUtils to build full path
                moviePosterPath = currentFilm.getString(MOVIE_POSTER_PATH);
                //Extract the movie backdrop and pass through UrlUtils to build full path
                movieBackdrop = currentFilm.getString(MOVIE_BACKDROP);
                //Extract the movie overview
                movieOverview = currentFilm.getString(MOVIE_SYNOPSIS);
                //Extract the movie release date
                movieReleaseDate = currentFilm.getString(MOVIE_RELEASE_DATE);
                movieCollection.add(new Movie(movieID, movieVoteCount, movieVoteAverage, movieTitle,
                        moviePopularity, movieLanguage, moviePosterPath, movieBackdrop,
                        movieOverview, movieReleaseDate));
            }

        } catch (JSONException je) {
            Log.e(TAG, "extractMoviesFromJson: problems extracting film details from json", je);
        }
        return movieCollection;
    }


}
