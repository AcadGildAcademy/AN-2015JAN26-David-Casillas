package com.acadgild.shakesensor;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    public CheckBox cb;
    public EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cb = (CheckBox) findViewById(R.id.checkBox);
        et = (EditText) findViewById(R.id.numberText);
        cb.setChecked(false);


        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Intent i = new Intent(MainActivity.this, Shake.class);
                    i.putExtra("number", et.getText().toString());
                    startService(i);
                } else {
                    Intent i = new Intent(MainActivity.this, Shake.class);
                    stopService(i);
                }

            }
        });

    }


}
