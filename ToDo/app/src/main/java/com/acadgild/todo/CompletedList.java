package com.acadgild.todo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class CompletedList extends Activity {

    private ListView listview;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incomplete_list);

        listview = (ListView) findViewById(R.id.listView);



    }
}
