package com.thebaileybrew.flix;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thebaileybrew.flix.interfaces.MovieAdapter;
import com.thebaileybrew.flix.loaders.MovieLoader;
import com.thebaileybrew.flix.model.Movie;
import com.thebaileybrew.flix.ui.MoviePreferences;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static android.view.View.VISIBLE;

public class MovieActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterClickHandler {


    private final static String SAVE_STATE = "save_state";
    private final static String RECYCLER_STATE = "recycler_state";
    private final static String MOVIE_KEY = "parcel_movie";

    private Parcelable savedRecyclerState;

    private RecyclerView mRecyclerView;
    private ArrayList<Movie> movies = new ArrayList<>();
    private ConstraintLayout noNetworkLayout;
    private GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        if (savedInstanceState == null || !savedInstanceState.containsKey(SAVE_STATE)) {
            movies = new ArrayList<>();
        } else {
            movies = savedInstanceState.getParcelableArrayList(SAVE_STATE);
            savedRecyclerState = savedInstanceState.getParcelable(RECYCLER_STATE);
        }

        mRecyclerView = findViewById(R.id.movie_recycler);
        noNetworkLayout = findViewById(R.id.no_connection_constraint_layout);
        gridLayoutManager = new GridLayoutManager(this, 2);
        swipeRefresh = findViewById(R.id.swipe_refresh);

        setSwipeRefreshListener();

        if (checkNetworkStatus()) {
            //Load Movies
            noNetworkLayout.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(VISIBLE);
            loadMoviesFromPrefs();
        } else {
            //Show no connection layout
            mRecyclerView.setVisibility(View.INVISIBLE);
            noNetworkLayout.setVisibility(VISIBLE);
            swipeRefresh.setRefreshing(false);
        }
    }

    private void setSwipeRefreshListener() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (checkNetworkStatus()) {
                    //Load Movies
                    noNetworkLayout.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(VISIBLE);
                    loadMoviesFromPrefs();
                } else {
                    //Show no connection layout
                    noNetworkLayout.setVisibility(VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    getRandomNoNetworkView();

                }
            }
        });
    }

    private void getRandomNoNetworkView() {
        TextView noNetworkTextMessageOne = findViewById(R.id.internet_out_message);
        ImageView noNetworkImage = findViewById(R.id.internet_out_image);
        Random randomNetworkGen = new Random();
        int i = randomNetworkGen.nextInt((5 - 1) + 1);
        switch (i) {
            case 1:
                noNetworkTextMessageOne.setText(getString(R.string.internet_message_one));
                noNetworkImage.setImageResource(R.drawable.voldemort);
                break;
            case 2:
                noNetworkTextMessageOne.setText(getString(R.string.internet_message_two));
                noNetworkImage.setImageResource(R.drawable.wonka);
                break;
            case 3:
                noNetworkTextMessageOne.setText(getString(R.string.internet_message_three));
                noNetworkImage.setImageResource(R.drawable.lotr);
                break;
            case 4:
                noNetworkTextMessageOne.setText(getString(R.string.internet_message_four));
                noNetworkImage.setImageResource(R.drawable.taken);
                break;
            default:
                noNetworkTextMessageOne.setText(getString(R.string.internet_message_default));
                noNetworkImage.setImageResource(R.drawable.thanos);
                break;

        }
        swipeRefresh.setRefreshing(false);
    }

    private void loadMoviesFromPrefs() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String sortingKey = getString(R.string.preference_sort_key);
        String sortingDefault = getString(R.string.preference_sort_popular);
        String sorting = sharedPrefs.getString(sortingKey, sortingDefault);

        String languageKey = getString(R.string.preference_sort_language_key);
        String languageDefault = getString(R.string.preference_sort_language_all);
        String language = sharedPrefs.getString(languageKey, languageDefault);

        //Set up the Adapter
        MovieAdapter mMovieAdapter = new MovieAdapter(this, movies, this);
        //Assign the adapter to the Loader
        MovieLoader movieLoader = new MovieLoader(mMovieAdapter);
        movieLoader.execute(sorting, language);
        //Set up the Recycler
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        //Assign the adapter to the Recycler
        mRecyclerView.setAdapter(mMovieAdapter);
        mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerState);
        swipeRefresh.setRefreshing(false);


    }

    private boolean checkNetworkStatus() {
        //Check for network status
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        boolean hasNetworkConn = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            hasNetworkConn = true;
        }
        return hasNetworkConn;
    }

    //Custom onclick for loading movie details based on selection in Recycler
    @Override
    public void onClick(Movie movie) {
        Intent openDisplayDetails = new Intent(MovieActivity.this, DetailsActivity.class);
        //Put Parcel Extra
        openDisplayDetails.putExtra(MOVIE_KEY, movie);
        startActivity(openDisplayDetails);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(SAVE_STATE, movies);
        //Declare the Recycler State
        outState.putParcelable(RECYCLER_STATE, mRecyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_prefs:
                Intent openSettings = new Intent(this, MoviePreferences.class);
                startActivity(openSettings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
