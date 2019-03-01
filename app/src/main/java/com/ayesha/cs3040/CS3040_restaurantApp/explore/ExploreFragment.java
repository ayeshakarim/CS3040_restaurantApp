package com.ayesha.cs3040.CS3040_restaurantApp.explore;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.ayesha.cs3040.CS3040_restaurantApp.RestaurantDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.CS3040_restaurantApp.map.MapFragment;
import com.ayesha.cs3040.myapp1.R;


public class ExploreFragment extends Fragment implements View.OnClickListener{

    private List<RestaurantItem> rv_list;
    private RecyclerView recyclerView;
    private FloatingActionButton mapBtn;

    private RestaurantDAO restaurantDAO;


    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(getContext());
        restaurantDAO  = db.getRestaurantDao();

        SearchView bookings_search = (SearchView) view.findViewById(R.id.explore_search_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mapBtn = (FloatingActionButton) view.findViewById(R.id.fab);

        mapBtn.setOnClickListener(this);
        bookings_search.setOnClickListener(this);

        setVisitedList();

//        rv_list = new ArrayList<>();
//        rv_list.add(new RestaurantItem("0","Resturant 1", 2, 3,"55 Example Lane, Birmingham, B90 23H", "www.example.com"));
//        rv_list.add(new RestaurantItem("1","Resturant 2", 3, 4, "66 Example Lane, Birmingham, B90 23H", "www.example.com"));
//        rv_list.add(new RestaurantItem("2","Resturant 3", 1,3, "77 Example Lane, Birmingham, B90 23H", "www.example.com"));
//        rv_list.add(new RestaurantItem("3","Resturant 4", 3,2, "88 Example Lane, Birmingham, B90 23H", "www.example.com"));
//        rv_list.add(new RestaurantItem("4","Resturant 5", 4,4, "55 Example Lane, Birmingham, B90 23H", "www.example.com"));
//        rv_list.add(new RestaurantItem("5","Resturant 6", 3,2, "55 Example Lane, Birmingham, B90 23H", "www.example.com"));
//
//
//        ExploreRecyclerAdapter mAdapter = new ExploreRecyclerAdapter(getContext(), rv_list);
//
//        recyclerView.setAdapter(mAdapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    public void setVisitedList(){

        rv_list = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                rv_list = restaurantDAO.findBookingsVisited(true);

                for (RestaurantItem r: restaurantDAO.findBookingsVisited(true)) {
                    Log.d("database id", r.id );
                    Log.d("database name", r.getName());
                    Log.d("database visited", r.isVisited() + "");
                }

                ExploreRecyclerAdapter mAdapter = new ExploreRecyclerAdapter(getContext(), rv_list);

                recyclerView.setAdapter(mAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                return null;
            }
        }.execute();
    }


    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.explore_search_bar:
//                fragment = new SearchFragment();
//                replaceFragment(fragment);
                break;
            case R.id.fab:
                fragment = new MapFragment();
                replaceFragment(fragment);
        }

    }


    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
