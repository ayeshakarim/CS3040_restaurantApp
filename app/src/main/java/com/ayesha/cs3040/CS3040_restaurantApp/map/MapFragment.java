package com.ayesha.cs3040.CS3040_restaurantApp.map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.LocationSort;
import com.ayesha.cs3040.CS3040_restaurantApp.MainActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.bookings.BookingsFragment;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.item.ItemActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.myapp1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RestaurantDAO restaurantDAO;
    private List<RestaurantItem> places;
    private boolean doubleClick = false;
    private Looper looper;
    private LatLng lastLocation;
    private List<Marker> markers;
    LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(getContext());
        restaurantDAO  = db.getRestaurantDao();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        lastLocation = getLastBestLocation();

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
        markers = new ArrayList<>();

        for (RestaurantItem r : places) {


            Date date = r.getDateBooked();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");

            String formattedDate = dateFormat.format(date);

            LatLng latLng = new LatLng(r.latitude, r.longitude);
            String title = r.name + ":   " + formattedDate;
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(title).snippet(r.id));

            mMap.addMarker(new MarkerOptions()
                    .position(lastLocation)
                    .title("My Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            markers.add(marker);
        }

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

            @Override
            public void onCameraMove() {
                Toast.makeText(getContext(), "Camera moving.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                final String id = marker.getSnippet();

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {

                        looper.prepare();
                        RestaurantItem res = restaurantDAO.getById(id);

                        if (doubleClick) {
                            openItemActivity(res);

                        } else {
                            doubleClick = true;

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    doubleClick = false;
                                }
                            }, 1000);
                        }
                        return null;
                    }
                }.execute();
                return doubleClick;
            }
        });

        getMarkerLocations();

    }

    private void getMarkerLocations() {

        if (lastLocation != null) {

            int i = 0;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, 15));

            VisibleRegion currentScreen = mMap.getProjection().getVisibleRegion();
            for(Marker marker : markers) {
                if(currentScreen.latLngBounds.contains(marker.getPosition())) {
                    // marker inside visible region
                } else {
                    // marker outside visible region
                    i ++;
                }
            }
            Toast.makeText(getContext(), i + " markers are located off screen", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity(), "Location not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Nullable
    private LatLng getLastBestLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.d("permission"," " + ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) );
            Toast.makeText(getActivity(), "permission not granted", Toast.LENGTH_SHORT).show();
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
            return new LatLng(locationGPS.getLatitude(), locationGPS.getLongitude());
        } else {
            return new LatLng(locationNet.getLatitude(), locationNet.getLongitude());
        }
    }


    public void openItemActivity(final RestaurantItem r) {
        Toast.makeText(getContext(), r.name, Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurant", r );
        Intent intent = new Intent(getContext(), ItemActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }
}
