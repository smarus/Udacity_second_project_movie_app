package com.example.ruslan.udacity_second_project;

import java.io.Serializable;

/**
 * Created by ruslan on 6/26/16.
 */
public class Movie implements Serializable {
    String title;
    String overview;
    String poster_path;
    String release_date;
    String vote_average;

    public Movie(String title, String overview, String poster_path, String release_date, String vote_average) {
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVote_average() {
        return vote_average;
    }
}
