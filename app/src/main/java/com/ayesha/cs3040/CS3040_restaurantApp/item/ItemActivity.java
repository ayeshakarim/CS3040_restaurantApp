package com.ayesha.cs3040.CS3040_restaurantApp.item;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayesha.cs3040.myapp1.R;
import com.ayesha.cs3040.CS3040_restaurantApp.profile.ProfileActivity;

public class ItemActivity extends AppCompatActivity {


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

        if(getIntent().hasExtra("item_name")){

            String itemName = getIntent().getStringExtra("item_name");

            setName( itemName);
        }
    }

    private void setName( String itemName){

        TextView name = findViewById(R.id.item_name);
        name.setText(itemName);
    }

    public void openSection(int value){
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("section_name", value );
        startActivity(i);
    }
}
