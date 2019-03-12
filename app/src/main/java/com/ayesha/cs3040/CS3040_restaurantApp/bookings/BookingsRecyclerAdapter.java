package com.ayesha.cs3040.CS3040_restaurantApp.bookings;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.explore.VisitedRecyclerAdapter;
import com.ayesha.cs3040.myapp1.R;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookingsRecyclerAdapter extends RecyclerView.Adapter<BookingsRecyclerAdapter.ViewHolder>{

    private  Context mContext;
    private List<RestaurantItem> item_list;
    private Looper looper;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private Calendar newCal = Calendar.getInstance();

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
            String date = null;

            if (item_list.get(position).dateBooked != null) {  date = getDate(item_list.get(position)); }
            else { date = " booking date is not set!" ; }

//        int imageURL = item_list.get(position).getId();
            holder.name.setText(name);
            holder.address.setText(address);
            holder.date.setText(date);
//        holder.image.setImageResource(imageURL);

            holder.btnVisit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        setVisited(item_list.get(position));
                        item_list.remove(position);
                        notifyDataSetChanged();
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
                        item_list.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "deleting " + item_list.get(position).getName(), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        getBookingDate(item_list.get(position));
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        }

    }

    private String getDate(RestaurantItem r) {
        Date date = r.getDateBooked();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy,   hh:mm");

        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public void setVisited(final RestaurantItem booking) {

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {

                        looper.prepare();
                        restaurantDAO.setVisited(booking.id, true);
                        Toast.makeText(mContext, booking.getName()+ " added to visited list", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                }.execute();
    }
    public void setBooked(final RestaurantItem booking) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                looper.prepare();
                restaurantDAO.setVisited(booking.id, false);
                Toast.makeText(mContext, booking.getName()+ " added to bookings list", Toast.LENGTH_SHORT).show();
                return null;
            }
        }.execute();
    }

    public void deleteBooking(final RestaurantItem booking) {

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {

                        looper.prepare();
                        restaurantDAO.delete(booking);
                        Toast.makeText(mContext, booking.getName() + " is deleted", Toast.LENGTH_SHORT).show();

                        for (RestaurantItem r : restaurantDAO.getAll()) {
                            Log.d("database", r.id + " " + r.getName());
                        }
                        return null;
                    }
                }.execute();
    }

    public void updateBookingDate(final RestaurantItem booking) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                looper.prepare();
                restaurantDAO.updateBookingDate(booking.id, newCal.getTime());
                Toast.makeText(mContext, booking.getName() + " is deleted", Toast.LENGTH_SHORT).show();

                Log.d("database",  booking.name + ": " + booking.dateBooked);
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
                updateBookingDate(booking);
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public List<RestaurantItem> getData() {
        return item_list;
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
        private ImageView btnVisit;
        private ImageView deleteBtn;
        private RelativeLayout parentLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = mView.findViewById(R.id.booking_name);
            address = mView.findViewById(R.id.booking_address);
            btnVisit = mView.findViewById(R.id.visit_btn);
            deleteBtn = mView.findViewById(R.id.booking_deleteBtn);
            date = mView.findViewById(R.id.booking_date);
            parentLayout = mView.findViewById(R.id.booked_item_layout);

        }
    }
}
