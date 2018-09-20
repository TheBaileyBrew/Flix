package com.thebaileybrew.flix.loaders;

import android.os.AsyncTask;
import android.util.Log;

import com.thebaileybrew.flix.BuildConfig;
import com.thebaileybrew.flix.interfaces.CreditsAdapter;
import com.thebaileybrew.flix.model.Credit;
import com.thebaileybrew.flix.utils.UrlUtils;
import com.thebaileybrew.flix.utils.jsonUtils;

import java.net.URL;
import java.util.ArrayList;

public class CreditsLoader extends AsyncTask<String, Void, ArrayList<Credit>> {
    private static final String TAG = CreditsLoader.class.getSimpleName();

    private final CreditsAdapter mCreditsAdapter;

    public CreditsLoader(CreditsAdapter creditsAdapter) {
        mCreditsAdapter = creditsAdapter;
    }


    @Override
    protected ArrayList<Credit> doInBackground(String... strings) {
        if (strings.length < 1 || strings[0] == null) {
            Log.e(TAG, "doInBackground: strings length= "+ strings.length );
            return null;
        }
        String movieID = strings[0];
        Log.e(TAG, "doInBackground: movieID" + movieID );

        URL creditRequestUrl = UrlUtils.buildCreditsMovieUrl(BuildConfig.API_KEY, movieID);
        try {
            String jsonCreditResponse = jsonUtils.requestHttpsMovieCredits(creditRequestUrl);
            return jsonUtils.extractCreditDetails(jsonCreditResponse);
        } catch (Exception e) {
            Log.e(TAG, "doInBackground: can't request credits", e);
            return null;
        }
    }

    @Override
    protected  void onPostExecute(ArrayList<Credit> credits) {
        if (credits != null) {
            mCreditsAdapter.setCreditCollection(credits);
        }
        super.onPostExecute(credits);
    }


}
