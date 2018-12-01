package com.ayesha.cs3040.CS3040_restaurantApp.review;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayesha.cs3040.myapp1.R;


public class WriteReviewFragment extends Fragment {

    final Fragment addFoodFragment = new AddFoodFragment();
    public WriteReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_review, container, false);

        view.findViewById(R.id.review_back_btn).setOnClickListener(mListener);
        view.findViewById(R.id.review_submit).setOnClickListener(mListener);
        view.findViewById(R.id.add_food_item).setOnClickListener(mListener);

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
                    getActivity().finish();
                    break;
                case R.id.add_food_item:
                    Toast.makeText(getContext(), "Open Add Food Fragment", Toast.LENGTH_SHORT).show();
                    replaceFragment(addFoodFragment);
                    break;
            }
        }
    };

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.profile_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
