package com.romanpr.finalapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        processUser("Roman Kalkavan", 33, 57);

    }

    public void processUser(String name, double latitude, double longitude) {
        DatabaseReference user = myRef.child(name);
        user.child("latitude").setValue(latitude);
        user.child("longitude").setValue(longitude);
    }
}
