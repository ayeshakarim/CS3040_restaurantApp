package com.ayesha.cs3040.CS3040_restaurantApp.review;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ayesha.cs3040.myapp1.R;


public class AddFoodFragment extends Fragment {

    public static final String[] COURSE_OPTIONS =  {"Starter", "Main", "Dessert", "Appetizer", "Side"};
//    final Fragment reviewFragment = new WriteReviewFragment();
    public EditText name;
    public EditText price;

    public AddFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);

        Spinner spinner = view.findViewById(R.id.food_course_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, COURSE_OPTIONS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(mListener);

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
//                    replaceFragment(reviewFragment);

                break;
                case R.id.add_food_back_btn:
                    Toast.makeText(getContext(), "Back Button Clicked", Toast.LENGTH_SHORT).show();
//                    replaceFragment(reviewFragment);
                    break;
            }
        }
    };

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.commit();
    }



    private final AdapterView.OnItemSelectedListener mListener = (new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (position) {
                case 0:
                    Toast.makeText(getContext(), "Option 1 Clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getContext(), "Option 2 Clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getContext(), "Option 3 Clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getContext(), "Option 4 Clicked", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(getContext(), "Option 5 Clicked", Toast.LENGTH_SHORT).show();
                    break;

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    });

}