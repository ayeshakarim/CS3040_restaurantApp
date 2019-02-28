package com.ayesha.cs3040.CS3040_restaurantApp.review;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.FoodItemDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.ReviewDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.myapp1.R;

import java.util.ArrayList;
import java.util.List;


public class WriteReviewFragment extends Fragment{

    private TextView restaurant_name;
    private TextView restaurant_address;
    private EditText comment;
    private RatingBar rating;

    private List<FoodItem> rv_list;
    private RecyclerView recyclerView;

    private Review review;
    private RestaurantItem restaurant;
    private boolean isCreated;

    private ReviewDAO reviewDAO;
    private FoodItemDAO foodDAO;

//    final Fragment addFoodFragment = new AddFoodFragment();
    public WriteReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_review, container, false);
        getIncomingIntent();
        isCreated = checkIsCreated();

        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(getContext());
        reviewDAO  = db.getReviewDao();
        foodDAO = db.getFoodItemDao();

        recyclerView = (RecyclerView) view.findViewById(R.id.food_item_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        view.findViewById(R.id.review_back_btn).setOnClickListener(mListener);
        view.findViewById(R.id.review_submit).setOnClickListener(mListener);
        view.findViewById(R.id.add_food_item).setOnClickListener(mListener);

        restaurant_name = view.findViewById(R.id.review_restaurant_name);
        restaurant_address = view.findViewById(R.id.review_restaurant_address);

        comment = (EditText) view.findViewById(R.id.review_comment);
        rating = (RatingBar) view.findViewById(R.id.review_rating);

        if(isCreated) {
            setFoodList();
        }


        return view;
    }


    private final View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.review_back_btn:
                    Toast.makeText(getContext(), "Back Button Clicked", Toast.LENGTH_SHORT).show();
                    getActivity().finish();

                    break;

                case R.id.review_submit:
                    Toast.makeText(getContext(), "Submit Button Clicked", Toast.LENGTH_SHORT).show();

                    String commentToString = comment.getText().toString();
                    String ratingToString = Float.toString(rating.getRating());

                    review.setComment(commentToString);
                    review.setRating(rating.getNumStars());
                    if (isCreated){

                        updateReview();
                    }
                    else {
                        createReview();
                    }

                    Log.d( "REVIEW", commentToString + " RATING : " + ratingToString);
                    getActivity().finish();

                    break;

                case R.id.add_food_item:
                    Toast.makeText(getContext(), "Open Add Food Fragment", Toast.LENGTH_SHORT).show();

//                    if (isCreated){ updateReview(); }
//                    else { createReview(); }

//                    replaceFragment(addFoodFragment);

                    break;
            }
        }
    };


    public void setFoodList(){

        foodDAO.getFoodForReview(review.getId());

        rv_list = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                rv_list = foodDAO.getFoodForReview(review.getId());

                for (FoodItem r: foodDAO.getFoodForReview(review.getId())) {
                    Log.d("database id", r.getFoodId() + "" );
                    Log.d("database name", r.getName());
                    Log.d("database visited", r.getPrice() + "");
                }

                ReviewRecyclerAdapter mAdapter = new ReviewRecyclerAdapter(getContext(), rv_list);

                recyclerView.setAdapter(mAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                return null;
            }
        }.execute();
    }

    public void createReview(){

        reviewDAO.insert(review);


        for (Review r: reviewDAO.getAllReviews()) {
            Log.d("database id", r.getId() + "");
            Log.d("database comment", r.getComment());
            Log.d("database rating", r.getRating() + "");
            Log.d("database restaurant", r.getRestaurantId());
        }
    }

    public void updateReview() {

    }

    private boolean checkIsCreated() {

        Bundle bundle = getArguments();

        if (bundle!= null) {

            review = (Review) bundle.getSerializable("review");

            comment.setText(review.getComment());
            rating.setRating(review.getRating());

            return true;
        }
        else {
            Log.e("null", "is new");
            return false;
        }
    }

    private void getIncomingIntent(){

        if(getActivity().getIntent().hasExtra("restaurant")){

            Bundle bundle = getActivity().getIntent().getExtras();
            if(bundle!=null) {
                restaurant = (RestaurantItem) bundle.getSerializable("restaurant");
//                String item_name = restaurant.getName();
//                String address = restaurant.getItem_address();
//                review.setRestaurantId(restaurant.getId());

                setParams(restaurant);

                Log.d("bundle params", restaurant.name);

            }
            else {
                Log.e("null", "null");
            }

        }
    }


    private void setParams( RestaurantItem restaurant){

//        String item_name = restaurant.getName();
//        String address = restaurant.getItem_address();

        review.setRestaurantId(restaurant.getId());
        restaurant_name.setText(restaurant.getName());
        restaurant_address.setText(restaurant.getItem_address());
    }


    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putSerializable("review", review);
        fragment.setArguments(bundle);

        transaction.replace(R.id.profile_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
