package com.ayesha.cs3040.CS3040_restaurantApp.review;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.MainActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.db.FoodItemDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.db.ReviewDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;
import com.ayesha.cs3040.CS3040_restaurantApp.item.ItemActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.myapp1.R;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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

        Log.d("oncreate fooditem", "" + review.getId());

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

    public static class ReviewsFragment extends Fragment implements View.OnClickListener{

    private List<Review> rv_list;
    private ReviewDAO reviewDAO;
    private RecyclerView recyclerView;
    private ReviewsRecyclerAdapter mAdapter;
    private ImageView refresh;


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

            refresh = (ImageView) view.findViewById(R.id.reviews_refresh);
            refresh.setOnClickListener(this);

            setReviewsList();


            return view;
        }

        public void setReviewsList() {
            rv_list = new ArrayList<>();
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    rv_list = reviewDAO.getAllReviews();


    //                for (Review r: rv_list) {
    //                    Log.d("database id", r.getId() + "");
    //
    //                    if (r.getComment() != null) {
    //                        Log.d("database rating", r.getRating() + "");
    //                        Log.d("database comment", r.getComment());
    //                        Log.d("database image", r.getImageUri());
    //                    }
    //                }

                    mAdapter = new ReviewsRecyclerAdapter(getContext(),getActivity(), rv_list);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    return null;
                }
            }.execute();
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.reviews_refresh:

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                    rv_list = reviewDAO.getAllReviews();
                    return null;
                        }
                    }.execute();
                    Toast.makeText(getContext(), "refreshing the list", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

    public static class ReviewsRecyclerAdapter extends RecyclerView.Adapter<ReviewsRecyclerAdapter.ViewHolder> implements Serializable {

        private ReviewDAO reviewDAO;
        private RestaurantDAO restaurantDAO;
        private Context mContext;
        private FragmentActivity c;
        private List<Review> reviews;
        private List<RestaurantItem> restaurants;
        private MainActivity myActivity;

    //    private String name;
    //    private Date date;


        public ReviewsRecyclerAdapter() {

        }

        public ReviewsRecyclerAdapter(Context context, FragmentActivity c, List<Review> list) {
            this.mContext = context;
            this.reviews = list;
            RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(context);
            reviewDAO  = db.getReviewDao();
            restaurantDAO = db.getRestaurantDao();
            getRestaurant();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);

            myActivity = (MainActivity) view.getContext();
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

            float rating = reviews.get(position).getRating();
            String comment = reviews.get(position).getComment();
            String name = "Restaurant Name ";
            Date date = new Date();
            final RestaurantItem restaurantItem = reviews.get(position).getItem();

            if (restaurantItem != null) {
                name = restaurantItem.name;
                date = restaurantItem.dateBooked;
            }


            holder.comment.setText(comment);
            holder.rating.setRating(rating);
            holder.name.setText(name);
            holder.bDate.setText(getDate(date));
            holder.editBtn.setVisibility(View.GONE);

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openSection(restaurantItem);
                }
            });

            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        deleteItem(reviews.get(position));
                        reviews.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "deleting " + reviews.get(position).getId(), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        public void getRestaurant() {

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    restaurants = restaurantDAO.getAll();

                    for (final Review review : reviews) {
                        Log.d("review ", review.getRestaurantId());

                        for (RestaurantItem restaurant : restaurants) {

                        if ( restaurant.getId().equals(review.getRestaurantId())) {
                                review.setItem(restaurant);
                                Log.d("restaurants ", restaurant.id);

                            }
                        }
                    }
                    return null;
                }
            }.execute();
        }

        public String getDate( Date date) {

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy,  hh:mm");
            String formattedDate = dateFormat.format(date);
            return formattedDate;
        }


        public void deleteItem(final Review item) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    reviewDAO.delete(item);

                    for (Review r: reviewDAO.getAllReviews()) {
                        Log.d("database", r.getId() + " " + r.getRestaurantId());
                    }
                    return null;
                }
            }.execute();
        }

        public void openSection(final RestaurantItem restaurantItem){

            Bundle bundle = new Bundle();
            bundle.putSerializable("restaurant", restaurantItem);
            Intent intent = new Intent(myActivity, ItemActivity.class);
            intent.putExtras(bundle);
            myActivity.startActivity(intent);
        }

        @Override
        public int getItemCount() { return reviews.size(); }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private View mView;
            private TextView name;
            private RatingBar rating;
            private TextView comment;
            private TextView bDate;
            private ImageView deleteBtn;
            private ImageView editBtn;
            private RelativeLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                name = mView.findViewById(R.id.review_name);
                rating = mView.findViewById(R.id.my_rating);
                bDate =  mView.findViewById(R.id.review_date);
                comment = mView.findViewById(R.id.my_comment);
                deleteBtn = mView.findViewById(R.id.review_deleteBtn);
                editBtn = mView.findViewById(R.id.review_edit_btn);
                layout = mView.findViewById(R.id.review_item_layout);
            }
        }
    }
}