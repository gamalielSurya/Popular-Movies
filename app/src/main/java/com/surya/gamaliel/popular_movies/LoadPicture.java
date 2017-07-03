package com.surya.gamaliel.popular_movies;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class LoadPicture {

    public void DrawPicasso(Context context, String mLoad, ImageView dPoster) {
        Picasso
                .with(context)
                .load("http://image.tmdb.org/t/p/w185/"+mLoad)
                .fit()
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .into(dPoster);
    }
}
