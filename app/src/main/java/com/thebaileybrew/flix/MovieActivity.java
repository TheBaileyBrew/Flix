package com.thebaileybrew.flix;

import android.os.Bundle;
import android.widget.Toast;

import com.thebaileybrew.flix.interfaces.MovieAdapter;
import com.thebaileybrew.flix.interfaces.MovieData;
import com.thebaileybrew.flix.loaders.MovieLoader;
import com.thebaileybrew.flix.model.Movie;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    MovieAdapter mMovieAdapter;
    ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        mRecyclerView = findViewById(R.id.movie_recycler);
        mMovieAdapter = new MovieAdapter(this,movies);
        MovieLoader movieLoader = new MovieLoader(mMovieAdapter);
        movieLoader.execute();

        Toast.makeText(this, "movie size" + movies.size(), Toast.LENGTH_SHORT).show();

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);
    }
}
