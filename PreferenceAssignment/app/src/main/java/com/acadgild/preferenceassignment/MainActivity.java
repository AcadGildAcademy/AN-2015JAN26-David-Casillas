package com.acadgild.preferenceassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    public static final String PASSWORD = "pref_password";
    public static final String REMINDERS = "pref_numberOfReminders";
    public static final String SAVE = "pref_save";
    public static final int REQUEST_CODE = 1;

    Button button;
    TextView firstTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        firstTV = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            loadMySavedPreferences();
        }
    }

    public void loadMySavedPreferences() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String text = "Password: " + sp.getString(PASSWORD, "NOPASSWORD");
        text = text + "\nReminders: " + sp.getString(REMINDERS, "NOREMINDERS");
        text = text + "\nSave info? " + sp.getBoolean(SAVE, false);

        firstTV.setText(text);

    }


}
