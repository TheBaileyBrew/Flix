package com.thebaileybrew.flix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;
import com.thebaileybrew.flix.interfaces.CollapsingToolbarListener;
import com.thebaileybrew.flix.interfaces.CreditsAdapter;
import com.thebaileybrew.flix.loaders.CreditsLoader;
import com.thebaileybrew.flix.loaders.SingleMovieLoader;
import com.thebaileybrew.flix.model.Credit;
import com.thebaileybrew.flix.model.Movie;
import com.thebaileybrew.flix.utils.UrlUtils;
import com.thebaileybrew.flix.utils.networkUtils;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailsActivity extends AppCompatActivity {
    private final static String TAG = DetailsActivity.class.getSimpleName();

    private final static String MOVIE_KEY = "parcel_movie";
    
    private androidx.appcompat.widget.Toolbar mToolbar;
    private ImageView posterImage;
    private ImageView poster;
    private TextView movieRuntime;
    private TextView movieRelease;
    private TextView movieRating;
    private TextView movieOverview;

    private TextView movieTagline;
    private TextView movieGenres;
    private View scrimView;
    private CreditsAdapter creditsAdapter;
    private AppBarLayout appBarLayout;
    private final ArrayList<Credit> credits = new ArrayList<>();
    private RecyclerView creditsRecycler;
    private TextView noCreditsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initViews();
        mToolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.app_toolbar);
        appBarLayout.addOnOffsetChangedListener(new CollapsingToolbarListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d(TAG, "onStateChanged: Current State: " + state.name());
                if (state == State.COLLAPSED) {
                    scrimView.setBackgroundResource(R.drawable.shape_scrim_collapsed);
                } else {
                    scrimView.setBackgroundResource(R.drawable.shape_scrim);
                }
            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent getMovieIntent = getIntent();
        
        if (getMovieIntent != null) {
            if (getMovieIntent.hasExtra(MOVIE_KEY)) {
                Movie movie = getMovieIntent.getParcelableExtra(MOVIE_KEY);
                getSupportActionBar().setTitle(movie.getMovieTitle());
                getSupportActionBar().getThemedContext();
                populateUI(movie);
            }
        }

    }

    private void populateUI(final Movie movie) {
        //Set up the Credit Recycler
        creditsRecycler = findViewById(R.id.credits_recycler);
        if (networkUtils.checkNetwork(DetailsActivity.this)) {
            //Load all data from credits json & details json
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
            creditsRecycler.setLayoutManager(linearLayoutManager);
            creditsAdapter = new CreditsAdapter(this,credits,creditsRecycler);
            creditsRecycler.setAdapter(creditsAdapter);
            CreditsLoader creditsLoader = new CreditsLoader(creditsAdapter);
            creditsLoader.execute(String.valueOf(movie.getMovieID()));
            //Set up the details for single film details
            SingleMovieLoader singleMovieLoader = new SingleMovieLoader(movieTagline, movieGenres, movieRuntime);
            singleMovieLoader.execute(String.valueOf(movie.getMovieID()));
        } else {
            //Load only data from Intent and add network message
            noCreditsText.setText(R.string.check_network_credits_display);
            creditsRecycler.setVisibility(View.INVISIBLE);
            movieRuntime.setText(R.string.unknown_time);
        }

        Picasso.get().load(UrlUtils.buildPosterPathUrl(movie.getMoviePosterPath())).into(poster);
        Picasso.get().load(UrlUtils.buildBackdropUrl(movie.getMovieBackdrop(), movie.getMoviePosterPath())).into(posterImage);
        movieOverview.setText(movie.getMovieOverview());

        String fullRating = String.valueOf(movie.getMovieVoteAverage()) +
                " / " +
                String.valueOf(movie.getMovieVoteCount() + " Votes");
        movieRating.setText(fullRating);
        movieRelease.setText(formatDate(movie.getMovieReleaseDate()));
    }


    private String formatDate(String movieReleaseDate) {
        String[] datestamps = movieReleaseDate.split("-");
        String dateYear = datestamps[0];
        String dateMonth = datestamps[1];
        String dateDay = datestamps[2];
        return dateMonth + getString(R.string.linebreak) + dateDay + getString(R.string.linebreak) + dateYear;
    }

    private void initViews() {
        scrimView = findViewById(R.id.scrim_view);
        noCreditsText = findViewById(R.id.no_credits_view);
        creditsRecycler = findViewById(R.id.credits_recycler);
        poster = findViewById(R.id.poster);
        posterImage = findViewById(R.id.poster_imageview);
        movieRuntime = findViewById(R.id.movie_runtime);
        movieRelease = findViewById(R.id.movie_release);
        movieRating = findViewById(R.id.movie_rating);
        movieOverview = findViewById(R.id.synopsis_text);
        movieTagline = findViewById(R.id.movie_tagline);
        movieGenres = findViewById(R.id.movie_genres);

    }

}
