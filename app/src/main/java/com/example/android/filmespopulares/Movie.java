package com.example.android.filmespopulares;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String mTitle;
    private String mPoster;
    private String mSynopsis;
    private double mAverageRating;
    private int mRatingsCount;
    private String mReleaseDate;

    /**
     * Contrói um novo objeto {@link Movie}.
     *
     * @param title         é o título do filme
     * @param poster        é o nome do arquivo do filme
     * @param synopsis      é a sinopse do filme
     * @param averageRating é a média de avaliação do filme por usuários
     * @param ratingsCount  é o total de avaliações de usuários
     * @param releaseDate   é a data de lançamento do filme
     */
    public Movie(String title, String poster, String synopsis, double averageRating, int ratingsCount, String releaseDate) {
        mTitle = title;
        mPoster = poster;
        mSynopsis = synopsis;
        mAverageRating = averageRating;
        mRatingsCount = ratingsCount;
        mReleaseDate = releaseDate;
    }

    public Movie(Parcel in) {
        mTitle = in.readString();
        mPoster = in.readString();
        mSynopsis = in.readString();
        mAverageRating = in.readDouble();
        mRatingsCount = in.readInt();
        mReleaseDate = in.readString();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPoster() {
        return mPoster;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public double getAverageRating() {
        return mAverageRating;
    }

    public int getRatingsCount() {
        return mRatingsCount;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mTitle);
        parcel.writeString(mPoster);
        parcel.writeString(mSynopsis);
        parcel.writeDouble(mAverageRating);
        parcel.writeInt(mRatingsCount);
        parcel.writeString(mReleaseDate);
    }

    static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
