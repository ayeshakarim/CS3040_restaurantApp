package com.ayesha.cs3040.CS3040_restaurantApp.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayesha.cs3040.myapp1.R;


public class PersonalInfoFragment extends Fragment {

    public PersonalInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);

        view.findViewById(R.id.personal_info_back_btn).setOnClickListener(mListener);

        return view;
    }

    private final View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.personal_info_back_btn:
                    Toast.makeText(getContext(), "Personal Info Page Closed", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    break;
                case R.id.personal_info_edit_btn:
                    Toast.makeText(getContext(), "Edit Button clicked", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
