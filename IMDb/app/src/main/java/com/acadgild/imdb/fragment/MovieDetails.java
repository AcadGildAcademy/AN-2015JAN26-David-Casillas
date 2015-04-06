package com.acadgild.imdb.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.acadgild.imdb.R;
import com.acadgild.imdb.async.DownloadImageTask;
import com.acadgild.imdb.async.GetCastCrew;
import com.acadgild.imdb.async.GetMovieDetails;
import com.acadgild.imdb.model.Cast;
import com.acadgild.imdb.model.Constants;
import com.acadgild.imdb.model.MovieInfo;
import com.acadgild.imdb.utils.ImageLoader;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieDetails extends Fragment {

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
    public String movieID;
    private ImageLoader imageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.movie_details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        title = (TextView) getActivity().findViewById(R.id.titleTextView2);
        tagline = (TextView) getActivity().findViewById(R.id.smallDescriptionTextView);
        releaseDate = (TextView) getActivity().findViewById(R.id.releaseDateTextView2);
        budget = (TextView) getActivity().findViewById(R.id.budgetTextView);
        revenue = (TextView) getActivity().findViewById(R.id.revenueTextView);
        status = (TextView) getActivity().findViewById(R.id.statusTextView);
        voteCount = (TextView) getActivity().findViewById(R.id.voteCountTextView2);
        ratingBar = (RatingBar) getActivity().findViewById(R.id.ratingBar2);
        poster = (ImageView) getActivity().findViewById(R.id.movieImageView2);
        overView = (TextView) getActivity().findViewById(R.id.descriptionTextView);
        favorites = (ImageView) getActivity().findViewById(R.id.favoritesImageView);
        favorites.setImageResource(R.drawable.favorite_disable_normal);
        favorites.setTag("disable");
        watchlist = (ImageView) getActivity().findViewById(R.id.watchlistImageView);
        watchlist.setImageResource(R.drawable.watchlist_disable_normal);
        watchlist.setTag("disable");

        movieID = getArguments().getString("MovieID");
        Log.e("movieID", movieID);

        URL = Constants.BASE_URL + Constants.API_VERSION + "/movie/" + movieID + Constants.API_KEY;

        try {
            MovieInfo movieInfo = new GetMovieDetails(getActivity()).execute(URL).get();
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
                poster.setImageResource(R.drawable.no_image);
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

        showCast();
        showCrew();
        showTrailers();
        showPosters();
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

    private void showCast() {

        LinearLayout castsSection = (LinearLayout) getActivity().findViewById(R.id.casts_section);

        URL = Constants.BASE_URL + Constants.API_VERSION + "/movie/" + movieID + "/credits" + Constants.API_KEY;

        try {
        List<Cast> castList = new GetCastCrew(getActivity(), Constants.TAG_CAST).execute(URL).get();

            if (castList != null && !castList.isEmpty()) {
                castsSection.setVisibility(View.VISIBLE);
                setCasts(castList);
            } else {
                castsSection.setVisibility(View.GONE);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    // Add multiple casts in the casts container
    private void setCasts(List<Cast> casts) {

        LinearLayout castsContainer = (LinearLayout) getActivity().findViewById(R.id.casts_container);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = new ImageLoader(getActivity());

        int size = casts.size();
        for (int i = 0; i < size; i++) {
            Cast cast = casts.get(i);

            if (cast != null) {

                LinearLayout clickableColumn = (LinearLayout) inflater.inflate(R.layout.column_cast_crew, null);
                ImageView thumbnailImage = (ImageView) clickableColumn.findViewById(R.id.thumbnail_image);
                TextView titleView = (TextView) clickableColumn.findViewById(R.id.title_view);
                TextView subTitleView = (TextView) clickableColumn.findViewById(R.id.subtitle_view);

                if (cast.getProfilePath().equals("null")) {
                    thumbnailImage.setImageResource(R.drawable.no_image);
                } else {
                    imageLoader.DisplayImage("http://image.tmdb.org/t/p/w500" + cast.getProfilePath(), thumbnailImage);
                }

                titleView.setText(cast.getName());
                subTitleView.setText(cast.getCharacter());

                castsContainer.addView(clickableColumn);

                if (i != size - 1) {
                    castsContainer.addView(inflater.inflate(R.layout.horizontal_divider, null));
                }
            }
        }
    }

    private void showCrew() {

        LinearLayout castsSection = (LinearLayout) getActivity().findViewById(R.id.crew_section);

        URL = Constants.BASE_URL + Constants.API_VERSION + "/movie/" + movieID + "/credits" + Constants.API_KEY;

        try {
            List<Cast> castList = new GetCastCrew(getActivity(), Constants.TAG_CREW).execute(URL).get();

            if (castList != null && !castList.isEmpty()) {
                castsSection.setVisibility(View.VISIBLE);
                setCrew(castList);
            } else {
                castsSection.setVisibility(View.GONE);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    // Add multiple casts in the casts container
    private void setCrew(List<Cast> casts) {

        LinearLayout castsContainer = (LinearLayout) getActivity().findViewById(R.id.crew_container);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = new ImageLoader(getActivity());

        int size = casts.size();
        for (int i = 0; i < size; i++) {
            Cast cast = casts.get(i);

            if (cast != null) {

                LinearLayout clickableColumn = (LinearLayout) inflater.inflate(R.layout.column_cast_crew, null);
                ImageView thumbnailImage = (ImageView) clickableColumn.findViewById(R.id.thumbnail_image);
                TextView titleView = (TextView) clickableColumn.findViewById(R.id.title_view);
                TextView subTitleView = (TextView) clickableColumn.findViewById(R.id.subtitle_view);

                if (cast.getProfilePath().equals("null")) {
                    thumbnailImage.setImageResource(R.drawable.no_image);
                } else {
                    imageLoader.DisplayImage("http://image.tmdb.org/t/p/w500" + cast.getProfilePath(), thumbnailImage);
                }

                titleView.setText(cast.getName());
                subTitleView.setText(cast.getJob());

                castsContainer.addView(clickableColumn);

                if (i != size - 1) {
                    castsContainer.addView(inflater.inflate(R.layout.horizontal_divider, null));
                }
            }
        }
    }

    private void showTrailers() {

        LinearLayout castsSection = (LinearLayout) getActivity().findViewById(R.id.trailer_section);

        URL = Constants.BASE_URL + Constants.API_VERSION + "/movie/" + movieID + "/videos" + Constants.API_KEY;

        try {
            List<Cast> castList = new GetCastCrew(getActivity(), Constants.TAG_RESULTS).execute(URL).get();

            if (castList != null && !castList.isEmpty()) {
                castsSection.setVisibility(View.VISIBLE);
                setTrailers(castList);
            } else {
                castsSection.setVisibility(View.GONE);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    // Add multiple casts in the casts container
    private void setTrailers(List<Cast> casts) {

        LinearLayout castsContainer = (LinearLayout) getActivity().findViewById(R.id.trailer_container);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int size = casts.size();
        for (int i = 0; i < size; i++) {
            Cast cast = casts.get(i);

            if (cast != null) {

                LinearLayout clickableColumn = (LinearLayout) inflater.inflate(R.layout.column_trailers, null);
                TextView titleView = (TextView) clickableColumn.findViewById(R.id.trailer_link);

                titleView.setText(cast.getName());
                final String trailer = "https://www.youtube.com/watch?v=" + cast.getKey();
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailer)));
                    }
                });

                castsContainer.addView(clickableColumn);

                if (i != size - 1) {
                    castsContainer.addView(inflater.inflate(R.layout.horizontal_divider, null));
                }
            }
        }
    }

    private void showPosters() {

        LinearLayout castsSection = (LinearLayout) getActivity().findViewById(R.id.posters_section);

        URL = Constants.BASE_URL + Constants.API_VERSION + "/movie/" + movieID + "/images" + Constants.API_KEY;

        try {
            List<Cast> castList = new GetCastCrew(getActivity(), Constants.TAG_POSTERS).execute(URL).get();

            if (castList != null && !castList.isEmpty()) {
                castsSection.setVisibility(View.VISIBLE);
                setPosters(castList);
            } else {
                castsSection.setVisibility(View.GONE);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    // Add multiple casts in the casts container
    private void setPosters(List<Cast> casts) {

        LinearLayout castsContainer = (LinearLayout) getActivity().findViewById(R.id.posters_container);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        imageLoader = new ImageLoader(getActivity());

        int size = casts.size();
        for (int i = 0; i < size; i++) {
            Cast cast = casts.get(i);

            if (cast != null) {

                LinearLayout clickableColumn = (LinearLayout) inflater.inflate(R.layout.column_cast_crew, null);
                ImageView thumbnailImage = (ImageView) clickableColumn.findViewById(R.id.thumbnail_image);
                TextView titleView = (TextView) clickableColumn.findViewById(R.id.title_view);
                TextView subTitleView = (TextView) clickableColumn.findViewById(R.id.subtitle_view);

                if (cast.getProfilePath().equals("null")) {
                    thumbnailImage.setImageResource(R.drawable.no_image);
                } else {
                    imageLoader.DisplayImage("http://image.tmdb.org/t/p/w500" + cast.getProfilePath(), thumbnailImage);
                }

                titleView.setVisibility(View.GONE);
                subTitleView.setVisibility(View.GONE);

                castsContainer.addView(clickableColumn);

                if (i != size - 1) {
                    castsContainer.addView(inflater.inflate(R.layout.horizontal_divider, null));
                }
            }
        }
    }
}