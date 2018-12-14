package com.example.android.filmespopulares;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.filmespopulares.utilities.NetworkUtils;
import com.example.android.filmespopulares.utilities.TmdbJsonUtils;

import java.net.URL;
import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private URL mUrl;

    public MovieLoader(Context context, URL url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Movie> movies = null;
        String tMdbSearchResults = null;

        try {
            tMdbSearchResults = NetworkUtils.getResponseFromHttpUrl(mUrl);
            movies = TmdbJsonUtils.getMoviesFromJson(tMdbSearchResults);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
}
