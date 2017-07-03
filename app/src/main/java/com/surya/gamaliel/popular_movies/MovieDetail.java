package com.surya.gamaliel.popular_movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetail extends AppCompatActivity {

    private static final String CLICKED_MOVIE = "clicked_movie";

    private TextView dTitle;
    private TextView dOverview;
    private TextView dReleasedate;
    private ImageView dPoster;
    private TextView dVoteaverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        dTitle = (TextView) findViewById(R.id.det_title);
        dOverview = (TextView) findViewById(R.id.det_overview);
        dReleasedate = (TextView) findViewById(R.id.det_releasedate);
        dPoster = (ImageView) findViewById(R.id.det_poster);
        dVoteaverage = (TextView) findViewById(R.id.det_vote_average);

        Intent starterIntent = getIntent();

        if(starterIntent != null) {
            if (starterIntent.hasExtra(CLICKED_MOVIE)) {
                Movie mMovie = starterIntent.getParcelableExtra(CLICKED_MOVIE);

                dTitle.setText(mMovie.getTitle());
                dOverview.setText(mMovie.getOverview());

                String relDate = getResources().getString(R.string.det_release) + mMovie.getRelease();
                dReleasedate.setText(relDate);

                String userRating = getResources().getString(R.string.det_rating) + mMovie.getVote();
                dVoteaverage.setText(userRating);

                LoadPicture loadPicture = new LoadPicture();
                loadPicture.DrawPicasso(this, mMovie.getPoster(), dPoster);
            }
        }
    }
}
