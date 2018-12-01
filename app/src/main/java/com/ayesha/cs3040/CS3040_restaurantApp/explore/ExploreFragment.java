package com.ayesha.cs3040.CS3040_restaurantApp.explore;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.ayesha.cs3040.CS3040_restaurantApp.search.SearchFragment;
import com.ayesha.cs3040.myapp1.R;


public class ExploreFragment extends Fragment implements View.OnClickListener{

    private List<ExploreItem> rv_list;
    private RecyclerView recyclerView;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        SearchView bookings_search = (SearchView) view.findViewById(R.id.explore_search_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton writeReview = (FloatingActionButton) view.findViewById(R.id.fab);
        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Map View is Disabled", Toast.LENGTH_SHORT ).show();
            }
        });

        bookings_search.setOnClickListener(this);

        rv_list = new ArrayList<>();
        rv_list.add(new ExploreItem("Item 1", R.drawable.ic_image, "55 Example Lane, Birmingham, B90 23H", true, false));
        rv_list.add(new ExploreItem("Item 2", R.drawable.ic_image, "55 Example Lane, Birmingham, B90 23H", true, false));
        rv_list.add(new ExploreItem("Item 3", R.drawable.ic_image, "55 Example Lane, Birmingham, B90 23H", true, false));
        rv_list.add(new ExploreItem("Item 4", R.drawable.ic_image, "55 Example Lane, Birmingham, B90 23H", true, false));
        rv_list.add(new ExploreItem("Item 5", R.drawable.ic_image, "55 Example Lane, Birmingham, B90 23H", true, false));
        rv_list.add(new ExploreItem("Item 6", R.drawable.ic_image, "55 Example Lane, Birmingham, B90 23H", true, false));


        ExploreRecyclerAdapter mAdapter = new ExploreRecyclerAdapter(getContext(), rv_list);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.explore_search_bar:
                fragment = new SearchFragment();
                replaceFragment(fragment);
                break;
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
