package com.thebaileybrew.flix.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movies")
public class MovieEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int movieID;
    private int movieVoteCount;
    private long movieVoteAverage;
    private String movieTitle;
    private long moviePopularity;
    private String movieLanguage;
    private String moviePosterPath;
    private String movieBackdrop;
    private String movieOverview;
    private String movieReleaseDate;

    private String movieTagLine;
    private int movieRuntime;
    private String movieGenre;

    private String creditCharacterName;
    private String creditActorName;
    private String creditPath;

    public MovieEntry(int movieID, int movieVoteCount, long movieVoteAverage, String movieTitle,
                      long moviePopularity, String movieLanguage, String moviePosterPath,
                      String movieBackdrop, String movieOverview, String movieReleaseDate,
                      String movieTagLine, int movieRuntime, String movieGenre,
                      String creditCharacterName, String creditActorName, String creditPath) {
        this.movieID = movieID;
        this.movieVoteCount = movieVoteCount;
        this.movieVoteAverage = movieVoteAverage;
        this.movieTitle = movieTitle;
        this.moviePopularity = moviePopularity;
        this.movieLanguage = movieLanguage;
        this.moviePosterPath = moviePosterPath;
        this.movieBackdrop = movieBackdrop;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
        this.movieTagLine = movieTagLine;
        this.movieRuntime = movieRuntime;
        this.movieGenre = movieGenre;
        this.creditCharacterName = creditCharacterName;
        this.creditActorName = creditActorName;
        this.creditPath = creditPath;
    }

    public int getMovieID() {
        return movieID;
    }
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public int getMovieVoteCount() {
        return movieVoteCount;
    }
    public void setMovieVoteCount(int movieVoteCount) {
        this.movieVoteCount = movieVoteCount;
    }

    public long getMovieVoteAverage() {
        return movieVoteAverage;
    }
    public void setMovieVoteAverage(long movieVoteAverage) {
        this.movieVoteAverage = movieVoteAverage;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public long getMoviePopularity() {
        return moviePopularity;
    }
    public void setMoviePopularity(long moviePopularity) {
        this.moviePopularity = moviePopularity;
    }

    public String getMovieLanguage() {
        return movieLanguage;
    }
    public void setMovieLanguage(String movieLanguage) {
        this.movieLanguage = movieLanguage;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }
    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public String getMovieBackdrop() {
        return movieBackdrop;
    }
    public void setMovieBackdrop(String movieBackdrop) {
        this.movieBackdrop = movieBackdrop;
    }

    public String getMovieOverview() {
        return movieOverview;
    }
    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }
    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieTagLine() {
        return movieTagLine;
    }
    public void setMovieTagLine(String movieTagLine) {
        this.movieTagLine = movieTagLine;
    }

    public int getMovieRuntime() {
        return movieRuntime;
    }
    public void setMovieRuntime(int movieRuntime) {
        this.movieRuntime = movieRuntime;
    }

    public String getMovieGenre() {
        return movieGenre;
    }
    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public String getCreditCharacterName() {
        return creditCharacterName;
    }
    public void setCreditCharacterName(String creditCharacterName) {
        this.creditCharacterName = creditCharacterName;
    }

    public String getCreditActorName() {
        return creditActorName;
    }
    public void setCreditActorName(String creditActorName) {
        this.creditActorName = creditActorName;
    }

    public String getCreditPath() {
        return creditPath;
    }
    public void setCreditPath(String creditPath) {
        this.creditPath = creditPath;
    }
}
