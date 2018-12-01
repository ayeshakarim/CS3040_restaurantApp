package com.ayesha.cs3040.CS3040_restaurantApp.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayesha.cs3040.myapp1.R;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    private List<CuisineItem> cuisineList;
    private List<StyleItem> styleList;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        view.findViewById(R.id.search_location_btn).setOnClickListener(mListener);

        recyclerView1 = (RecyclerView) view.findViewById(R.id.search_cuisine_hzv);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        cuisineList = new ArrayList<>();

        cuisineList.add(new CuisineItem("Cuisine Name", R.drawable.ic_restaurant_black_24dp));
        cuisineList.add(new CuisineItem("Cuisine Name", R.drawable.ic_restaurant_black_24dp));
        cuisineList.add(new CuisineItem("Cuisine Name", R.drawable.ic_restaurant_black_24dp));
        cuisineList.add(new CuisineItem("Cuisine Name", R.drawable.ic_restaurant_black_24dp));
        cuisineList.add(new CuisineItem("Cuisine Name", R.drawable.ic_restaurant_black_24dp));

        SearchRecyclerAdapter1 mAdapter1 = new SearchRecyclerAdapter1(cuisineList);

        recyclerView1.setAdapter(mAdapter1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());



        recyclerView2 = (RecyclerView) view.findViewById(R.id.search_style_hzv);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        styleList = new ArrayList<>();

        styleList.add(new StyleItem("Style Name", R.drawable.ic_restaurant_menu_black_24dp));
        styleList.add(new StyleItem("Style Name", R.drawable.ic_restaurant_menu_black_24dp));
        styleList.add(new StyleItem("Style Name", R.drawable.ic_restaurant_menu_black_24dp));
        styleList.add(new StyleItem("Style Name", R.drawable.ic_restaurant_menu_black_24dp));
        styleList.add(new StyleItem("Style Name", R.drawable.ic_restaurant_menu_black_24dp));
        styleList.add(new StyleItem("Style Name", R.drawable.ic_restaurant_menu_black_24dp));
        styleList.add(new StyleItem("Style Name", R.drawable.ic_restaurant_menu_black_24dp));

        SearchRecyclerAdapter2 mAdapter2 = new SearchRecyclerAdapter2(styleList);

        recyclerView2.setAdapter(mAdapter2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());


        return view;
    }

    private final View.OnClickListener mListener = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.search_location_btn:
                    Toast.makeText(getContext(), "Can't edit location at this time" , Toast.LENGTH_SHORT ).show();
                    break;

            }
        }
    };

}
