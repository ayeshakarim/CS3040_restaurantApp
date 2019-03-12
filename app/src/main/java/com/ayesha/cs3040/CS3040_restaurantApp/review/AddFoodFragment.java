package com.ayesha.cs3040.CS3040_restaurantApp.review;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.db.FoodItemDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.myapp1.R;


public class AddFoodFragment extends Fragment {

    public EditText name;
    public EditText price;
    public FoodItem foodItem;
    public Review review;
    public RestaurantItem restaurant;

    private FoodItemDAO foodDAO;

    public AddFoodFragment() {
        // Required empty public constructor
    }

    public static AddFoodFragment newInstance(Review r, RestaurantItem restaurant, int id) {
        AddFoodFragment fragment = new AddFoodFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("review", r);
        Log.d("review", "Setting the review " + r);
        bundle.putSerializable("restaurant", restaurant);
        bundle.putInt("review id", id);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);

        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(getContext());
        foodDAO  = db.getFoodItemDao();

        review = (Review) getArguments().getSerializable("review");
        Log.d("review", "Getting the review " + review);
        restaurant = (RestaurantItem) getArguments().getSerializable("restaurant");

        foodItem = new FoodItem(review.getId());



        view.findViewById(R.id.food_item_submit).setOnClickListener(mListener1);
        view.findViewById(R.id.add_food_back_btn).setOnClickListener(mListener1);

        name = (EditText) view.findViewById(R.id.food_item_name);
        price = (EditText) view.findViewById(R.id.food_item_price);
        price.setInputType(InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_NUMBER_FLAG_DECIMAL);


        return view;

    }

    private final View.OnClickListener mListener1 = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.food_item_submit:
                    Toast.makeText(getContext(), "Submit Button Clicked", Toast.LENGTH_SHORT).show();

                    float priceAsFloat = Float.valueOf(price.getText().toString());
                    String nameToString = name.getText().toString();

                    Log.d("food name", nameToString);
                    Log.d("food price", priceAsFloat + "");
//                    Log.d("review id", review.getId() + "");



                    foodItem.setName(nameToString);
                    foodItem.setPrice(priceAsFloat);
//                    foodItem.setReviewId(review.getId());

                    createFoodItem();

                break;
                case R.id.add_food_back_btn:
                    Toast.makeText(getContext(), "Back Button Clicked", Toast.LENGTH_SHORT).show();
                    replaceFragment();
                    break;
            }
        }
    };


    public void createFoodItem(){

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                foodDAO.insert(foodItem);
                for (FoodItem r: foodDAO.getAllItems()) {
                    Log.d("database id", r.getFoodId() + "");
                    Log.d("database food", r.getName());
                    Log.d("database price", r.getPrice() + "");
                    Log.d("database reviewId", r.getReviewId() + "");
                }
                return null;
            }
        }.execute();
        replaceFragment();
    }

    public void replaceFragment() {

        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurant", restaurant);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment fragment = WriteReviewFragment.newInstance(restaurant);
        ft.replace(R.id.item_container, fragment);
        ft.commit();

    }

}