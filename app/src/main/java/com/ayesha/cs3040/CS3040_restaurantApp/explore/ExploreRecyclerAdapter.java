package com.ayesha.cs3040.CS3040_restaurantApp.explore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import com.ayesha.cs3040.CS3040_restaurantApp.MainActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;
import com.ayesha.cs3040.CS3040_restaurantApp.item.ItemActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.CS3040_restaurantApp.profile.ProfileActivity;
import com.ayesha.cs3040.myapp1.R;

public class ExploreRecyclerAdapter extends RecyclerView.Adapter<ExploreRecyclerAdapter.ViewHolder> implements Serializable {

    public List<RestaurantItem> home_list;
    private Context mContext;

    public ExploreRecyclerAdapter(Context context, List<RestaurantItem> list) {
        this.mContext = context;
        this.home_list = list;
    }

    @NonNull
    @Override
    public ExploreRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookings_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreRecyclerAdapter.ViewHolder holder, final int position) {
//        final int id = home_list.get(position).getId();
        final String item_name = home_list.get(position).getItem_name();
        String item_address = home_list.get(position).getItem_address();
//        List<FoodItem> meal_List = home_list.get(position).getMealList();
//        for (FoodItem item : meal_List){
//           String meal_item_name = item.getName();
//        }

       final RestaurantItem restaurant =  home_list.get(position);

        holder.name.setText(item_name);
//        holder.image.setImageResource(id);
        holder.address.setText(item_address);
//        holder.mealName.setText((meal_item));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, item_name, Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(mContext, ItemActivity.class);
//                intent.putExtra("item", item_name);
//                mContext.startActivity(intent);
                Bundle bundle = new Bundle();
                bundle.putSerializable("restaurant", restaurant);
                Intent intent = new Intent(mContext, ItemActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);

            }
        });

        holder.bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                home_list.get(position).setBooked(true);

            }
        });

        if (home_list.get(position).isBooked()) {
            holder.bookBtn.setEnabled(false);
            holder.bookBtn.setText("Booked");
            holder.bookBtn.setBackgroundColor(Color.parseColor("grey"));
            holder.bookBtn.setTextColor(Color.parseColor("#555555"));
        }

    }

    @Override
    public int getItemCount() {
        return home_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView name;
//        private ImageView image;
        private TextView address;
        private TextView mealName;
        private TextView mealPrice;
        private LinearLayout parentLayout;
        private Button bookBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = mView.findViewById(R.id.item_name);
//            image = mView.findViewById(R.id.item_image);
            address = mView.findViewById(R.id.item_address);
            mealName = mView.findViewById(R.id.meal_list_name);
            mealPrice =mView.findViewById(R.id.meal_list_price);
            parentLayout = mView.findViewById(R.id.bookings_parent_layout);
            bookBtn = mView.findViewById(R.id.book_btn);
        }
    }

}
