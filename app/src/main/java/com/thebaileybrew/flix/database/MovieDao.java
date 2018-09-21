package com.thebaileybrew.flix.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    List<MovieEntry> loadAllMovies();

    void insertMovie(MovieEntry movieEntry);

    void updateMovie(MovieEntry movieEntry);

    void deleteMovie(MovieEntry movieEntry);
}
