package com.acadgild.autoproducts;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductsDatabase extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Products DB";
    private static final String DB_TABLE_NAME = "Products";
    private static final String DB_COLUMN_1_NAME = "Product";

    public ProductsDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + DB_TABLE_NAME + "(" + DB_COLUMN_1_NAME + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    public void addProduct(String product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN_1_NAME, product);
        db.insert(DB_TABLE_NAME, null, values);
        db.close();
    }

    public List<String> getAllProducts() {
        List<String> allProducts = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DB_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                allProducts.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return allProducts;
    }

    boolean checkDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE_NAME, null, DB_COLUMN_1_NAME, null, null, null, null, null);
        if (cursor.getCount() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
        onCreate(db);
    }
}
