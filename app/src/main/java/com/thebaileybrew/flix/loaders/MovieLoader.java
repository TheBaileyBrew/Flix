package com.thebaileybrew.flix.loaders;

import android.content.Context;

import com.thebaileybrew.flix.model.Movie;
import com.thebaileybrew.flix.utils.jsonUtils;

import java.util.List;

import androidx.loader.content.AsyncTaskLoader;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    private static final String TAG = MovieLoader.class.getSimpleName();

    private String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Movie> MovieList = jsonUtils.fetchMovieData(mUrl);
        return MovieList;
    }
}
