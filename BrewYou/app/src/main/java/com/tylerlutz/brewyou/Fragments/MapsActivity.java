package com.tylerlutz.brewyou.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tylerlutz.brewyou.Models.Restaurant;
import com.tylerlutz.brewyou.R;

import org.json.JSONException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Intent intent;
    private Restaurant restaurant = new Restaurant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        intent = getIntent();
        restaurant.setRestaurantId(intent.getStringExtra("objectid"));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurant");
        query.getInBackground(restaurant.getRestaurantId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseRestaurant, ParseException e) {
                if (e == null) {
                    try {
                        restaurant.findRestaurantCoordinates();
                    } catch (JSONException x) {
                        x.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
        // Add a marker in Sydney and move the camera
        // LatLng here = new LatLng(restaurant.getRestaurantLatitude(), restaurant.getRestaurantLongitude());
        mMap.addMarker(new MarkerOptions().position(restaurant.getRestaurantLatLng()).title(restaurant.getRestaurantName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(restaurant.getRestaurantLatLng()));
    }

}