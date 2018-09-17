package com.thebaileybrew.flix.loaders;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.thebaileybrew.flix.BuildConfig;
import com.thebaileybrew.flix.interfaces.MovieAdapter;
import com.thebaileybrew.flix.interfaces.MovieData;
import com.thebaileybrew.flix.model.Movie;
import com.thebaileybrew.flix.utils.UrlUtils;
import com.thebaileybrew.flix.utils.jsonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class MovieLoader extends AsyncTask<String, Void, ArrayList<Movie>> {
    private static final String TAG = MovieLoader.class.getSimpleName();

    private MovieAdapter mMovieAdapter;
    private String languageFilter;

    public MovieLoader(MovieAdapter movieAdapter) {
        mMovieAdapter = movieAdapter;

    }


    @Override
    protected ArrayList<Movie> doInBackground(String... strings) {
        if (strings.length < 2 || strings[0] == null) {
            return null;
        }
        String sortingOrder = strings[0];
        languageFilter = strings[1];

        URL moviesRequestUrl = UrlUtils.buildMovieUrl(BuildConfig.API_KEY, sortingOrder);
        try {
            String jsonMoviesResponse = jsonUtils.makeHttpsRequest(moviesRequestUrl);

            return jsonUtils.extractMoviesFromJson(jsonMoviesResponse);
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: can't make http req", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        if(movies != null) {
            if (languageFilter.equals("all")) {
                mMovieAdapter.setMovieCollection(movies);
            } else {
                ArrayList<Movie> languageFilteredList = new ArrayList<>();
                for (Movie movie : movies) {
                    if (movie.getMovieLanguage().equals(languageFilter)) {
                        languageFilteredList.add(movie);
                    }
                }
                mMovieAdapter.setMovieCollection(languageFilteredList);
            }
        }
        super.onPostExecute(movies);

    }
}
