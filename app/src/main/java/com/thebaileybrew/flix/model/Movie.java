package com.thebaileybrew.flix.model;

import java.util.List;

public class Movie {
    //This class model is for the basic details of the film

    private int movieID;
    private int movieVoteCount;
    private float movieVoteAverage;
    private String movieTitle;
    private float moviePopularity;
    private String movieLanguage;
    private String moviePosterPath;
    private String movieOverview;
    private String movieReleaseDate;

    //No args constructor for serialization
    public Movie() {}

    public Movie(int movieID, int movieVoteCount, float movieVoteAverage, String movieTitle,
                 float moviePopularity, String movieLanguage, String moviePosterPath,
                 String movieOverview, String movieReleaseDate) {
        this.movieID = movieID;
        this.movieVoteCount = movieVoteCount;
        this.movieVoteAverage = movieVoteAverage;
        this.movieTitle = movieTitle;
        this.moviePopularity = moviePopularity;
        this.movieLanguage = movieLanguage;
        this.moviePosterPath = moviePosterPath;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
    }

    public int getMovieID() {
        return movieID;
    }

    public int getMovieVoteCount() {
        return movieVoteCount;
    }

    public float getMovieVoteAverage() {
        return movieVoteAverage;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public float getMoviePopularity() {
        return moviePopularity;
    }

    public String getMovieLanguage() {
        return movieLanguage;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }
}
