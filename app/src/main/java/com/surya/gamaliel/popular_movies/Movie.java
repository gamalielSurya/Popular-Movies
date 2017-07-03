package com.surya.gamaliel.popular_movies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    public static final Parcelable.Creator<Movie> CREATOR = new
            Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private String title;
    private String poster;
    private String overview;
    private String release;
    private String vote;

    public Movie(String title, String poster, String overview, String release, String vote) {
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.release = release;
        this.vote = vote;
    }

    public Movie(Parcel in) {
        this.title = in.readString();
        this.poster = in.readString();
        this.overview = in.readString();
        this.release = in.readString();
        this.vote = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease() {
        return release;
    }

    public String getVote() {
        return vote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.poster);
        dest.writeString(this.overview);
        dest.writeString(this.release);
        dest.writeString(this.vote);
    }
}