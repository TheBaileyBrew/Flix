package com.thebaileybrew.flix;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.thebaileybrew.flix.model.Movie;
import com.thebaileybrew.flix.utils.UrlUtils;

import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {
    private final static String TAG = DetailsActivity.class.getSimpleName();

    private final static String MOVIE_KEY = "parcel_movie";
    
    androidx.appcompat.widget.Toolbar mToolbar;
    ImageView posterImage;
    TextView movieRuntime;
    TextView movieRelease;
    TextView movieRating;
    TextView movieOverview;
    TextView movieTitle;
    TextView movieTagline;
    ImageView backgroundFade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initViews();
        Intent getMovieIntent = getIntent();
        
        if (getMovieIntent != null) {
            if (getMovieIntent.hasExtra(MOVIE_KEY)) {
                Movie movie = getMovieIntent.getParcelableExtra(MOVIE_KEY);
                populateUI(movie);
            }
        }

        //TODO: Set up to display details for specific selected film

        /*TODO: Set up background process to query API again for
        *    RUNTIME, GENRE LIST, TAGLINE
        */
    }

    private void populateUI(Movie movie) {
        //Display the movieposter
        Picasso.get().load(UrlUtils.buildPosterPathUrl(movie.getMoviePosterPath())).into(posterImage);
        Picasso.get().load(UrlUtils.buildBackdropUrl(movie.getMovieBackdrop())).into(backgroundFade);
        Log.e(TAG, "populateUI: full backdropURL is: " + String.valueOf(UrlUtils.buildBackdropUrl(movie.getMovieBackdrop())));
        movieTitle.setText(movie.getMovieTitle());
        movieTagline.setText("This is a tagline demo...");
        movieOverview.setText(movie.getMovieOverview());
        movieRating.setText(String.valueOf(movie.getMovieVoteAverage()));
        movieRelease.setText(movie.getMovieReleaseDate());
    }

    private void initViews() {
        posterImage = findViewById(R.id.poster_imageview);
        movieRuntime = findViewById(R.id.movie_runtime);
        movieRelease = findViewById(R.id.movie_release);
        movieRating = findViewById(R.id.movie_rating);
        movieOverview = findViewById(R.id.synopsis_text);
        movieTitle = findViewById(R.id.movie_title);
        movieTagline = findViewById(R.id.movie_tagline);
        backgroundFade = findViewById(R.id.main_background_fade);
    }
}
