package com.ayesha.cs3040.CS3040_restaurantApp.profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ayesha.cs3040.CS3040_restaurantApp.review.WriteReviewFragment;
import com.ayesha.cs3040.myapp1.R;

public class ProfileActivity extends AppCompatActivity {


    final Fragment personalInfoFragment = new PersonalInfoFragment();
    final Fragment writeReviewFragment = new WriteReviewFragment();
    final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        fm.beginTransaction().add(R.id.main_container, personalInfoFragment).hide(personalInfoFragment).commit();
        getIncomingIntent();

    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("section_name")) {

            int intentFragment = getIntent().getExtras().getInt("section_name");

            switch(intentFragment) {
                case 1:
                    fm.beginTransaction().replace(R.id.profile_container, personalInfoFragment).commit();
                    break;
                case 2:
                    fm.beginTransaction().replace(R.id.profile_container, writeReviewFragment).commit();
                    break;
            }
        }
    }
}
