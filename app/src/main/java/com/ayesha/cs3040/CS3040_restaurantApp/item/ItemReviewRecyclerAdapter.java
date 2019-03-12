package com.ayesha.cs3040.CS3040_restaurantApp.item;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.ReviewsFragment;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.db.ReviewDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.review.Review;
import com.ayesha.cs3040.CS3040_restaurantApp.review.ReviewRecyclerAdapter;
import com.ayesha.cs3040.myapp1.R;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ItemReviewRecyclerAdapter extends RecyclerView.Adapter<ItemReviewRecyclerAdapter.ViewHolder> implements Serializable {

    private ReviewDAO reviewDAO;
    private RestaurantDAO restaurantDAO;
    private Context mContext;
    private FragmentActivity c;
    private List<Review> reviews;
    private RestaurantItem r;
    private List<RestaurantItem> r_items;


    public ItemReviewRecyclerAdapter(Context context, List<Review> list, RestaurantItem r) {
        this.mContext = context;
        this.reviews = list;
        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(context);
        reviewDAO  = db.getReviewDao();
        restaurantDAO = db.getRestaurantDao();
        this.r = r;
    }
    public ItemReviewRecyclerAdapter(Context context, FragmentActivity c, List<Review> list) {
        this.mContext = context;
        this.reviews = list;
        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(context);
        reviewDAO  = db.getReviewDao();
        restaurantDAO = db.getRestaurantDao();
        getRestaurants();
    }

    @NonNull
    @Override
    public ItemReviewRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemReviewRecyclerAdapter.ViewHolder holder, final int position) {

        float rating = reviews.get(position).getRating();
        String comment = reviews.get(position).getComment();
        String name = "";
        Date date = new Date();

        if (r != null) {
            name = r.name;
            date = r.dateBooked;
        }
        else if (r_items !=null) {
            for (RestaurantItem res: r_items) {
                if (res.getId().equals(reviews.get(position).getRestaurantId())) {
                    Log.d("reviews", res.name);
                    r = res;
                    name = res.name;
                    date = res.dateBooked;
                }
            }
        }

        holder.comment.setText(comment);
        holder.rating.setRating(rating);
        holder.name.setText(name);
        holder.bDate.setText(getDate(date));
        holder.editBtn.setVisibility(View.GONE);

//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(c, r.name, Toast.LENGTH_SHORT).show();
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("restaurant", r);
//                Intent intent = new Intent(c, ItemActivity.class);
//                intent.putExtras(bundle);
//                c.startActivity(intent);
//
//            }
//        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    deleteItem(reviews.get(position));
                    reviews.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "deleting " + reviews.get(position).getId(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getRestaurants() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
               r_items = restaurantDAO.getAll();
                return null;
            }
        }.execute();
    }

    public String getDate( Date date) {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy,  hh:mm");
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }


    public void deleteItem(final Review item) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                reviewDAO.delete(item);

                for (Review r: reviewDAO.getAllReviews()) {
                    Log.d("database", r.getId() + " " + r.getRestaurantId());
                }
                return null;
            }
        }.execute();
    }

    @Override
    public int getItemCount() { return reviews.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView name;
        private RatingBar rating;
        private TextView comment;
        private TextView bDate;
        private ImageView deleteBtn;
        private ImageView editBtn;
        private RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = mView.findViewById(R.id.review_name);
            rating = mView.findViewById(R.id.my_rating);
            bDate =  mView.findViewById(R.id.review_date);
            comment = mView.findViewById(R.id.my_comment);
            deleteBtn = mView.findViewById(R.id.review_deleteBtn);
            editBtn = mView.findViewById(R.id.review_edit_btn);
            layout = mView.findViewById(R.id.review_item_layout);
        }
    }
}
