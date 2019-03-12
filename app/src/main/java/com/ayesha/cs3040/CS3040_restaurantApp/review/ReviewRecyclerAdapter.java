package com.ayesha.cs3040.CS3040_restaurantApp.review;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayesha.cs3040.CS3040_restaurantApp.db.FoodItemDAO;
import com.ayesha.cs3040.CS3040_restaurantApp.db.RestaurantDatabase;
import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.myapp1.R;

import java.io.Serializable;
import java.util.List;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder> implements Serializable {

    private FoodItemDAO foodDAO;
    private Context mContext;
    private List<FoodItem> food_list;

    public ReviewRecyclerAdapter(Context context, List<FoodItem> list) {
        this.mContext = context;
        this.food_list = list;
        RestaurantDatabase db = RestaurantDatabase.getInMemoryDatabase(context);
        foodDAO  = db.getFoodItemDao();
    }


    @NonNull
    @Override
    public ReviewRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewRecyclerAdapter.ViewHolder holder, final int position) {

        final String name = food_list.get(position).getName();
        String price = "£ " + Double.toString(food_list.get(position).getPrice());

        holder.name.setText(name);
        holder.price.setText(price);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    deleteItem(food_list.get(position));
                    food_list.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "deleting " + food_list.get(position).getName(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void deleteItem(final FoodItem item) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                foodDAO.delete(item);
                for (FoodItem r: foodDAO.getAllItems()) {
                    Log.d("database", r.getFoodId() + " " + r.getName());
                }
                return null;
            }
        }.execute();
    }

    @Override
    public int getItemCount() {
        return food_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView name;
        private TextView price;
        private ImageView deleteBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = mView.findViewById(R.id.review_food_name);
            price = mView.findViewById(R.id.review_food_price);
            deleteBtn = mView.findViewById(R.id.review_food_binBtn);
        }
    }
}
