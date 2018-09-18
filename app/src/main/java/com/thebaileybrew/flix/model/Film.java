package com.thebaileybrew.flix.model;

public class Film {

    private final String movieTagLine;
    private final int movieRuntime;
    private final String movieGenre;

    public Film(String movieTagLine, int movieRuntime, String movieGenre) {
        this.movieTagLine = movieTagLine;
        this.movieRuntime = movieRuntime;
        this.movieGenre = movieGenre;
    }

    public String getMovieTagLine() {
        return movieTagLine;
    }

    public int getMovieRuntime() {
        return movieRuntime;
    }

    public String getMovieGenre() {
        return movieGenre;
    }
}
