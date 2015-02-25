package com.acadgild.dialogsassignment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class CustomDialog extends DialogFragment {

    EditText name, number, dob;

    public interface NewContactListener {
        void onFinishDialog(String text);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.custom_dialog, null);
        name = (EditText) view.findViewById(R.id.nameET);
        number = (EditText) view.findViewById(R.id.numberET);
        dob = (EditText) view.findViewById(R.id.dobET);

        builder.setView(view);

        builder.setIcon(R.mipmap.ic_launcher);

        builder.setTitle(getText(R.string.title).toString());

        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String text = name.getText().toString() + " " + number.getText().toString() + " " + dob.getText().toString();
                NewContactListener listener = (NewContactListener) getActivity();
                listener.onFinishDialog(text);
                dismiss();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        return builder.create();
    }

}
