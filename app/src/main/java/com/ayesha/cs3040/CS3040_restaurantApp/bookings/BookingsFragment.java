package com.ayesha.cs3040.CS3040_restaurantApp.bookings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;
import com.ayesha.cs3040.myapp1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<RestaurantItem> rv_list;
    private List<FoodItem> mealList;


    public BookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.booking_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_list = new ArrayList<>();
        rv_list.add(new RestaurantItem("0","Resturant 1", 2, 3,"55 Example Lane, Birmingham, B90 23H", "www.example.com"));
        rv_list.add(new RestaurantItem("1","Resturant 2", 3, 4, "66 Example Lane, Birmingham, B90 23H", "www.example.com"));
        rv_list.add(new RestaurantItem("2","Resturant 3", 1,3, "77 Example Lane, Birmingham, B90 23H", "www.example.com"));
        rv_list.add(new RestaurantItem("3","Resturant 4", 3,2, "88 Example Lane, Birmingham, B90 23H", "www.example.com"));
        rv_list.add(new RestaurantItem("4","Resturant 5", 4,4, "55 Example Lane, Birmingham, B90 23H", "www.example.com"));
        rv_list.add(new RestaurantItem("5","Resturant 6", 3,2, "55 Example Lane, Birmingham, B90 23H", "www.example.com"));

        BookingsRecyclerAdapter mAdapter = new BookingsRecyclerAdapter(rv_list);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return view;
    }

}
