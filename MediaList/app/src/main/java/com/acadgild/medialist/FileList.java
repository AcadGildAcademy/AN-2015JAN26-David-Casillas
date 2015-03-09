package com.acadgild.medialist;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;


public class FileList extends ActionBarActivity {

    public ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        listview = (ListView) findViewById(R.id.listView2);

        String[] whereVal = { getIntent().getStringExtra("key") };

        String[] columns = { MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.MIME_TYPE };

        String where = MediaStore.Audio.Media.ALBUM + "=?";

        String orderBy = MediaStore.Audio.Media.TITLE;

        int[] displayTitle = { android.R.id.text1 };

        String[] displayFields = new String[] {MediaStore.Audio.Media.TITLE};

        Cursor cursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns, where, whereVal, orderBy);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, displayFields, displayTitle);

        listview.setAdapter(adapter);


    }

}
