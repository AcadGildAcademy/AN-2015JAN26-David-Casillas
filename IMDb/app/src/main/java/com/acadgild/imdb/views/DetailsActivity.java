package com.acadgild.imdb.views;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.acadgild.imdb.R;
import com.acadgild.imdb.fragment.MovieDetails;
import com.acadgild.imdb.fragment.MovieList;

public class DetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setArguments(getIntent().getExtras());
        transaction.replace(R.id.fragmentContainer2, movieDetails);
        transaction.commit();
    }
}
