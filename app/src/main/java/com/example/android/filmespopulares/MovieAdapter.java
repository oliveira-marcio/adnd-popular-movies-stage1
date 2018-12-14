package com.example.android.filmespopulares;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.filmespopulares.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    final private MovieAdapterOnItemClickListener mOnClickListener;

    private List<Movie> mMovie;
    private Context mContext;

    public interface MovieAdapterOnItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MovieAdapter(List<Movie> movie, MovieAdapterOnItemClickListener listener) {
        mMovie = movie;
        mOnClickListener = listener;
    }

    // Métodos clear() e addAll() criados para simular os equivalentes de um Adapter de ListView
    public void clear() {
        mMovie.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Movie> movie) {
        mMovie.clear();
        mMovie.addAll(movie);
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        String imagePath = mMovie.get(position).getPoster();
        Picasso.with(mContext).
                load(NetworkUtils.buildPosterUrl(imagePath, mContext.getString(R.string.thumb_size)).toString()).
                placeholder(R.drawable.placeholder).
                into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        if (mMovie == null) return 0;
        return mMovie.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView moviePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            moviePoster = (ImageView) itemView.findViewById(R.id.image_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
