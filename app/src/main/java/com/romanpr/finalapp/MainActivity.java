package com.romanpr.finalapp;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    LocationManager locationManager;
    Location mCurrentLocation;
    LocationListener locationListener;
    double findLatitude, findLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                mCurrentLocation = location;
                logLocation();
                processUser("Roman", mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        locationManager.requestLocationUpdates(locationManager.getBestProvider(new Criteria(), false),
                10000, 5, locationListener);

    }

    public void updateDistance(View view) {
        myRef.child("Roman").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                findLatitude = Double.valueOf(dataSnapshot.child("latitude").getValue().toString());
                findLongitude = Double.valueOf(dataSnapshot.child("longitude").getValue().toString());
                Log.i("Location", "Find: " + findLatitude + ", " + findLongitude);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void processUser(String name, double latitude, double longitude) {
        DatabaseReference user = myRef.child(name);
        user.child("latitude").setValue(latitude);
        user.child("longitude").setValue(longitude);
    }

    public void logLocation() {
        Log.i("Location", String.valueOf(mCurrentLocation.getLatitude())
                + ", " + String.valueOf(mCurrentLocation.getLongitude()));
    }

}
