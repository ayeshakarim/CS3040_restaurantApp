package com.ayesha.cs3040.CS3040_restaurantApp.bookings;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayesha.cs3040.myapp1.R;
import com.ayesha.cs3040.CS3040_restaurantApp.explore.ExploreItem;

import java.util.List;

public class BookingsRecyclerAdapter extends RecyclerView.Adapter<BookingsRecyclerAdapter.ViewHolder>{

    private List<ExploreItem> item_list;

    public BookingsRecyclerAdapter(List<ExploreItem> items) {this.item_list = items;}

    @NonNull
    @Override
    public BookingsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String name = item_list.get(position).getItem_name();
        int imageURL = item_list.get(position).getId();
        boolean btnVisited = item_list.get(position).isVisited();
        holder.name.setText(name);
        holder.image.setImageResource(imageURL);

        if(btnVisited){
            holder.btnLable.setText("Visited");
            holder.btnLable.setBackgroundColor(Color.parseColor("grey"));
            holder.btnLable.setTextColor(Color.parseColor("#555555"));
        }
        else {
            holder.btnLable.setText("Add to Visited");
        }

    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView name;
        private ImageView image;
        private TextView btnLable;
        private LinearLayout parentLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = mView.findViewById(R.id.booked_item_text);
            image = mView.findViewById(R.id.booked_item_image);
            btnLable = mView.findViewById(R.id.add_visited_btn);

        }
    }
}
