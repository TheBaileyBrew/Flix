package com.thebaileybrew.flix;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thebaileybrew.flix.loaders.SingleMovieLoader;
import com.thebaileybrew.flix.model.Movie;
import com.thebaileybrew.flix.utils.UrlUtils;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        //TODO: Set up to display details for specific selected film

        /*TODO: Set up background process to query API again for
        *    RUNTIME, GENRE LIST, TAGLINE
        */
    }

    private void populateUI(Movie movie) {
        SingleMovieLoader singleMovieLoader = new SingleMovieLoader(movieTagline, movieGenres, movieRuntime);
        singleMovieLoader.execute(String.valueOf(movie.getMovieID()));
        Picasso.get().load(UrlUtils.buildPosterPathUrl(movie.getMoviePosterPath())).into(poster);
        Picasso.get().load(UrlUtils.buildBackdropUrl(movie.getMovieBackdrop())).into(posterImage);
        Log.e(TAG, "populateUI: full backdropURL is: " + String.valueOf(UrlUtils.buildBackdropUrl(movie.getMovieBackdrop())));
        movieOverview.setText(movie.getMovieOverview());

        String fullRating = String.valueOf(movie.getMovieVoteAverage()) +
                " / " +
                String.valueOf(movie.getMovieVoteCount() + " Votes");
        movieRating.setText(fullRating);
        movieRelease.setText(movie.getMovieReleaseDate());
    }



    private void initViews() {
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
