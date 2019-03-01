package com.ayesha.cs3040.CS3040_restaurantApp.item;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ayesha.cs3040.myapp1.R;
import com.ayesha.cs3040.CS3040_restaurantApp.profile.ProfileActivity;

import java.io.Serializable;

public class ItemActivity extends AppCompatActivity implements Serializable {

    public RestaurantItem restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        getIncomingIntent();

        ImageView backBtn = (ImageView) findViewById(R.id.item_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FloatingActionButton writeReview = (FloatingActionButton) findViewById(R.id.fab_write_review);
        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openSection(2);
            }
        });
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

                setParams(item_name, address, rating, website);

            }
            else {
                Log.e("null", "null");
            }

        }
    }

    private void setParams( String itemName, String address, float itemRating, String itemWebsite ){

        TextView name = findViewById(R.id.item_name);
        name.setText(itemName);

        TextView item_address = findViewById(R.id.item_address);
        item_address.setText(address);

        RatingBar rating = findViewById(R.id.item_rating);
        rating.setRating(itemRating);

        TextView website = findViewById(R.id.item_website);
        website.setText(itemWebsite);
    }


    public void openSection(int value){

        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurant", restaurant);
        Intent intent = new Intent(this , ProfileActivity.class);
        intent.putExtras(bundle);
        intent.putExtra("section_name", value );
        startActivity(intent);
    }
}
