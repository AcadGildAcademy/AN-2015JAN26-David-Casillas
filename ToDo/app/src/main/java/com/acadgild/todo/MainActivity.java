package com.acadgild.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    public List<TaskInfo> allTasks = null;
    public ListView listview;
    public TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        getSupportActionBar();

        final TaskDataBase db = new TaskDataBase(this);

        listview = (ListView) findViewById(R.id.listView);

        allTasks = db.getAllTasks();
        adapter = new TaskAdapter(this, R.layout.list_item, allTasks);
        if (listview != null) {
            listview.setAdapter(adapter);
        }

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                TaskInfo task = db.getTask(position + 1);
                int status = db.updateTask(new TaskInfo(task.getDate(), task.getTitle(), task.getDescription(), R.mipmap.ic_action_comp));
                if (status > 0) {
                    Toast.makeText(getApplicationContext(), task.getTitle() + " has been completed!", Toast.LENGTH_SHORT).show();
                    List<TaskInfo> updatedTasks = db.getAllTasks();
                    adapter.updateList(updatedTasks);
                } else {
                    Toast.makeText(getApplicationContext(), "Error updating database!", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

    }

    private void AddTask() {

        TaskDialog dialog = new TaskDialog(MainActivity.this);
        dialog.show();

    }

    public void OnSave() {
        adapter.updateList(allTasks);
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
