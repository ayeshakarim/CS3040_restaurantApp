package com.ayesha.cs3040.CS3040_restaurantApp.item;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.db.ReviewDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.review.Review;
import com.ayesha.cs3040.CS3040_restaurantApp.review.WriteReviewFragment;
import com.ayesha.cs3040.myapp1.R;
import com.ayesha.cs3040.CS3040_restaurantApp.profile.ProfileActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;


public class ItemActivity extends AppCompatActivity implements Serializable {

    public RestaurantItem restaurant;
    private RecyclerView recyclerView;
    private List<Review> rv_list;
    private ReviewDAO reviewDAO;
    private FloatingActionButton writeReview;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_restaurant_item);
        getIncomingIntent();
        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(this);
        reviewDAO  = db.getReviewDao();

        ImageView backBtn = (ImageView) findViewById(R.id.item_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.item_reviews_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setList();

        writeReview = (FloatingActionButton) findViewById(R.id.fab_write_review);
        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openSection();
            }
        });
        imageView = (ImageView) findViewById(R.id.item_image);



    }

    public void setList () {
        rv_list = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (reviewDAO.findReviewsForRestaurant(restaurant.id) != null) {

                    rv_list.add(reviewDAO.findReviewsForRestaurant(restaurant.id));

//                    for (Review r : rv_list) {
//
//                        Log.d("database id", r.getId() + "");
//                        if (r.getComment() != null) {
//                            Log.d("database rating", r.getRating() + "");
//                            Log.d("database comment", r.getComment());
//                            Log.d("database image", r.getImageUri());
//                        }
//                    }

                    if (reviewDAO.findReviewsForRestaurant(restaurant.id).getImageUri() != null) {
                        setImage(reviewDAO.findReviewsForRestaurant(restaurant.id).getImageUri());
                    }

                    ItemReviewRecyclerAdapter mAdapter = new ItemReviewRecyclerAdapter(getBaseContext(), rv_list, restaurant);

                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                } else  {

                    TextView noReviews = findViewById(R.id.item_no_reviews);
                    noReviews.setVisibility(View.VISIBLE);
                }
                return null;
            }
        }.execute();
    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("restaurant")){

            Bundle bundle = getIntent().getExtras();
            if(bundle!=null) {
                    restaurant = (RestaurantItem) bundle.getSerializable("restaurant");
                    String item_name = restaurant.getName();
                    String address = restaurant.getItem_address();
                    String website = restaurant.getWebsite();
                    float rating = restaurant.getRating();
                    Date date = restaurant.getDateBooked();

                    setParams(item_name, address, rating, website, date);

            }
            else {
                Log.e("null", "null");
            }

        }

    }

    public void setImage(String uri){

        if (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            Log.d("permission"," " + ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) );
            Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
        }
        else {
//        try {
//            final InputStream imageStream = getContentResolver().openInputStream(uri);
//            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

            imageView.setVisibility(View.VISIBLE);
//            imageView.setImageURI(Uri.parse(uri));
        }

//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }


    }
    public void linkClicked(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private String getDate(Date d) {


        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy,  hh:mm");

        String formattedDate = dateFormat.format(d);
        return formattedDate;
    }

    private void setParams(String itemName, String address, float itemRating, final String itemWebsite, Date date ){

        TextView name = findViewById(R.id.item_name);
        name.setText(itemName);

        TextView item_date = findViewById(R.id.item_date);
        item_date.setText(getDate(date));

        TextView mAddress = findViewById(R.id.item_address);
        mAddress.setText(address);

        RatingBar rating = findViewById(R.id.item_rating);
        rating.setRating(itemRating);

        TextView website = findViewById(R.id.item_website);
        website.setText(itemWebsite);
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    linkClicked(itemWebsite);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void SHARE(View view) {

        String review = getDate(restaurant.getDateBooked())+ "   ";

        if (rv_list != null){
            for (Review r: rv_list) { review += "rating: " + r.getRating()+ "   Comment: " + r.getComment(); }
        } else { review += "not reviewed";}

        String shareBody = review;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, restaurant.name + "  " + getDate(restaurant.getDateBooked()));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent,  "Share with:"));
    }

    public void openSection(){

        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurant", restaurant);

        setContentView(R.layout.activity_item);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = WriteReviewFragment.newInstance(restaurant);
        ft.replace(R.id.item_container, fragment);
        ft.commit();
    }
}
