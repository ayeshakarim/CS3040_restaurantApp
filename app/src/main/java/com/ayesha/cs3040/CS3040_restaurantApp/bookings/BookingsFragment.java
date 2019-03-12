package com.ayesha.cs3040.CS3040_restaurantApp.bookings;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.LocationSort;
import com.ayesha.cs3040.CS3040_restaurantApp.SwipeToBookCallback;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.myapp1.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingsFragment extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private List<RestaurantItem> rv_list;
    private Button sortDate;
    private Button sortName;
    private LocationManager locationManager;
    private Location lastLocation;
    private CoordinatorLayout coordinatorLayout;
    private BookingsRecyclerAdapter mAdapter;


    RestaurantDAO restaurantDAO;

    public BookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);

        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(getContext());
        restaurantDAO  = db.getRestaurantDao();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.booking_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new BookingsRecyclerAdapter(getContext(), rv_list);
//        enableSwipeToVisitAndUndo();

        sortDate = (Button) view.findViewById(R.id.bookings_sort_date);
        sortName = (Button) view.findViewById(R.id.bookings_sort_distance);

        sortName.setOnClickListener(this);
        sortDate.setOnClickListener(this);

        getBookingsList();

        return view;
    }

    public void getBookingsList(){

        rv_list = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    rv_list = restaurantDAO.findBookingsVisited(false);
                    for (RestaurantItem r: restaurantDAO.findBookingsVisited(false)) {
                        Log.d("database id", r.id );
                        Log.d("database name", r.getName());
                        Log.d("database date booked", r.dateBooked + "");
                        Log.d("database visited", r.isVisited() + "");
                    }

                    BookingsRecyclerAdapter mAdapter = new BookingsRecyclerAdapter(getContext(), rv_list);

                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    return null;
                }
            }.execute();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bookings_sort_date:

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        rv_list = restaurantDAO.orderByDate(false);
                        return null;
                    }
                }.execute();
                Toast.makeText(getContext(), "ordering by date", Toast.LENGTH_SHORT).show();

                break;
            case R.id.bookings_sort_distance:

                        orderByLocation();

                Toast.makeText(getContext(), "ordering by distance from me", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.bookings_sort_a-z:

//                new AsyncTask<Void, Void, Void>() {
//                    @Override
//                    protected Void doInBackground(Void... voids) {
//                        rv_list = restaurantDAO.orderByName(false);
//                        return null;
//                    }
//                }.execute();
//                Toast.makeText(getContext(), "ordering alphabetically", Toast.LENGTH_SHORT).show();
//                break;
        }

        BookingsRecyclerAdapter mAdapter = new BookingsRecyclerAdapter(getContext(), rv_list);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void orderByLocation(){

        lastLocation = getLastBestLocation();
        if (lastLocation != null) {

            //sort the list, give the Comparator the current location
            Collections.sort(rv_list, new LocationSort(lastLocation.getLatitude(), lastLocation.getLongitude()));

            for (RestaurantItem p: rv_list){
                Log.i("Places after sorting", "Restaurant: " + p.name);
            }
            Toast.makeText(getActivity(), "Your Location: " + lastLocation, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getActivity(), "Location not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    private Location getLastBestLocation() {
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
            return locationGPS;
        } else {
            return locationNet;
        }
    }

    private void enableSwipeToVisitAndUndo() {
        SwipeToBookCallback swipeToDeleteCallback = new SwipeToBookCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final RestaurantItem item = mAdapter.getData().get(viewHolder.getAdapterPosition());


                mAdapter.setVisited(item);


                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.setBooked(item);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

}
