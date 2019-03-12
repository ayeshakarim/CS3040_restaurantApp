package com.ayesha.cs3040.CS3040_restaurantApp.explore;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.item.ItemActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.myapp1.R;

public class VisitedRecyclerAdapter extends RecyclerView.Adapter<VisitedRecyclerAdapter.ViewHolder> implements Serializable {

    private Context mContext;
    public List<RestaurantItem> home_list;
    private FragmentActivity c;
    private Looper looper;

    private RestaurantDAO restaurantDAO;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private Calendar newCal = Calendar.getInstance();


    public VisitedRecyclerAdapter(Context context, FragmentActivity c, List<RestaurantItem> list) {
        this.mContext = context;
        this.c = c;
        this.home_list = list;
        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(c);
        restaurantDAO  = db.getRestaurantDao();
    }

    @NonNull
    @Override
    public VisitedRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookings_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitedRecyclerAdapter.ViewHolder holder, final int position) {

        if (home_list != null){

        final String item_name = home_list.get(position).getName();
        final String item_address = home_list.get(position).getItem_address();
            String date = null;

            if (home_list.get(position).dateBooked != null) {  date = getDate(home_list.get(position)); }
            else { date = " booking date is not set!" ; }


        holder.name.setText(item_name);
        holder.address.setText(item_address);
        holder.date.setText(date);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(c, item_name, Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putSerializable("restaurant", home_list.get(position));
                Intent intent = new Intent(c, ItemActivity.class);
                intent.putExtras(bundle);
                c.startActivity(intent);

            }
        });

        holder.bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    String newId = home_list.get(position).id + new Date();
                    RestaurantItem newBooking = home_list.get(position);
                    newBooking.setId(newId);

                    getBookingDate(newBooking);
                    notifyDataSetChanged();
                    Toast.makeText(c, "booking " + home_list.get(position).name, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    deleteItem(home_list.get(position));
                    home_list.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(c, "deleting " + home_list.get(position).getName(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (home_list.get(position).isBooked()) {
            holder.bookBtn.setEnabled(false);
            holder.bookBtn.setText("Booked");
            holder.bookBtn.setBackgroundColor(Color.parseColor("grey"));
            holder.bookBtn.setTextColor(Color.parseColor("#555555"));
        }
        }

    }

    private String getDate(RestaurantItem restaurant) {
        Date date = restaurant.getDateBooked();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy,  hh:mm");

        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public void bookRestaurant(final RestaurantItem restaurant) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                looper.prepare();
                restaurantDAO.insert(restaurant);
                restaurantDAO.setVisited(restaurant.id, false);
                restaurantDAO.updateBookingDate(restaurant.id, newCal.getTime());

                for (RestaurantItem r: restaurantDAO.getAll()) {
                    Log.d("database id", r.id );
                    Log.d("database name", r.name);
                    Log.d("database address", r.address);
                    Log.d("database date", r.dateBooked + "");

                }
                home_list = restaurantDAO.findBookingsVisited(true);
                return null;
            }
        }.execute();

    }

    public void deleteItem(final RestaurantItem restaurant) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                looper.prepare();
                restaurantDAO.delete(restaurant);;
                for (RestaurantItem r: restaurantDAO.getAll()) {
                    Log.d("database",  "deleting: " + r.getName());
                }
                return null;
            }
        }.execute();
    }

    public void getBookingDate(final RestaurantItem booking) {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

//                        newCal.set(year, monthOfYear, dayOfMonth);
                        getBookingTime(booking, year, monthOfYear, dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void getBookingTime(final RestaurantItem booking, final int year, final int monthOfYear, final int dayOfMonth){

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                newCal.set(year, monthOfYear, dayOfMonth, hourOfDay, minute);
                bookRestaurant(booking);

            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public List<RestaurantItem> getVisitedList() {
        return home_list;
    }


    @Override
    public int getItemCount() {
        return home_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView name;
        private TextView address;
        private TextView date;
        private RelativeLayout parentLayout;
        private Button bookBtn;
        private ImageView deleteBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = mView.findViewById(R.id.item_name);
            address = mView.findViewById(R.id.item_address);
            date = mView.findViewById(R.id.item_date);
            parentLayout = mView.findViewById(R.id.bookings_parent_layout);
            bookBtn = mView.findViewById(R.id.book_btn);
            deleteBtn = mView.findViewById(R.id.home_deleteBtn);
        }
    }

}
