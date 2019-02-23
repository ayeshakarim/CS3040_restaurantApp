package com.ayesha.cs3040.CS3040_restaurantApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ayesha.cs3040.CS3040_restaurantApp.item.ItemActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.myapp1.R;

import java.io.Serializable;
import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> implements Serializable {

    public List<RestaurantItem> restaurant_list;
    private Context mContext;

    public SearchRecyclerAdapter(Context context, List<RestaurantItem> list) {
        this.mContext = context;
        this.restaurant_list = list;
    }


    @NonNull
    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_search_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerAdapter.ViewHolder holder, final int position) {

        String name = restaurant_list.get(position).getItem_name();
        String address = restaurant_list.get(position).getAddress();
        String website = restaurant_list.get(position).getWebsite();
        float rating = restaurant_list.get(position).getRating();
        int price = restaurant_list.get(position).getPriceLevel();

        final RestaurantItem restaurant =  restaurant_list.get(position);

        holder.name.setText(name);
        holder.address.setText(address);
        holder.website.setText(website);
        holder.rating.setRating(rating);
        holder.mealPrice.setText("Price: " + price +" / 4");

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("restaurant", restaurant);
//        Intent intent = new Intent(mContext, ItemActivity.class);
//        intent.putExtras(bundle);
//        mContext.startActivity(intent);


    }

    @Override
    public int getItemCount() {
        return restaurant_list.size();    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView name;
        //        private ImageView image;
        private TextView address;
        private RatingBar rating;
        private TextView website;
        private TextView mealPrice;
        private LinearLayout parentLayout;
        private Button bookBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = mView.findViewById(R.id.search_item_name);
//            image = mView.findViewById(R.id.item_image);
            address = mView.findViewById(R.id.search_item_address);
            website = mView.findViewById(R.id.search_item_website);
            rating = mView.findViewById(R.id.search_item_rating);
            mealPrice =mView.findViewById(R.id.price);
            parentLayout = mView.findViewById(R.id.restaurant_search_item);
            bookBtn = mView.findViewById(R.id.search_book_btn);
        }
    }

}
