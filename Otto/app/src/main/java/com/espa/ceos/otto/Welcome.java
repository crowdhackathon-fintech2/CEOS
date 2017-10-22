package com.espa.ceos.otto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.simplify.android.sdk.Simplify;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class Welcome extends AppCompatActivity {
    Simplify simplify;
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
    private static final String IBAN="GR4501101030000010348012377";
    private String results;
    public static String temp=null;
    private TextView mText;
    private static String currentAmmount="Waiting for amount to load";
    private static DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final TextView mText=(TextView)findViewById(R.id.Circle);
        OkHttpClient client = new OkHttpClient();

        // read account

        new AsyncTask<Void, Void, Void>() {
            private final ProgressDialog dialog = new ProgressDialog(Welcome.this);
            @Override
            protected Void doInBackground( Void... voids ) {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://apis.nbg.gr/public/nbgapis/obp/v3.0.1/banks/DB173089-A8FE-43F1-8947-F1B2A8699829/accounts/33491949-04d8-440b-8b98-db30afa14585/owner/account")
                        .get()
                        .addHeader("accept", "text/json")
                        .addHeader("sandbox_id", "darasgiannis")
                        .addHeader("application_id", "REPLACE_THIS_VALUE")
                        .addHeader("user_id", "c060ae72-994c-40b5-82eb-0403d50f8600")
                        .addHeader("username", "User1")
                        .addHeader("provider_id", "NBG.gr")
                        .addHeader("provider", "NBG")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    temp=response.body().string();
                    JSONObject obj = new JSONObject(temp);
                    JSONObject obj2=obj.getJSONObject("balance");
                    currentAmmount=obj2.getString("amount");



                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }
            protected void onPostExecute(Void result){
                mText.setText(currentAmmount);

            }

        }.execute();




    }




}
