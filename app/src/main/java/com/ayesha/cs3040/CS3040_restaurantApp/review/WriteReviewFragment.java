package com.ayesha.cs3040.CS3040_restaurantApp.review;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.db.FoodItemDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.db.ReviewDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.myapp1.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class WriteReviewFragment extends Fragment {

    private TextView restaurant_name;
    private TextView restaurant_address;
    private EditText comment;
    private RatingBar rating;
    private ImageView imageView;
    private ImageView setImageBtn;
    private TextView setImageText;

    private Uri uri;
    private List<FoodItem> rv_list;
    private RecyclerView recyclerView;

    private Review review;
    private RestaurantItem restaurant;
    private boolean isCreated;

    private ReviewDAO reviewDAO;
    private FoodItemDAO foodDAO;

    private int GALLERY_REQUEST_CODE = 1;


    public WriteReviewFragment() {
        // Required empty public constructor
    }

    public static WriteReviewFragment newInstance(RestaurantItem r) {
        WriteReviewFragment fragment = new WriteReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurant", r);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_review, container, false);

        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(getContext());
        reviewDAO  = db.getReviewDao();
        foodDAO = db.getFoodItemDao();

        restaurant = (RestaurantItem) getActivity().getIntent().getSerializableExtra("restaurant");
        checkIsCreated();



        Log.d("serializable", restaurant.name + " " + restaurant.address);

        recyclerView = (RecyclerView) view.findViewById(R.id.food_item_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        view.findViewById(R.id.review_back_btn).setOnClickListener(mListener);
        view.findViewById(R.id.review_submit).setOnClickListener(mListener);
        view.findViewById(R.id.add_food_item).setOnClickListener(mListener);
        view.findViewById(R.id.add_review_image).setOnClickListener(mListener);

        restaurant_name = view.findViewById(R.id.review_restaurant_name);
        restaurant_address = view.findViewById(R.id.review_restaurant_address);
        restaurant_name.setText(restaurant.name);
        restaurant_address.setText(restaurant.address);

        comment = (EditText) view.findViewById(R.id.review_comment);
        rating = (RatingBar) view.findViewById(R.id.review_rating);
        imageView = (ImageView) view.findViewById(R.id.review_image);
        setImageBtn = (ImageView) view.findViewById(R.id.add_image_circle);
        setImageText = (TextView) view.findViewById(R.id.add_image_text);

        return view;
    }


    private final View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View view) {

            String commentToString = comment.getText().toString();
            String ratingToString = Float.toString(rating.getRating());
            Log.d("input info: ", commentToString + "   Rating: "+ ratingToString);

            review.setComment(commentToString);
            review.setRating(rating.getRating());

            switch (view.getId()) {
                case R.id.review_back_btn:
                    Toast.makeText(getContext(), "Back Button Clicked", Toast.LENGTH_SHORT).show();
                    getActivity().finish();

                    break;

                case R.id.review_submit:
                    Toast.makeText(getContext(), "Submit Button Clicked", Toast.LENGTH_SHORT).show();

                    if (isCreated){

                        updateReview();

                    }
                    else {
                        createReview(1);
                    }

                    Log.d( "REVIEW", commentToString + " RATING : " + ratingToString);
                    getActivity().finish();

                    break;

                case R.id.add_food_item:
                    Toast.makeText(getContext(), "Open Add Food Fragment", Toast.LENGTH_SHORT).show();

                    if (isCreated){openAddFoodFragment(); updateReview(); }
                    else { createReview(2); }

                    break;

                case R.id.add_review_image:
                    Log.d("getImage", "button clicked");
                    getImage();

                    break;
            }
        }
    };


    public void getImage() {

        Log.d("getImage", "method started");

        Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputY", 300);
        intent.putExtra("outputX", 300);
        intent.putExtra("scale", true);

        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, 1);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

                if(resultCode == RESULT_OK){
                    try {
                        Uri uri = imageReturnedIntent.getData();

                        review.setImageUri(uri.toString());
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(uri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        setImageText.setVisibility(View.GONE);
                        setImageBtn.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
    }

    public void setFoodList(){

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                rv_list = foodDAO.getFoodForReview(review.getId());

                if(foodDAO.getFoodForReview(review.getId()) == null || foodDAO.getFoodForReview(review.getId()).size() == 0){
                    Log.d("database =", " NO FOOD ITEMS");

                } else {

                    for (FoodItem r: rv_list) {
                        Log.d("database id", r.getFoodId() + "" );
                        Log.d("database name", r.getName());
                        Log.d("database price", r.getPrice() + "");
                    }
                }

                ReviewRecyclerAdapter mAdapter = new ReviewRecyclerAdapter(getContext(), rv_list);

                recyclerView.setAdapter(mAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                return null;
            }
        }.execute();
    }

    public void createReview(int i ){

        if (i == 2) {
            openAddFoodFragment();
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                reviewDAO.insert(review);

                for (Review r: reviewDAO.getAllReviews()) {
                    Log.d("database id", r.getId() + "");
                    Log.d("database comment", r.getComment());
                    Log.d("database rating", r.getRating() + "");
                    Log.d("database restaurant", r.getRestaurantId());
                }
            return null;
            }
        }.execute();
    }

    public void updateReview() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                reviewDAO.update(review);

                Review r = reviewDAO.getReview(review.getId());
                Log.d("update", r.getComment());
                Log.d("update", r.getRating() + "");
                Log.d("update", r.getRestaurantId());
//                Log.d("update", r.getFoodItems().toString());
                return null;
            }
        }.execute();

    }

    private void checkIsCreated() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                review = reviewDAO.findReviewsForRestaurant(restaurant.id);

                if (review != null) {

                    isCreated = true;
                    comment.setText(review.getComment());
                    rating.setRating(review.getRating());
                    setFoodList();

                } else {
                    isCreated = false;
                    review = new Review(restaurant.id);
                    review.setRestaurantId(restaurant.id);
                    Log.e("null", "is new");
                }
                return null;
            }
        }.execute();
    }

    public void openAddFoodFragment() {

        Bundle bundle = new Bundle();
        bundle.putSerializable("review", review);
        bundle.putSerializable("restaurant", restaurant);
        bundle.putInt("review id", review.getId());

        Log.d("getreview", review.getId() + "");

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment fragment = AddFoodFragment.newInstance(review, restaurant, review.getId());
        ft.replace(R.id.item_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
