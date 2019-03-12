package com.ayesha.cs3040.CS3040_restaurantApp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
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

import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.db.ReviewDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.item.ItemReviewRecyclerAdapter;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.CS3040_restaurantApp.review.Review;
import com.ayesha.cs3040.CS3040_restaurantApp.review.ReviewRecyclerAdapter;
import com.ayesha.cs3040.myapp1.R;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReviewsRecyclerAdapter extends RecyclerView.Adapter<ReviewsRecyclerAdapter.ViewHolder> implements Serializable {

    private ReviewDAO reviewDAO;
    private RestaurantDAO restaurantDAO;
    private Context mContext;
    private FragmentActivity c;
    private List<Review> reviews;
    private List<RestaurantItem> restaurants;
//    private String name;
//    private Date date;


    public ReviewsRecyclerAdapter() {

    }

    public ReviewsRecyclerAdapter(Context context, FragmentActivity c, List<Review> list) {
        this.mContext = context;
        this.reviews = list;
        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(context);
        reviewDAO  = db.getReviewDao();
        restaurantDAO = db.getRestaurantDao();
        getRestaurant();
    }

    @NonNull
    @Override
    public ReviewsRecyclerAdapter.ViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsRecyclerAdapter.ViewHolder holder, final int position) {

//        getRestaurant(reviews.get(position));
        float rating = reviews.get(position).getRating();
        String comment = reviews.get(position).getComment();
        String name = "Restaurant Name ";
        Date date = new Date();

//        RestaurantItem r = getRestaurant(reviews.get(position)).getItem();

//        if (r != null) {
//            name = r.name;
//            date = r.dateBooked;
//
//        } else { Log.d("restaurant", " is null" ); }
//

        if (reviews.get(position).getItem() != null) {
            RestaurantItem restaurantItem = reviews.get(position).getItem();
            name = restaurantItem.name;
            date = restaurantItem.dateBooked;
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

    public void getRestaurant() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                restaurants = restaurantDAO.getAll();

                for (final Review review : reviews) {
                    Log.d("review ", review.getRestaurantId());

                    for (RestaurantItem restaurant : restaurants) {

                    if ( restaurant.getId().equals(review.getRestaurantId())) {
                            review.setItem(restaurant);
                            Log.d("restaurants ", restaurant.id);

                        }
                    }
                }
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

