package com.ayesha.cs3040.CS3040_restaurantApp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.db.ReviewDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.item.ItemReviewRecyclerAdapter;
import com.ayesha.cs3040.CS3040_restaurantApp.review.Review;
import com.ayesha.cs3040.myapp1.R;

import java.util.ArrayList;
import java.util.List;


public class ReviewsFragment extends Fragment implements View.OnClickListener{

private List<Review> rv_list;
private ReviewDAO reviewDAO;
private RecyclerView recyclerView;


    public ReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(getContext());
        reviewDAO  = db.getReviewDao();

        recyclerView = (RecyclerView) view.findViewById(R.id.review_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setReviewsList();


        return view;
    }

    public void setReviewsList() {
        rv_list = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                rv_list = reviewDAO.getAllReviews();


                for (Review r: rv_list) {
                    Log.d("database id", r.getId() +"");
                    Log.d("database comment", r.getComment());
                    Log.d("database rating", r.getRating() + "");
                }

                ReviewsRecyclerAdapter mAdapter = new ReviewsRecyclerAdapter(getContext(),getActivity(), rv_list);

                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                return null;
            }
        }.execute();
    }

    @Override
    public void onClick(View v) {

    }
}
