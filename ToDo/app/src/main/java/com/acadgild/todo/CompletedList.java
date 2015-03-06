package com.acadgild.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class CompletedList extends ActionBarActivity {

    public List<TaskInfo> allCompletedTasks = null;
    public ListView listview;
    public TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        getSupportActionBar();
        final TaskDataBase db = new TaskDataBase(this);

        listview = (ListView) findViewById(R.id.listView);

        allCompletedTasks = db.getAllCompletedTasks();
        adapter = new TaskAdapter(this, R.layout.list_item, allCompletedTasks);
        if (listview != null) {
            listview.setAdapter(adapter);
        }

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                TaskInfo task = allCompletedTasks.get(position);
                db.deleteTask(task);
                Toast.makeText(getApplicationContext(), task.getTitle() + " has been deleted!", Toast.LENGTH_SHORT).show();
                allCompletedTasks = db.getAllCompletedTasks();
                adapter.updateList(allCompletedTasks);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
