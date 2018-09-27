package com.thebaileybrew.flix.loaders;

import android.os.AsyncTask;
import android.util.Log;

import com.thebaileybrew.flix.BuildConfig;
import com.thebaileybrew.flix.R;
import com.thebaileybrew.flix.interfaces.MovieAdapter;
import com.thebaileybrew.flix.model.Movie;
import com.thebaileybrew.flix.utils.UrlUtils;
import com.thebaileybrew.flix.utils.jsonUtils;

import java.net.URL;
import java.util.ArrayList;

public class MovieLoader extends AsyncTask<String, Void, ArrayList<Movie>> {
    private static final String TAG = MovieLoader.class.getSimpleName();

    private final MovieAdapter mMovieAdapter;

    public MovieLoader(MovieAdapter movieAdapter) {
        mMovieAdapter = movieAdapter;

    }


    @Override
    protected ArrayList<Movie> doInBackground(String... strings) {
        if (strings.length < 3 || strings[0] == null) {
            return null;
        }
        String sortingOrder = strings[0];
        String languageFilter = strings[1];
        String filterYear = strings[2];
        String searchQuery = strings[3];


        URL moviesRequestUrl = UrlUtils.buildMovieUrl(
                    BuildConfig.API_KEY,
                    languageFilter,
                    sortingOrder,
                    filterYear,
                    searchQuery);
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
            mMovieAdapter.setMovieCollection(movies);
        }
        super.onPostExecute(movies);

    }
}
