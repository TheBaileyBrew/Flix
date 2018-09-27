package com.thebaileybrew.flix;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;
import com.thebaileybrew.flix.interfaces.CollapsingToolbarListener;
import com.thebaileybrew.flix.interfaces.CreditsAdapter;
import com.thebaileybrew.flix.interfaces.StaticProgressBar;
import com.thebaileybrew.flix.loaders.CreditsLoader;
import com.thebaileybrew.flix.loaders.SingleMovieLoader;
import com.thebaileybrew.flix.model.Credit;
import com.thebaileybrew.flix.model.Film;
import com.thebaileybrew.flix.model.Movie;
import com.thebaileybrew.flix.utils.UrlUtils;
import com.thebaileybrew.flix.utils.displayMetricsUtils;
import com.thebaileybrew.flix.utils.networkUtils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.VISIBLE;

public class DetailsActivity extends AppCompatActivity {
    private final static String TAG = DetailsActivity.class.getSimpleName();

    private final static String MOVIE_KEY = "parcel_movie";
    private final static String TIME_FORMAT = "%02d:%02d";

    private androidx.appcompat.widget.Toolbar mToolbar;
    private ImageView posterImage;
    private ImageView poster;
    private TextView movieRuntime;
    private TextView movieRelease;
    private TextView movieRating;
    private StaticProgressBar movieRatingBar;
    private TextView movieOverview;
    private Animation animScaleDown, animFadeOut;
    private Animation animScaleUp, animFadeIn;
    private Boolean posterHidden = false;
    private double currentFilmRating;
    private TextView ratingTitleHeader;
    private TextView movieTagline;
    private TextView movieGenres;
    private View scrimView;
    private final ArrayList<Credit> credits = new ArrayList<>();
    private RecyclerView creditsRecycler;
    private TextView noCreditsText;
    private ArrayList<Film> arrayFilm = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        animScaleDown = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.anim_fade_out_ratingbar);
        animScaleUp = AnimationUtils.loadAnimation(this, R.anim.anim_scale_up);
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_fade_in_ratingbar);
        initViews();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        Intent getMovieIntent = getIntent();

        if (getMovieIntent != null) {
            if (getMovieIntent.hasExtra(MOVIE_KEY)) {
                Movie movie = getMovieIntent.getParcelableExtra(MOVIE_KEY);
                getSupportActionBar().setTitle(movie.getMovieTitle());
                getSupportActionBar().getThemedContext();
                populateUI(movie);
                currentFilmRating = movie.getMovieVoteAverage();
            }
        }
        AppBarLayout appBarLayout = findViewById(R.id.app_toolbar);
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener(new CollapsingToolbarListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d(TAG, "onStateChanged: Current State: " + state.name());
                if (state == State.COLLAPSED) {
                    hidePosterImage();
                    showRatingBar();
                    updateRatingBar(currentFilmRating);
                    posterHidden = true;
                    scrimView.setBackgroundResource(R.drawable.shape_scrim_collapsed);

                } else if (state == State.EXPANDED) {
                    showPosterImage();
                    hideRatingBar();
                    scrimView.setBackgroundResource(R.drawable.shape_scrim);

                    posterHidden = false;

                } else {
                    scrimView.setBackgroundResource(R.drawable.shape_scrim);
                }
            }
        });
    }

    private void updateRatingBar(double currentProgress) {
        movieRatingBar.setMax(10);
        movieRatingBar.setSuffixText(" ");
        float value = (float) currentProgress;
        ObjectAnimator animation = ObjectAnimator.ofFloat(movieRatingBar,"progress", 0, value);
        animation.setDuration(2000);
        animation.setInterpolator(new OvershootInterpolator());
        animation.start();
        if(currentProgress >= 7.00) {
            movieRatingBar.setTextColor(Color.parseColor("#ffffff"));
            movieRatingBar.setFinishedStrokeColor(Color.parseColor("#25cc00"));
            movieRatingBar.setUnfinishedStrokeColor(Color.parseColor("#b8ffc3"));
            movieRatingBar.setBackgroundResource(R.drawable.good_rounded_edge);
            if (currentProgress >= 8.00) {
                movieRatingBar.setBottomText("GREAT");
            } else if (currentProgress >= 9.00) {
                movieRatingBar.setBottomText("BEST");
            } else {
                movieRatingBar.setBottomText("GOOD");
            }
        } else if (currentProgress >= 4.25) {
            movieRatingBar.setTextColor(Color.parseColor("#ffffff"));
            movieRatingBar.setFinishedStrokeColor(Color.parseColor("#f5c400"));
            movieRatingBar.setUnfinishedStrokeColor(Color.parseColor("#ffe7ab"));
            movieRatingBar.setBackgroundResource(R.drawable.mid_rounded_edge);
            if (currentProgress >= 6.00) {
                movieRatingBar.setBottomText("AVERAGE");
            } else if (currentProgress >=4.75) {
                movieRatingBar.setBottomText("OKAY");
            } else {
                movieRatingBar.setBottomText("MEH..");
            }
        } else {
            movieRatingBar.setTextColor(Color.parseColor("#ffffff"));
            movieRatingBar.setFinishedStrokeColor(Color.parseColor("#dc0202"));
            movieRatingBar.setUnfinishedStrokeColor(Color.parseColor("#ffa1a1"));
            movieRatingBar.setBackgroundResource(R.drawable.bad_rounded_edge);
            if (currentProgress >= 3.5) {
                movieRatingBar.setBottomText("MEH");
            } else if (currentProgress >= 2.00) {
                movieRatingBar.setBottomText("AVOID");
            } else if (currentProgress == 0.00) {
                movieRatingBar.setBottomText("NO SCORE");
            } else {
                movieRatingBar.setBottomText("HARD PASS");
            }
        }

    }

    private void showPosterImage() {
        poster.startAnimation(animScaleUp);
        poster.setVisibility(VISIBLE);
    }
    private void hideRatingBar() {
        movieRatingBar.startAnimation(animFadeOut);
        movieRatingBar.setVisibility(View.INVISIBLE);
        ratingTitleHeader.startAnimation(animFadeIn);
        ratingTitleHeader.setVisibility(VISIBLE);
        movieRating.startAnimation(animFadeIn);
        movieRating.setVisibility(VISIBLE);
    }

    private void hidePosterImage() {
        poster.startAnimation(animScaleDown);
        poster.setVisibility(View.INVISIBLE);
    }
    private void showRatingBar() {
        movieRatingBar.startAnimation(animFadeIn);
        movieRatingBar.setVisibility(VISIBLE);
        ratingTitleHeader.startAnimation(animFadeOut);
        ratingTitleHeader.setVisibility(View.INVISIBLE);
        movieRating.startAnimation(animFadeOut);
        movieRating.setVisibility(View.INVISIBLE);
    }


    private void populateUI(final Movie movie) {
        //Set up the Credit Recycler
        creditsRecycler = findViewById(R.id.credits_recycler);
        if (networkUtils.checkNetwork(DetailsActivity.this)) {
            //Load all data from credits json & details json
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
            creditsRecycler.setLayoutManager(linearLayoutManager);
            CreditsAdapter creditsAdapter = new CreditsAdapter(this, credits, creditsRecycler);
            creditsRecycler.setAdapter(creditsAdapter);
            CreditsLoader creditsLoader = new CreditsLoader(creditsAdapter);
            creditsLoader.execute(String.valueOf(movie.getMovieID()));
            //Set up the details for single film details
            SingleMovieLoader singleMovieLoader = new SingleMovieLoader();
            singleMovieLoader.execute(String.valueOf(movie.getMovieID()));
            try {
                arrayFilm = singleMovieLoader.get();
            } catch (ExecutionException ee) {
                ee.printStackTrace();
            } catch (InterruptedException e) {
                Log.e(TAG, "populateUI: Interrupted Exception", e);
            }
            Film currentFilm = arrayFilm.get(0);
            movieTagline.setText(currentFilm.getMovieTagLine());
            movieGenres.setText(currentFilm.getMovieGenre());
            if (currentFilm.getMovieRuntime() == 0) {
                movieRuntime.setText(R.string.unknown_time);
            } else {
                movieRuntime.setText(convertTime(currentFilm.getMovieRuntime()));
            }
        } else {
            //Load only data from Intent and add network message
            noCreditsText.setText(R.string.check_network_credits_display);
            creditsRecycler.setVisibility(View.INVISIBLE);
            movieRuntime.setText(R.string.unknown_time);
        }

        Picasso.get().load(UrlUtils.buildPosterPathUrl(movie.getMoviePosterPath())).into(poster);
        Picasso.get().load(UrlUtils.buildBackdropUrl(movie.getMovieBackdrop(), movie.getMoviePosterPath())).into(posterImage);
        movieOverview.setText(movie.getMovieOverview());

        String fullRating = String.valueOf(trimRating((float)movie.getMovieVoteAverage()));
        movieRating.setText(fullRating);
        movieRelease.setText(formatDate(movie.getMovieReleaseDate()));
    }

    private String trimRating(float fullRating) {
        Log.e(TAG, "trimRating: " + fullRating );
        String tempString = String.valueOf(fullRating);
        String filteredString = tempString.substring(0,3);
        double tempDouble = Double.parseDouble(filteredString);

        return String.format(Locale.US, "%.2f", tempDouble);
    }

    private String convertTime(int movieRuntime) {
        int hours = movieRuntime / 60;
        int minutes = movieRuntime % 60;
        return String.format(Locale.US, TIME_FORMAT, hours, minutes);
    }


    private String formatDate(String movieReleaseDate) {
        String[] datestamps = movieReleaseDate.split("-");
        String dateYear = datestamps[0];
        String dateMonth = datestamps[1];
        String dateDay = datestamps[2];
        return dateMonth + getString(R.string.linebreak) + dateDay + getString(R.string.linebreak) + dateYear;
    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbar);
        scrimView = findViewById(R.id.scrim_view);
        noCreditsText = findViewById(R.id.no_credits_view);
        creditsRecycler = findViewById(R.id.credits_recycler);
        poster = findViewById(R.id.poster);
        posterImage = findViewById(R.id.poster_imageview);
        movieRuntime = findViewById(R.id.movie_runtime);
        movieRelease = findViewById(R.id.movie_release);
        movieRating = findViewById(R.id.movie_rating);
        movieRatingBar = findViewById(R.id.progress);
        movieOverview = findViewById(R.id.synopsis_text);
        movieTagline = findViewById(R.id.movie_tagline);
        movieGenres = findViewById(R.id.movie_genres);
        ratingTitleHeader = findViewById(R.id.rating_title);

    }

}
