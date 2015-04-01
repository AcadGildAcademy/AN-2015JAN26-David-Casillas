package com.acadgild.imdb.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Movie Database";
    private static final String TABLE_MOVIEDETAILS = "Movies";
    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_RELEASE_DATE = "release date";
    private static final String COLUMN_POSTER_PATH = "poster path";
    private static final String COLUMN_POPULARITY = "popularity";
    private static final String COLUMN_VOTE_AVERAGE = "vote average";
    private static final String COLUMN_VOTE_COUNT = "vote count";
    private static final String COLUMN_MOVIE_RAW_DETAILS = "movie raw details";
    private static final String COLUMN_IS_FAVORITE = "is favorite";
    private static final String COLUMN_IS_WATCHLIST = "is watchlist";
    private static final String DATATYPE_NUMERIC = " INTEGER";
    private static final String DATATYPE_VARCHAR = " TEXT";
    private static final String DATATYPE_REAL = " REAL";
    private static final String DATATYPE_BLOB = " BLOB";
    private static final String OPEN_BRACE = "(";
    private static final String COMMA = ",";

    private static final String CREATE_TABLE_MOVIEDETAILS = new StringBuilder()
            .append(CREATE_TABLE).append(TABLE_MOVIEDETAILS).append(OPEN_BRACE)
            .append(COLUMN_ID).append(DATATYPE_NUMERIC + COMMA)
            .append(COLUMN_TITLE).append(DATATYPE_NUMERIC + COMMA)
            .append(COLUMN_RELEASE_DATE).append(DATATYPE_VARCHAR + COMMA)
            .append(COLUMN_POSTER_PATH).append(DATATYPE_BLOB + COMMA)
            .append(COLUMN_POPULARITY).append(DATATYPE_REAL + COMMA)
            .append(COLUMN_VOTE_AVERAGE).append(DATATYPE_REAL + COMMA)
            .append(COLUMN_VOTE_COUNT).append(DATATYPE_NUMERIC + COMMA)
            .append(COLUMN_MOVIE_RAW_DETAILS).append(DATATYPE_VARCHAR + COMMA)
            .append(COLUMN_IS_FAVORITE).append(DATATYPE_VARCHAR + COMMA)
            .append(COLUMN_IS_WATCHLIST).append(DATATYPE_VARCHAR + COMMA)
            .append("UNIQUE(").append(COLUMN_ID).append(") ON CONFLICT REPLACE)").toString();

    public MovieDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIEDETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIEDETAILS);
        onCreate(db);

    }
}


