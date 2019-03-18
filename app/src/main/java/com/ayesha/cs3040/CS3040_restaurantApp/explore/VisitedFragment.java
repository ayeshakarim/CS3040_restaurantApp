package com.ayesha.cs3040.CS3040_restaurantApp.explore;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.db.ReviewDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.CS3040_restaurantApp.map.MapFragment;
import com.ayesha.cs3040.CS3040_restaurantApp.review.Review;
import com.ayesha.cs3040.myapp1.R;


public class VisitedFragment extends Fragment implements View.OnClickListener{

    private List<RestaurantItem> rv_list;
    private RecyclerView recyclerView;
    private FloatingActionButton mapBtn;
    private Button sortDate;
    private Button sortName;
    private VisitedRecyclerAdapter mAdapter;
    private ImageView refresh;

    private RestaurantDAO restaurantDAO;


    public VisitedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visited, container, false);

        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(getContext());
        restaurantDAO  = db.getRestaurantDao();

        recyclerView = (RecyclerView) view.findViewById(R.id.home_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mapBtn = (FloatingActionButton) view.findViewById(R.id.fab);
        sortDate = (Button) view.findViewById(R.id.home_sort_date);
        sortName = (Button) view.findViewById(R.id.home_sort_a_z);
        refresh = (ImageView) view.findViewById(R.id.visited_refresh);


        mapBtn.setOnClickListener(this);
        sortName.setOnClickListener(this);
        sortDate.setOnClickListener(this);
        refresh.setOnClickListener(this);

        setVisitedList();

        return view;
    }

    public void setVisitedList(){

        rv_list = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                rv_list = restaurantDAO.findBookingsVisited(true);

                for (RestaurantItem r: rv_list) {
                    Log.d("database id", r.id );
                    Log.d("database name", r.getName());
                    Log.d("database date booked", r.dateBooked + "");
                    Log.d("database visited", r.isVisited() + "");
                }

                mAdapter = new VisitedRecyclerAdapter(getContext(),getActivity(), rv_list);

                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                return null;
            }
        }.execute();
    }


    @Override
    public void onClick(View view) {
        Fragment fragment = null;


        switch (view.getId()) {
            case R.id.home_sort_date:

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        rv_list = restaurantDAO.orderByDate(true);
                        return null;
                    }
                }.execute();
                Toast.makeText(getContext(), "ordering by date", Toast.LENGTH_SHORT).show();

                break;
            case R.id.home_sort_a_z:

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        rv_list = restaurantDAO.orderByName(true);
                        return null;
                    }
                }.execute();
                Toast.makeText(getContext(), "ordering alphabetically", Toast.LENGTH_SHORT).show();
                break;
            case R.id.visited_refresh:
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        rv_list = restaurantDAO.findBookingsVisited(true);
                        return null;
                    }
                }.execute();
                Toast.makeText(getContext(), "refreshing the list", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab:
                fragment = new MapFragment();
                replaceFragment(fragment);
                break;
        }

        VisitedRecyclerAdapter mAdapter = new VisitedRecyclerAdapter(getContext(), getActivity(), rv_list);

        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
