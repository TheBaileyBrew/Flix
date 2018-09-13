package com.thebaileybrew.flix.interfaces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thebaileybrew.flix.R;
import com.thebaileybrew.flix.model.Movie;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<Movie> movieCollection;

    //Create the recycler
    public MovieAdapter(Context context, ArrayList<Movie> movieCollection) {
        this.layoutInflater = LayoutInflater.from(context);
        this.movieCollection = movieCollection;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.movie_card_view, parent, false);
        return new ViewHolder(view);
    }

    //Bind the Arraydata to the layoutview
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie currentMovie = movieCollection.get(position);
        holder.movieVoteAverage.setProgress(Float.floatToIntBits(currentMovie.getMovieVoteAverage()));
        holder.movieTitle.setText(currentMovie.getMovieTitle());
        String moviePosterPath = currentMovie.getMoviePosterPath();
        Picasso.get()
                .load(moviePosterPath)
                .into(holder.moviePoster);
        //TODO:add imageSource link
    }

    @Override
    public int getItemCount() {
        if (movieCollection == null) {
            return 0;
        } else {
            return movieCollection.size();
        }
    }

    public void setMovieCollection(ArrayList<Movie> movieReturn) {
        movieCollection = movieReturn;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        ProgressBar movieVoteAverage;
        ImageView moviePoster;

        private ViewHolder(View newView) {
            super(newView);
            movieTitle = newView.findViewById(R.id.movie_cardview_title);
            movieVoteAverage = newView.findViewById(R.id.movie_cardview_votes);
            moviePoster = newView.findViewById(R.id.movie_cardview_poster);
        }
    }
}
