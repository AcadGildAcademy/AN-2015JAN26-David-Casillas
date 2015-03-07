package com.acadgild.todo;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;


public class TaskDialog extends Dialog {

    Context context;
    public EditText title;
    public EditText description;
    public DatePicker datePicker;
    public Button saveButton;
    public Button cancelButton;
    public int id;
    public int checkButton;
    private static final String yearMonthDay = "yyyyMMdd";

    public TaskDialog(final Context context) {
        super(context);
        this.context = context;

        final TaskDataBase db = new TaskDataBase(context);

        setContentView(R.layout.custom_dialog);
        title = (EditText) findViewById(R.id.titleEditText);
        title.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        description = (EditText) findViewById(R.id.descriptionEditText);
        description.setImeOptions(EditorInfo.IME_ACTION_DONE);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        saveButton = (Button) findViewById(R.id.buttonSave);
        cancelButton = (Button) findViewById(R.id.buttonCancel);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title.getText().toString().equals("")) {
                    title.setError("Must have a title");

                } else if (description.getText().toString().equals("")) {
                    description.setError("Must have a description");

                } else {
                    String mTitle = title.getText().toString();
                    String mDescription = description.getText().toString();
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    int year = datePicker.getYear();
                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.set(year, month, day);
                    String date = String.valueOf(android.text.format.DateFormat.format(yearMonthDay, calendar.getTime()));

                    boolean check = db.checkTask(mTitle);

                    if (checkButton == 0) { //editing task
                        TaskInfo task = db.getTask(id);
                        String oldTitle = task.getTitle();
                        if (oldTitle.equals(mTitle)) {
                            db.updateTask(new TaskInfo(id, date, mTitle, mDescription, R.mipmap.ic_action_inc, 0));
                        } else if (!oldTitle.equals(mTitle) && check) {
                            Toast.makeText(context, mTitle + " is being used, try a different title!", Toast.LENGTH_SHORT).show();
                        } else {
                            db.updateTask(new TaskInfo(id, date, mTitle, mDescription, R.mipmap.ic_action_inc, 0));
                        }
                    }

                    if (checkButton == 1) { //adding new task
                        if (check) {
                            Toast.makeText(context, mTitle + " is being used, try a different title!", Toast.LENGTH_SHORT).show();
                        } else {
                            db.addTask(new TaskInfo(date, mTitle, mDescription, R.mipmap.ic_action_inc, 0));

                        }
                    }

                    dismiss();
                    ((Activity) context).finish();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        show();
    }

}
