package com.thebaileybrew.flix.interfaces;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.squareup.picasso.Picasso;
import com.thebaileybrew.flix.R;
import com.thebaileybrew.flix.model.Movie;
import com.thebaileybrew.flix.utils.UrlUtils;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private final static String TAG = MovieAdapter.class.getSimpleName();

    private final LayoutInflater layoutInflater;
    private ArrayList<Movie> movieCollection;

    final private MovieAdapterClickHandler adapterClickHandler;

    public interface MovieAdapterClickHandler {
        void onClick(Movie movie);
    }

    //Create the recycler
    public MovieAdapter(Context context, ArrayList<Movie> movieCollection, MovieAdapterClickHandler clicker) {
        this.layoutInflater = LayoutInflater.from(context);
        this.movieCollection = movieCollection;
        this.adapterClickHandler = clicker;
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
        holder.movieVoteAverage.setRating((currentMovie.getMovieVoteAverage() / 2));
        String moviePosterPath = UrlUtils.buildPosterPathUrl(currentMovie.getMoviePosterPath());
        Log.e(TAG, "onBindViewHolder: current poster path" + moviePosterPath);
        Picasso.get()
                .load(moviePosterPath)
                .placeholder(R.drawable.movie_poster)
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final RatingBar movieVoteAverage;
        final ImageView moviePoster;

        private ViewHolder(View newView) {
            super(newView);
            movieVoteAverage = newView.findViewById(R.id.movie_vote_rating);
            moviePoster = newView.findViewById(R.id.movie_cardview_poster);
            newView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Movie currentMovie = movieCollection.get(getAdapterPosition());
            adapterClickHandler.onClick(currentMovie);
        }
    }
}
