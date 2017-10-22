package com.espa.ceos.otto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;


public class CheckoutActivity extends AppCompatActivity {
    public double sum;
    private DatabaseReference mDatabase;
    private ArrayList<String> productList;
    private ListView lv;
    private ArrayAdapter<String> adapter;
    private TextView mTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        productList=new ArrayList<String>();

        mDatabase = FirebaseDatabase.getInstance().getReference("items");
        mTxt=(TextView)findViewById(R.id.txt);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                sum=0;
                Iterable<DataSnapshot> questionsType=dataSnapshot.getChildren();
                for (DataSnapshot types : questionsType){
                    productList.add((String)types.getKey());
                    sum=sum+Double.parseDouble((String) types.getValue());
                }
                lv=(ListView) findViewById(R.id.listView2);
                adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,productList);
                lv.setAdapter(adapter);
                mTxt.setText(Double.toString(sum));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
