package com.ayesha.cs3040.CS3040_restaurantApp.bookings;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.RestaurantDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.RestaurantDatabase;
import com.ayesha.cs3040.myapp1.R;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;

import java.util.Date;
import java.util.List;

public class BookingsRecyclerAdapter extends RecyclerView.Adapter<BookingsRecyclerAdapter.ViewHolder>{

    private  Context mContext;
    private List<RestaurantItem> item_list;

    private RestaurantDAO restaurantDAO;

    public BookingsRecyclerAdapter(Context context, List<RestaurantItem> items) {
        mContext = context;
        this.item_list = items;
        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(context);
        restaurantDAO  = db.getRestaurantDao();
    }

    @NonNull
    @Override
    public BookingsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_item, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        String name = item_list.get(position).getName();
        String address = item_list.get(position).getItem_address();
//        int imageURL = item_list.get(position).getId();
        holder.name.setText(name);
        holder.address.setText(address);
//        holder.image.setImageResource(imageURL);

            holder.btnLable.setText("Add to Visited");

        holder.btnLable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    setVisited(item_list.get(position));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void setVisited(final RestaurantItem r) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                restaurantDAO.updateVisited(true);
                Toast.makeText(mContext, r.getName()+ " added to visited list", Toast.LENGTH_SHORT).show();
                return null;
            }
        }.execute();
    }

    public void deleteBooking(final RestaurantItem restaurant) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                restaurantDAO.insert(restaurant);

                for (RestaurantItem r: restaurantDAO.getAll()) {
                    Log.d("database", r.id + " " + r.getName());
                }
                return null;
            }
        }.execute();
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView name;
        private TextView address;
        private TextView btnLable;
        private LinearLayout parentLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = mView.findViewById(R.id.booking_name);
            address = mView.findViewById(R.id.booking_address);
            btnLable = mView.findViewById(R.id.visit_btn);

        }
    }
}
