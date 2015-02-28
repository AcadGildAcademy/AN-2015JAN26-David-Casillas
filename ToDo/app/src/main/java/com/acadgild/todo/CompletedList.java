package com.acadgild.todo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;


public class CompletedList extends ActionBarActivity {

    private ListView listview;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        getSupportActionBar();

        listview = (ListView) findViewById(R.id.listView);







        listview.setAdapter(adapter);



    }
}
