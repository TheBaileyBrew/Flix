package com.thebaileybrew.flix.interfaces;

import com.thebaileybrew.flix.model.Movie;

import java.util.ArrayList;

public class MovieData extends ArrayList {
    private static ArrayList<Movie> mMovieCollection = new ArrayList<>();


    public static ArrayList getMovies() {
        return mMovieCollection;
    };

    public static void loadMovies(ArrayList<Movie> movieCollection) {
        mMovieCollection = movieCollection;
    }
}
