package com.acadgild.medialist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class AlbumList extends ActionBarActivity {

    public ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listView);

        int[] displayAlbum = { android.R.id.text1 };

        String[] displayFields = new String[] { MediaStore.Audio.Albums.ALBUM };

        String[] columns = new String[] { MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM };

        Cursor cursor = managedQuery(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, columns, null, null, null);

        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, displayFields, displayAlbum);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = adapter.getCursor();
                cursor.moveToPosition(position);

                String albumTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));

                Intent intent = new Intent(AlbumList.this, FileList.class);
                intent.putExtra("key", albumTitle);
                startActivity(intent);

            }
        });


    }
}
