package com.espa.ceos.otto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DonationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final double price;
        final double donation;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        TextView text1 = (TextView)findViewById(R.id.text1);
        ImageButton yesbutton= (ImageButton)findViewById(R.id.yesbutton);
        Button nobutton= (Button)findViewById(R.id.nobutton);


        price =getIntent().getDoubleExtra("PAYMENT",0.0);
        donation =price - (int)price;
        text1.setText("Your total is "+ price +"."+"Would you like to donate" + donation + "to charity?");
        yesbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i1 = new Intent(DonationActivity.this, CharitiesActivity.class);
                i1.putExtra("donation",donation);
                startActivity(i1);
            }
        });

        nobutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(DonationActivity.this, "Ok!Have a good day!", Toast.LENGTH_LONG).show();
            }
        });


    }

}
