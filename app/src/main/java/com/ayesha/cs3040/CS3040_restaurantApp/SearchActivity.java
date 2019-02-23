package com.ayesha.cs3040.CS3040_restaurantApp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.myapp1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SearchActivity extends AppCompatActivity implements Runnable{

    private boolean success;
    private RecyclerView recyclerView;
    private LocationManager locationManager;
    private Location lastLocation;
    private final InfoFinder infoFinder = new InfoFinder();
    private List<RestaurantItem> restaurants = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        setContentView(R.layout.search_layout);

        restaurants = new ArrayList<RestaurantItem>();

    }

    @SuppressWarnings("unused")
    public void initialClick(View v) {

        Toast.makeText(this, "button has been clicked", Toast.LENGTH_SHORT).show();
        infoFinder.resetParameters();

        findRestaurants();
    }

    @Override
    public void run() {

        if (!success) {

            restaurants = infoFinder.getRestaurants();
            setView(R.id.restaurant_result_layout);
            recyclerView = (RecyclerView) findViewById(R.id.search_results_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            Toast.makeText(this, "getting list of restaurants", Toast.LENGTH_SHORT).show();
            while (restaurants == null || restaurants.size() != 20) {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                wait(100);
            }
            setRestaurantList();


        } else {
//            setView(R.id.not_connected_layout);
            Toast.makeText(this, "error connecting to db", Toast.LENGTH_SHORT).show();
        }
    }

    private void findRestaurants() {
        lastLocation = getLastBestLocation();
        if (lastLocation == null) {
            setView(R.id.permission_layout);
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION}, 0);
            return;
        }
        setView(R.id.loading_layout);
        RestaurantItem.setSearchActivity(this);
        infoFinder.setMainAndLocation(this, lastLocation);
        new Thread(infoFinder).start();
    }

    @Nullable
    private Location getLastBestLocation() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.d("permission"," " + ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) );
            Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
            return null;
        }
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (locationGPS != null) {
            GPSLocationTime = locationGPS.getTime();
        }

        long NetLocationTime = 0;

        if (locationNet != null) {
            NetLocationTime = locationNet.getTime();
        }

        if (NetLocationTime < GPSLocationTime) {
            return locationGPS;
        } else {
            return locationNet;
        }
    }

    public void setRestaurantList(){

        for (RestaurantItem restaurant: restaurants) {
            Log.d("restaurants", "  " +restaurant.getItem_name() +",  address: " + restaurant.getAddress() + ",  rating: " + restaurant.getRating() + ",  price level: " + restaurant.getPriceLevel() + ",  website: "  + restaurant.getWebsite());
        }

            SearchRecyclerAdapter mAdapter = new SearchRecyclerAdapter(this, restaurants);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private void setView(int layoutId) {
        RelativeLayout layouts = findViewById(R.id.layouts);
        for (int i=0; i < layouts.getChildCount(); i++)
            layouts.getChildAt(i).setVisibility(View.GONE);
        findViewById(layoutId).setVisibility(View.VISIBLE);
    }

}
