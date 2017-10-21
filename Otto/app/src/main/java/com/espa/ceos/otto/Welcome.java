package com.espa.ceos.otto;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.simplify.android.sdk.Simplify;


import org.json.JSONException;
import org.json.JSONObject;

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


//Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();

//        Intent intent = new Intent(getApplicationContext(), Nfc.class);
//        startActivity(intent);

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        OkHttpClient client = new OkHttpClient();

        // read account

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground( Void... voids ) {
                OkHttpClient client2 = new OkHttpClient();

                Request request2 = new Request.Builder()
                        .url("https://apis.nbg.gr/public/nbgapis/obp/v3.0.1/banks/DB173089-A8FE-43F1-8947-F1B2A8699829/accounts")
                        .get()
                        .addHeader("sandbox_id", "darasgiannis")
                        .addHeader("application_id", "REPLACE_THIS_VALUE")
                        .addHeader("user_id", senderUserId)
                        .addHeader("username", senderUsername)
                        .addHeader("provider_id", providerId)
                        .addHeader("provider", provider)
                        .build();

                try {
                    Response response2 = client2.newCall(request2).execute();
                    temp=response2.body().string();
                   Log.d("AKJDJDJDJDJDJDJDJDJ",temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();


        // execute payment
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground( Void... voids ) {
                OkHttpClient client3 = new OkHttpClient();
//                MediaType mediaType = MediaType.parse("application/json");
//                RequestBody body = RequestBody.create(mediajsonType, "{\"to\":{\"iban\":\"GR4501101030000010348012377\"},\"charge_policy\":\"OUR\",\"value\":{\"currency\":\"EUR\",\"amount\":1},\"description\":\"test\"}");
//                Request request3 = new Request.Builder()
//                        .url("https://apis.nbg.gr/public/nbgapis/obp/v3.0.1/banks/DB173089-A8FE-43F1-8947-F1B2A8699829/accounts/33491949-04d8-440b-8b98-db30afa14585/owner/transaction-request-types/sepa/transaction-requests")
//                        .post(body)
//                        .addHeader("Content-Type", "application/json")
//                        .addHeader("Accept", "application/json")
//                        .addHeader("sandbox_id", "darasgiannis")
//                        .addHeader("application_id", "testg")
//                        .addHeader("user_id", "c060ae72-994c-40b5-82eb-0403d50f8600")
//                        .addHeader("username", "User1")
//                        .addHeader("provider_id", "NBG.gr")
//                        .addHeader("provider", "NBG")
//                        .build();
//                try {
//                    Response response3 = client3.newCall(request3).execute();
//
//
//                    Log.d("Answer",response3.body().string());
//                } catch (Exception e) {
//                    Log.d("ESP","Here");
////                    e.printStackTrace();
//                }

                try {
                    HttpPost httpPost = new HttpPost("https://apis.nbg.gr/public/nbgapis/obp/v3.0.1/banks/DB173089-A8FE-43F1-8947-F1B2A8699829/accounts/33491949-04d8-440b-8b98-db30afa14585/owner/transaction-request-types/sepa/transaction-requests");
                    httpPost.setEntity(new StringEntity("{\"to\":{\"iban\":\"GR4501101030000010348012377\"},\"charge_policy\":\"OUR\",\"value\":{\"currency\":\"EUR\",\"amount\":1},\"description\":\"tes22t\"}"));
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
        }.execute();







    }





}
