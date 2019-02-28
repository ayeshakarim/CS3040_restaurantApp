package com.ayesha.cs3040.CS3040_restaurantApp.bookings;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.MainActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.RestaurantDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.RestaurantDatabase;
import com.ayesha.cs3040.myapp1.R;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

        if (item_list != null) {

            String name = item_list.get(position).getName();
            String address = item_list.get(position).getItem_address();
            String date =  getDate(item_list.get(position));

//        int imageURL = item_list.get(position).getId();
            holder.name.setText(name);
            holder.address.setText(address);
            holder.date.setText(date);
//        holder.image.setImageResource(imageURL);

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

            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        deleteBooking(item_list.get(position));
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "deleting " + item_list.get(position).getName(), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    private String getDate(RestaurantItem r) {
        Date date = r.getDateBooked();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");

        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public void setVisited(final RestaurantItem r) {

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        Looper.prepare();
                        restaurantDAO.updateVisited(r.id, true);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, r.getName()+ " added to visited list", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                }.execute();

    }

    public void deleteBooking(final RestaurantItem restaurant) {

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {

                        Looper.prepare();
                        restaurantDAO.delete(restaurant);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, restaurant.getName() + " is deleted", Toast.LENGTH_SHORT).show();

                        for (RestaurantItem r : restaurantDAO.getAll()) {
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
        private TextView date;
        private ImageView btnLable;
        private ImageView deleteBtn;
        private LinearLayout parentLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = mView.findViewById(R.id.booking_name);
            address = mView.findViewById(R.id.booking_address);
            btnLable = mView.findViewById(R.id.visit_btn);
            deleteBtn = mView.findViewById(R.id.booking_deleteBtn);
            date = mView.findViewById(R.id.booking_date);

        }
    }
}
