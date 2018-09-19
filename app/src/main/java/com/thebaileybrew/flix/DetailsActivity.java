package com.thebaileybrew.flix;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;
import com.thebaileybrew.flix.interfaces.CreditsAdapter;
import com.thebaileybrew.flix.loaders.CreditsLoader;
import com.thebaileybrew.flix.loaders.SingleMovieLoader;
import com.thebaileybrew.flix.model.Credit;
import com.thebaileybrew.flix.model.Movie;
import com.thebaileybrew.flix.utils.UrlUtils;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
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
    private CreditsAdapter creditsAdapter;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ArrayList<Credit> credits = new ArrayList<>();
    private RecyclerView creditsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mToolbar = findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        initViews();
        Intent getMovieIntent = getIntent();
        
        if (getMovieIntent != null) {
            if (getMovieIntent.hasExtra(MOVIE_KEY)) {
                Movie movie = getMovieIntent.getParcelableExtra(MOVIE_KEY);
                getSupportActionBar().setTitle(movie.getMovieTitle());
                getSupportActionBar().getThemedContext();
                mToolbar.setTitleTextColor(Color.WHITE);
                populateUI(movie);
            }
        }

    }

    private void populateUI(final Movie movie) {
        //Set up the Credit Recycler
        creditsRecycler = findViewById(R.id.credits_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        creditsRecycler.setLayoutManager(linearLayoutManager);
        creditsAdapter = new CreditsAdapter(this,credits,creditsRecycler);
        creditsRecycler.setAdapter(creditsAdapter);
        CreditsLoader creditsLoader = new CreditsLoader(creditsAdapter);
        creditsLoader.execute(String.valueOf(movie.getMovieID()));
        //Set up the details for single film details
        SingleMovieLoader singleMovieLoader = new SingleMovieLoader(movieTagline, movieGenres, movieRuntime);
        singleMovieLoader.execute(String.valueOf(movie.getMovieID()));
        Picasso.get().load(UrlUtils.buildPosterPathUrl(movie.getMoviePosterPath())).into(poster);
        Picasso.get().load(UrlUtils.buildBackdropUrl(movie.getMovieBackdrop(), movie.getMoviePosterPath())).into(posterImage);
        movieOverview.setText(movie.getMovieOverview());

        String fullRating = String.valueOf(movie.getMovieVoteAverage()) +
                " / " +
                String.valueOf(movie.getMovieVoteCount() + " Votes");
        movieRating.setText(fullRating);
        movieRelease.setText(formatDate(movie.getMovieReleaseDate()));
        getFilmCreditsForUI(movie.getMovieID());
    }

    private void getFilmCreditsForUI(int movieID) {


    }

    private String formatDate(String movieReleaseDate) {
        String[] datestamps = movieReleaseDate.split("-");
        String dateYear = datestamps[0];
        String dateMonth = datestamps[1];
        String dateDay = datestamps[2];
        return dateMonth + getString(R.string.linebreak) + dateDay + getString(R.string.linebreak) + dateYear;
    }

    private void initViews() {
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
