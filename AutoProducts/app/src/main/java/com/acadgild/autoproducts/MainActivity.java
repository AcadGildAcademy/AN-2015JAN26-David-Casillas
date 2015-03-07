package com.acadgild.autoproducts;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    public AutoCompleteTextView acText;
    public ArrayAdapter<String> adapter;
    public ProductsDatabase db;
    public List<String> array;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar();

        acText = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        db = new ProductsDatabase(this);

        if (db.checkDB()) {
            insertData();
        }

        array = db.getAllProducts();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
        acText.setAdapter(adapter);

    }

    public void insertData() {

        db.addProduct("Color Monitor");
        db.addProduct("Computer");
        db.addProduct("Compact Disk");
        db.addProduct("Ram");
        db.addProduct("Processor");
        db.addProduct("Mouse");
        db.addProduct("Keyboard");
        db.addProduct("Headphones");
        db.addProduct("Laptop");
        db.addProduct("Desktop");
        db.addProduct("Program");
        db.addProduct("Graphics Card");
        db.addProduct("HP");
        db.addProduct("Dell");
        db.addProduct("Toshiba");
        db.addProduct("Logitech");

    }


}
