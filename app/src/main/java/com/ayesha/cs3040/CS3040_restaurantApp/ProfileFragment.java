package com.ayesha.cs3040.CS3040_restaurantApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayesha.cs3040.myapp1.R;
import com.ayesha.cs3040.CS3040_restaurantApp.profile.ProfileActivity;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        view.findViewById(R.id.btn_user_info).setOnClickListener(mListener);
//        view.findViewById(R.id.btn_add_restaurant).setOnClickListener(mListener);
        view.findViewById(R.id.btn_help).setOnClickListener(mListener);
        view.findViewById(R.id.btn_terms).setOnClickListener(mListener);
        view.findViewById(R.id.btn_write_review).setOnClickListener(mListener);
        view.findViewById(R.id.btn_password).setOnClickListener(mListener);

        return view;

    }

    private final View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View view) {
            Fragment fragment = null;
            switch (view.getId()) {
                case R.id.btn_user_info:
                    Toast.makeText(getContext(), "User Info Button Clicked" , Toast.LENGTH_SHORT ).show();
                    openSection(1);

                    break;
                case R.id.btn_write_review:
                    Toast.makeText(getContext(), "Write Review Button Clicked" , Toast.LENGTH_SHORT ).show();
                    openSection(2);

                    break;
//                case R.id.btn_add_restaurant:
//                    Toast.makeText(getContext(), "Add Restaurant Button Clicked" , Toast.LENGTH_SHORT ).show();
//                    break;

                case R.id.btn_terms:
                    Toast.makeText(getContext(), "Term Button Clicked" , Toast.LENGTH_SHORT ).show();
                    break;
                case R.id.btn_help:
                    Toast.makeText(getContext(), "Help Button Clicked" , Toast.LENGTH_SHORT ).show();
                    break;
                case R.id.btn_password:
                    Toast.makeText(getContext(), "Change Password Button Clicked", Toast.LENGTH_SHORT ).show();
                    break;            }
        }
    };

    public void openSection(int value){
        Intent i = new Intent(getContext(), ProfileActivity.class);
        i.putExtra("section_name", value );
        startActivity(i);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
