package com.espa.ceos.otto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ChoicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        // Here we turn your string.xml in an array
        String[] myKeys = getResources().getStringArray(R.array.sections);

        TextView myTextView = (TextView) findViewById(R.id.my_textview);
        myTextView.setText("You chose to donate to " +myKeys[position]);

    }

}
