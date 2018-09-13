package com.thebaileybrew.flix.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Movie implements Parcelable{
    //This class model is for the basic details of the film

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    //No args constructor for serialization
    public Movie() {}

    public Movie(int movieID, int movieVoteCount, long movieVoteAverage, String movieTitle,
                 long moviePopularity, String movieLanguage, String moviePosterPath,
                 String movieBackdrop, String movieOverview, String movieReleaseDate) {
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
    }

    private Movie(Parcel in) {
        movieID = in.readInt();
        movieVoteCount = in.readInt();
        movieVoteAverage = in.readLong();
        movieTitle = in.readString();
        moviePopularity = in.readLong();
        movieLanguage = in.readString();
        moviePosterPath = in.readString();
        movieBackdrop = in.readString();
        movieOverview = in.readString();
        movieReleaseDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(movieID);
        parcel.writeInt(movieVoteCount);
        parcel.writeLong(movieVoteAverage);
        parcel.writeString(movieTitle);
        parcel.writeLong(moviePopularity);
        parcel.writeString(movieLanguage);
        parcel.writeString(moviePosterPath);
        parcel.writeString(movieBackdrop);
        parcel.writeString(movieOverview);
        parcel.writeString(movieReleaseDate);
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

    public String getMovieBackdrop() {
        return movieBackdrop;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }
}
