package com.ayesha.cs3040.CS3040_restaurantApp.map;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayesha.cs3040.CS3040_restaurantApp.RestaurantDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.myapp1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RestaurantDAO restaurantDAO;
    private List<RestaurantItem> places;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(getContext());
        restaurantDAO  = db.getRestaurantDao();

        getPlaces();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    public void getPlaces(){

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                places = restaurantDAO.findBookingsVisited(true);

                for (RestaurantItem r: restaurantDAO.findBookingsVisited(true)) {
                    Log.d("database id", r.id );
                    Log.d("database name", r.getName());
                    Log.d("database visited", r.isVisited() + "");
                }

                return null;
            }
        }.execute();
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


        for (RestaurantItem r : places) {

            LatLng latLng = new LatLng(r.latitude, r.longitude);
            String title = r.name;

            mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        }

        LatLng focus = new LatLng(places.get(0).latitude, places.get(0).longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(focus));

    }
}
