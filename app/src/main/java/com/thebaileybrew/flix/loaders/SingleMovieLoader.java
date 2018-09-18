package com.thebaileybrew.flix.loaders;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.thebaileybrew.flix.BuildConfig;
import com.thebaileybrew.flix.model.Film;
import com.thebaileybrew.flix.utils.UrlUtils;
import com.thebaileybrew.flix.utils.jsonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class SingleMovieLoader extends AsyncTask<String, Void, ArrayList<Film>> {
    private static final String TAG = SingleMovieLoader.class.getSimpleName();
    private final static String TIME_FORMAT = "%02d:%02d";

    private final TextView movieTag;
    private final TextView movieGenre;
    private final TextView movieTime;

    public SingleMovieLoader (TextView movieTag, TextView movieGenre, TextView movieTime) {
        this.movieTag = movieTag;
        this.movieGenre = movieGenre;
        this.movieTime = movieTime;
    }


    @Override
    protected ArrayList<Film> doInBackground(String... params){
        if (params.length < 1 || params[0] == null) {
            return null;
        }
        String movieID = params[0];

        URL singleFilmRequest = UrlUtils.buildSingleMovieUrl(BuildConfig.API_KEY,movieID);
        try {
            String jsonFilmResponse = jsonUtils.requestHttpsSingleFilm(singleFilmRequest);

            return jsonUtils.extractSingleFilmData(jsonFilmResponse);
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: can't make http single req", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Film> films) {
        if (films != null) {
            Film currentFilm = films.get(0);
            movieTag.setText(currentFilm.getMovieTagLine());
            movieGenre.setText(currentFilm.getMovieGenre());
            movieTime.setText(convertTime(currentFilm.getMovieRuntime()));

        }

        super.onPostExecute(films);
    }

    private String convertTime(int runTime) {
        int hours = runTime / 60;
        int minutes = runTime % 60;
        return String.format(Locale.US, TIME_FORMAT, hours, minutes);
    }
}
