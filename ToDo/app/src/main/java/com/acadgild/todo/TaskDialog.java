package com.acadgild.todo;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


public class TaskDialog extends Dialog {

    Context context;
    public EditText title;
    public EditText description;
    public DatePicker datePicker;
    public Button saveButton;
    public Button cancelButton;
    //private static final String dateFormat = "MM/dd/yyyy";

    public TaskDialog(final Context context) {
        super(context);
        this.context = context;

        final TaskDataBase db = new TaskDataBase(context);

        setContentView(R.layout.custom_dialog);
        title = (EditText) findViewById(R.id.titleEditText);
        description = (EditText) findViewById(R.id.descriptionEditText);
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
                    int month = datePicker.getMonth() + 1;
                    int year = datePicker.getYear();
                    String date = month + "/" + String.valueOf(day) + "/" + year;
                    db.addTask(new TaskInfo(date, mTitle, mDescription, R.mipmap.ic_action_inc, 0));

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
