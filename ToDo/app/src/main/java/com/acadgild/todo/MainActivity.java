package com.acadgild.todo;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    private ListView listview;
    private TaskAdapter adapter;
    TaskInfo[] toDoList = new TaskInfo[] {new TaskInfo("Title","Description","01/01/01",R.mipmap.ic_action_inc)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        getSupportActionBar();

        listview = (ListView) findViewById(R.id.listView);
        adapter = new TaskAdapter(this, R.layout.list_item, toDoList);

        if(listview != null) {
            listview.setAdapter(adapter);
        }

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final Dialog dialog = new Dialog(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_dialog, null);
                dialog.setContentView(layout);

                EditText title = (EditText) layout.findViewById(R.id.titleEditText);
                EditText description = (EditText) layout.findViewById(R.id.descriptionEditText);
                DatePicker datePicker = (DatePicker) layout.findViewById(R.id.datePicker);
                Button saveButton = (Button) layout.findViewById(R.id.buttonSave);
                Button cancelButton = (Button) layout.findViewById(R.id.buttonCancel);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                dialog.show();

                return true;
            }
        });



    }

    private void AddTask() {

    }

    private void CompletedList() {

        Intent i = new Intent(getApplicationContext(), CompletedList.class);
        startActivity(i);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.add:

                AddTask();

            return true;

            case R.id.complete:

                CompletedList();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
