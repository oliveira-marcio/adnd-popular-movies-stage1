package com.example.android.filmespopulares;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.filmespopulares.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private TextView mSynopsisTextView;
    private TextView mReleaseDateTextView;
    private TextView mAvgRatingTextView;
    private TextView mRatingsTextView;
    private ImageView mPosterImage;
    private Movie mMovie;

    private final String SAVE_STATE_KEY = "movies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if(savedInstanceState == null) {
            Intent intent = getIntent();

            if (intent != null) {
                if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                    mMovie = intent.getExtras().getParcelable(Intent.EXTRA_TEXT);
                }
            }
        } else {
            if(savedInstanceState.containsKey(SAVE_STATE_KEY)) {
                mMovie = savedInstanceState.getParcelable(SAVE_STATE_KEY);
            }
        }

        if(mMovie != null){
            initializeUIElements();
            loadUIData();
        } else {
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVE_STATE_KEY, mMovie);
        super.onSaveInstanceState(outState);
    }

    public void initializeUIElements() {
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mSynopsisTextView = (TextView) findViewById(R.id.tv_synopsis);
        mReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);
        mAvgRatingTextView = (TextView) findViewById(R.id.tv_average_rating);
        mRatingsTextView = (TextView) findViewById(R.id.tv_user_rating);
        mPosterImage = (ImageView) findViewById(R.id.image_poster);
    }

    public void loadUIData(){
        mTitleTextView.setText(mMovie.getTitle());
        mSynopsisTextView.setText(mMovie.getSynopsis());
        mReleaseDateTextView.setText(formatDate(mMovie.getReleaseDate()));
        mAvgRatingTextView.setText("" + mMovie.getAverageRating());
        mRatingsTextView.setText("" + mMovie.getRatingsCount());

        Picasso.with(this).
                load(NetworkUtils.buildPosterUrl(mMovie.getPoster(), getString(R.string.thumb_size)).toString()).
                placeholder(R.drawable.placeholder).
                into(mPosterImage);
    }

    public String formatDate(String dateString) {
        SimpleDateFormat firstFormatter, secondFormatter;
        firstFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date oldDate = firstFormatter.parse(dateString.replaceFirst(":(?=[0-9]{2}$)", ""));
            secondFormatter = new SimpleDateFormat("dd/LLL/yyyy");
            return secondFormatter.format(oldDate);
        } catch (java.text.ParseException e) {
            return dateString;
        }
    }
}
