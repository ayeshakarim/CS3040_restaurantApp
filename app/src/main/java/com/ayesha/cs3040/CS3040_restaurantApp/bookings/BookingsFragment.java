package com.ayesha.cs3040.CS3040_restaurantApp.bookings;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayesha.cs3040.CS3040_restaurantApp.explore.ExploreItem;
import com.ayesha.cs3040.myapp1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingsFragment extends Fragment {

    private RecyclerView hrzRecyclerView;
    private RecyclerView vrtRecyclerView;
    private List<ExploreItem> rv_list;


    public BookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);

        hrzRecyclerView = (RecyclerView) view.findViewById(R.id.booking_rv);
        hrzRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        vrtRecyclerView = (RecyclerView) view.findViewById(R.id.visited_rv);
        vrtRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_list = new ArrayList<>();
        rv_list.add(new ExploreItem("Item 1", R.drawable.ic_image, "55 Example Lane, Birmingham, B90 23H", false, false));
        rv_list.add(new ExploreItem("Item 2", R.drawable.ic_image, "55 Example Lane, Birmingham, B90 23H", false, false));
        rv_list.add(new ExploreItem("Item 3", R.drawable.ic_image, "55 Example Lane, Birmingham, B90 23H", false, true));
        rv_list.add(new ExploreItem("Item 4", R.drawable.ic_image, "55 Example Lane, Birmingham, B90 23H", false, true));
        rv_list.add(new ExploreItem("Item 5", R.drawable.ic_image, "55 Example Lane, Birmingham, B90 23H", false, false));
        rv_list.add(new ExploreItem("Item 6", R.drawable.ic_image, "55 Example Lane, Birmingham, B90 23H", false, false));

        BookingsRecyclerAdapter mAdapter = new BookingsRecyclerAdapter(rv_list);

        hrzRecyclerView.setAdapter(mAdapter);
        hrzRecyclerView.setItemAnimator(new DefaultItemAnimator());

        VisitedRecyclerAdapter mAdapter1 = new VisitedRecyclerAdapter(rv_list);

        vrtRecyclerView.setAdapter(mAdapter1);
        vrtRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

}
