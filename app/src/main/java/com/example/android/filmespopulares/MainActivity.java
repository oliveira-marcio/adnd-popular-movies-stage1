package com.example.android.filmespopulares;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.filmespopulares.MovieAdapter.MovieAdapterOnItemClickListener;
import com.example.android.filmespopulares.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>, MovieAdapterOnItemClickListener {

    private static final int MOVIE_LOADER_ID = 1;

    private RecyclerView mMoviesList;
    private MovieAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mLoadingIndicator;

    private List<Movie> mMoviesData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUIElements();

        int pixelWidth = Integer.parseInt(getResources().getString(R.string.thumb_size));
        GridLayoutManager layoutManager = new GridLayoutManager(this, calculateBestSpanCount(pixelWidth));

        mMoviesList.setLayoutManager(layoutManager);
        mMoviesList.setHasFixedSize(true);

        mAdapter = new MovieAdapter(mMoviesData, this);
        mMoviesList.setAdapter(mAdapter);

        if (hasInternetConnection()) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            getLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
            showErrorMessage(R.string.no_internet_connection);
        }
    }

    public void initializeUIElements() {
        mMoviesList = (RecyclerView) findViewById(R.id.rv_movies);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadMovies();
            }
        });
    }

    /**
     * Calcula a quantidade ótima de colunas para o GridLayout de acordo com o tamanho da tela e
     * a largura dos posters solcitados
     */
    private int calculateBestSpanCount(int posterWidth) {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }

    private void showMoviesDataView() {
        mEmptyStateTextView.setVisibility(View.INVISIBLE);
        mMoviesList.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(int errorId) {
        mEmptyStateTextView.setText(errorId);
        mEmptyStateTextView.setVisibility(View.VISIBLE);
        mMoviesList.setVisibility(View.INVISIBLE);
    }

    public boolean hasInternetConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected()) ? true : false;
    }

    public void reloadMovies() {
        mAdapter.clear();
        mSwipeRefreshLayout.setRefreshing(false);
        mEmptyStateTextView.setVisibility(View.GONE);
        if (hasInternetConnection()) {
            getLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
        } else {
            showErrorMessage(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        SharedPreferences settings = getSharedPreferences(getString(R.string.settings_shared_preferences), 0);
        String sortBy = settings.getString(getString(R.string.settings_sort_order), NetworkUtils.SORT_BY_POPULAR);
        URL url = NetworkUtils.buildUrl(sortBy);
        return new MovieLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        mLoadingIndicator.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.clear();

        if (movies != null) {
            showMoviesDataView();
            mMoviesData = movies;
            mAdapter.addAll(mMoviesData);
        } else {
            showErrorMessage(R.string.no_movies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        mAdapter.clear();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, mMoviesData.get(clickedItemIndex));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        SharedPreferences settings = getSharedPreferences(getString(R.string.settings_shared_preferences), 0);
        String settingsSaved = settings.getString(getString(R.string.settings_sort_order), NetworkUtils.SORT_BY_POPULAR);

        int menuId = (settingsSaved.equals(NetworkUtils.SORT_BY_POPULAR)) ? R.id.action_popular : R.id.action_top_rated;
        MenuItem item = menu.findItem(menuId);
        item.setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id != R.id.action_popular && id != R.id.action_top_rated) {
            return super.onOptionsItemSelected(item);
        }

        if (!item.isChecked()) {
            item.setChecked(true);
            SharedPreferences settings = getSharedPreferences(getString(R.string.settings_shared_preferences), 0);
            SharedPreferences.Editor editor = settings.edit();

            String settingToSave = (id == R.id.action_top_rated) ? NetworkUtils.SORT_BY_TOP_RATED : NetworkUtils.SORT_BY_POPULAR;
            editor.putString(getString(R.string.settings_sort_order), settingToSave);
            editor.apply();

            reloadMovies();
        }

        return super.onOptionsItemSelected(item);
    }
}
