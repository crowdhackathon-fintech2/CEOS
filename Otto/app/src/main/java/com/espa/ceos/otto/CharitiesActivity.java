package com.espa.ceos.otto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CharitiesActivity extends AppCompatActivity {
    private double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charities);
        ListView lv = (ListView) findViewById(R.id.listView1);
        Intent intent=getIntent();
        amount=intent.getDoubleExtra("donation",0);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                Intent intent = new Intent(CharitiesActivity.this,ChoicesActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("position", position);
                intent.putExtra("amount",amount);
                startActivity(intent);



            }
        });


    }

}
