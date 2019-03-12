package com.ayesha.cs3040.CS3040_restaurantApp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.myapp1.R;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> implements Serializable {

    public List<RestaurantItem> restaurant_list;
    private Context mContext;
    private Looper looper;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private Calendar newCal = Calendar.getInstance();
    private RestaurantDAO restaurantDAO;


    public SearchRecyclerAdapter(Context context, List<RestaurantItem> list) {
        this.mContext = context;
        this.restaurant_list = list;
        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(context);
        restaurantDAO  = db.getRestaurantDao();

    }


    @NonNull
    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_search_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerAdapter.ViewHolder holder, final int position) {

        String name = restaurant_list.get(position).getName();
        String address = restaurant_list.get(position).getAddress();
        final String website = restaurant_list.get(position).getWebsite();
        float rating = restaurant_list.get(position).getRating();
        int price = restaurant_list.get(position).getPriceLevel();


        holder.name.setText(name);
        holder.address.setText(address);
        holder.website.setText(website);
        holder.rating.setRating(rating);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    linkClicked(website);
                    Toast.makeText(mContext, "booking " + restaurant_list.get(position).name, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        holder.mealPrice.setText("Price: " + price +" / 4");


        holder.bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    getBookingDate(restaurant_list.get(position));
                    Toast.makeText(mContext, "booking " + restaurant_list.get(position).name, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void bookRestaurant(final RestaurantItem booking) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                looper.prepare();
                booking.setId(booking.id + new Date());

                restaurantDAO.insert(booking);
                restaurantDAO.setAddress(booking.id, booking.getAddress());
                restaurantDAO.updateBookingDate(booking.id, newCal.getTime());
                Toast.makeText(mContext, "booking " +  booking.name + " for " + booking.dateBooked, Toast.LENGTH_SHORT).show();

                for (RestaurantItem r: restaurantDAO.getAll()) {
                    Log.d("database id", r.id );
                    Log.d("database name", r.name);
                    Log.d("database address", r.address);
                    Log.d("database date", r.dateBooked + "");



                }
                return null;
            }
        }.execute();
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);

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
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

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

                newCal.set(year, monthOfYear, dayOfMonth,hourOfDay, minute);
                bookRestaurant(booking);

            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void linkClicked(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        mContext.startActivity(intent);
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
