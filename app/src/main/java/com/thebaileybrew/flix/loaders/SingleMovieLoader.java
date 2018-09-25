package com.thebaileybrew.flix.loaders;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.thebaileybrew.flix.BuildConfig;
import com.thebaileybrew.flix.R;
import com.thebaileybrew.flix.model.Film;
import com.thebaileybrew.flix.utils.UrlUtils;
import com.thebaileybrew.flix.utils.jsonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class SingleMovieLoader extends AsyncTask<String, Void, ArrayList<Film>> {
    private static final String TAG = SingleMovieLoader.class.getSimpleName();




    public SingleMovieLoader () {

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

        super.onPostExecute(films);
    }
}
