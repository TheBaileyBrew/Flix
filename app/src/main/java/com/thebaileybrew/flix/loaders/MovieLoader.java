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

public class MovieLoader extends AsyncTask<String, Void, ArrayList<Movie>> {
    private static final String TAG = MovieLoader.class.getSimpleName();

    private MovieAdapter mMovieAdapter;

    public MovieLoader(MovieAdapter movieAdapter) {
        mMovieAdapter = movieAdapter;

    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param strings The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected ArrayList<Movie> doInBackground(String... strings) {

        URL moviesRequestUrl = UrlUtils.buildMovieUrl(BuildConfig.API_KEY);
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
