package com.espa.ceos.otto;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;


public class NoDonationActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private static final String donationIban="GR4501101030000010348012377";
    private static final String transactionIban="GR0901109720000097226400154";
    private static final String sandboxId="darasgiannis";
    private static final String senderUserId="c060ae72-994c-40b5-82eb-0403d50f8600";
    private static final String receiverUserId="46529c9b-9eec-440a-b951-81c7cffbcba2";
    private static final String senderUsername="User1";
    private static final String receiverUsername="User2";
    private static final String senderAccountId="33491949-04d8-440b-8b98-db30afa14585";
    private static final String receiverAccountId="c870420d-46d1-4557-bf92-c0b392bf7510";
    private static final String provider="NBG";
    private static final String providerId="NBG.gr";
    private static final String bankId="DB173089-A8FE-43F1-8947-F1B2A8699829";
    private String results;
    public double amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        amount=intent.getDoubleExtra("key",0);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground( Void... voids ) {


                try {
                    HttpPost httpPost = new HttpPost("https://apis.nbg.gr/public/nbgapis/obp/v3.0.1/banks/DB173089-A8FE-43F1-8947-F1B2A8699829/accounts/33491949-04d8-440b-8b98-db30afa14585/owner/transaction-request-types/sepa/transaction-requests");
                    httpPost.setEntity(new StringEntity("{\"to\":{\"iban\":\"GR4501101030000010348012377\"},\"charge_policy\":\"OUR\",\"value\":{\"currency\":\"EUR\",\"amount\":"+amount+"},\"description\":\"Payment done\"}"));
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-type", "application/json");
                    httpPost.setHeader("sandbox_id", "darasgiannis");
                    httpPost.setHeader("application_id", "testg");
                    httpPost.setHeader("user_id", "c060ae72-994c-40b5-82eb-0403d50f8600");
                    httpPost.setHeader("username", "User1");
                    httpPost.setHeader("provider_id", "NBG.gr");
                    httpPost.setHeader("provider", "NBG");

                    HttpResponse resp = new DefaultHttpClient().execute(httpPost);
                    Log.d("tag",resp.toString());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;

            }
            protected void onPostExecute(Void result){
                mDatabase = FirebaseDatabase.getInstance().getReference("unlock/value");
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mDatabase.setValue(1);
                        Intent intent=new Intent(NoDonationActivity.this,Welcome.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }.execute();



    }

}
