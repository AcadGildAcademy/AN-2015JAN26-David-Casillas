package com.acadgild.todo;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.List;

public class TaskDialog extends Dialog {

    //public interface SaveListener {
        //public void onSave();
    //}

    //private SaveListener listener;

    public TaskDialog(final Context context) {
        super(context);

        final TaskDataBase db = new TaskDataBase(context);

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_dialog, null);
        setContentView(layout);
        final EditText title = (EditText) layout.findViewById(R.id.titleEditText);
        final EditText description = (EditText) layout.findViewById(R.id.descriptionEditText);
        final DatePicker datePicker = (DatePicker) layout.findViewById(R.id.datePicker);
        Button saveButton = (Button) layout.findViewById(R.id.buttonSave);
        Button cancelButton = (Button) layout.findViewById(R.id.buttonCancel);

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
                    String date = String.valueOf(day) + "/" + month + "/" + year;
                    db.addTask(new TaskInfo(date, mTitle, mDescription, R.mipmap.ic_action_inc));
                    
                    dismiss();
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
