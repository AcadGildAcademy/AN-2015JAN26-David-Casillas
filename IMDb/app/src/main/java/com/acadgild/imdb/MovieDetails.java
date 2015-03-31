package com.acadgild.imdb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MovieDetails extends Fragment {

    private static final String TAG_TITLE = "title";
    private static final String TAG_RELEASE_DATE = "release_date";
    private static final String TAG_POSTER_PATH = "poster_path";
    private static final String TAG_VOTE_AVERAGE = "vote_average";
    private static final String TAG_VOTE_COUNT = "vote_count";
    private static final String TAG_BUDGET = "budget";
    private static final String TAG_REVENUE = "revenue";
    private static final String TAG_STATUS = "status";
    private static final String TAG_OVERVIEW = "overview";
    private static final String TAG_TAGLINE = "tagline";
    private static final String BASE_URL = "http://api.themoviedb.org/";
    private static final String API_VERSION = "3";
    private static final String API_KEY = "?api_key=8496be0b2149805afa458ab8ec27560c";
    public String URL;
    public TextView title;
    public TextView tagline;
    public TextView releaseDate;
    public TextView budget;
    public TextView revenue;
    public TextView status;
    public TextView voteCount;
    public TextView overView;
    public RatingBar ratingBar;
    public ImageView poster;
    public ImageView favorites;
    public ImageView watchlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_details, container, false);

        title = (TextView) view.findViewById(R.id.titleTextView2);
        tagline = (TextView) view.findViewById(R.id.smallDescriptionTextView);
        releaseDate = (TextView) view.findViewById(R.id.releaseDateTextView2);
        budget = (TextView) view.findViewById(R.id.budgetTextView);
        revenue = (TextView) view.findViewById(R.id.revenueTextView);
        status = (TextView) view.findViewById(R.id.statusTextView);
        voteCount = (TextView) view.findViewById(R.id.voteCountTextView2);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar2);
        poster = (ImageView) view.findViewById(R.id.movieImageView2);
        overView = (TextView) view.findViewById(R.id.descriptionTextView);
        favorites = (ImageView) view.findViewById(R.id.favoritesImageView);
        favorites.setImageResource(R.drawable.favorite_disable_normal);
        favorites.setTag("disable");
        watchlist = (ImageView) view.findViewById(R.id.watchlistImageView);
        watchlist.setImageResource(R.drawable.watchlist_disable_normal);
        watchlist.setTag("disable");

        setHasOptionsMenu(true);

        String movieID = getArguments().getString("MovieID");
        URL = BASE_URL + API_VERSION + "/movie/" + movieID + API_KEY;

        try {
            MovieInfo movieInfo = new GetSingleMovieInfo().execute(URL).get();
            title.setText(movieInfo.getTitle());
            tagline.setText(movieInfo.getTagline());
            releaseDate.setText(movieInfo.getDate());
            budget.setText("$" + movieInfo.getBudget());
            revenue.setText("$" + movieInfo.getRevenue());
            status.setText(movieInfo.getStatus());
            overView.setText(movieInfo.getOverview());
            voteCount.setText("(" + movieInfo.getVote_average() + "/10) voted by " + movieInfo.getVote_count() + " users");
            ratingBar.setRating(Float.parseFloat(movieInfo.getVote_average()) / 2);
            if (movieInfo.getPoster().equals("null")) {
                poster.setImageResource(R.drawable.loading_image);
            } else {
                new DownloadImageTask(poster).execute("http://image.tmdb.org/t/p/w500" + movieInfo.getPoster());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = favorites.getTag();
                if(tag == "disable") {
                    favorites.setImageResource(R.drawable.favorite_enable_normal);
                    favorites.setTag("enable");
                } else {
                    favorites.setImageResource(R.drawable.favorite_disable_normal);
                    favorites.setTag("disable");
                }
            }
        });

        watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = watchlist.getTag();
                if(tag == "disable") {
                    watchlist.setImageResource(R.drawable.watchlist_enable_normal);
                    watchlist.setTag("enable");
                } else {
                    watchlist.setImageResource(R.drawable.watchlist_disable_normal);
                    watchlist.setTag("disable");
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {

            case R.id.addFavorite:

                return true;

            case R.id.addWatchlist:

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetSingleMovieInfo extends AsyncTask<String, Void, MovieInfo> {

        @Override
        protected MovieInfo doInBackground(String... urls) {
            ServiceHandler sh = new ServiceHandler();
            String jsonStr = sh.makeServiceCall(urls[0], ServiceHandler.GET);

            if (jsonStr != null) {
                try {
                    JSONObject o = new JSONObject(jsonStr);

                    MovieInfo movieInfo = new MovieInfo();
                    title.setText(o.getString(TAG_TITLE));
                    movieInfo.setTitle(o.getString(TAG_TITLE));
                    movieInfo.setDate(o.getString(TAG_RELEASE_DATE));
                    movieInfo.setPoster(o.getString(TAG_POSTER_PATH));
                    movieInfo.setVote_average(o.getString(TAG_VOTE_AVERAGE));
                    movieInfo.setVote_count(o.getString(TAG_VOTE_COUNT));
                    movieInfo.setBudget(o.getString(TAG_BUDGET));
                    movieInfo.setRevenue(o.getString(TAG_REVENUE));
                    movieInfo.setTagline(o.getString(TAG_TAGLINE));
                    movieInfo.setStatus(o.getString(TAG_STATUS));
                    movieInfo.setOverview(o.getString(TAG_OVERVIEW));

                    return movieInfo;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return null;
        }

        @Override
        protected void onPostExecute(MovieInfo result) {
            super.onPostExecute(result);

            if (result == null) {

                Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
            }
        }
    }

}
