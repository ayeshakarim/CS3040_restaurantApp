package com.ayesha.cs3040.CS3040_restaurantApp.explore;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.ayesha.cs3040.CS3040_restaurantApp.item.ItemActivity;
import com.ayesha.cs3040.myapp1.R;

public class ExploreRecyclerAdapter extends RecyclerView.Adapter<ExploreRecyclerAdapter.ViewHolder> {

    public List<ExploreItem> home_list;
    private Context mContext;

    public ExploreRecyclerAdapter(Context context, List<ExploreItem> list) {
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
    public void onBindViewHolder(@NonNull ExploreRecyclerAdapter.ViewHolder holder, int position) {
        final int id = home_list.get(position).getId();
        final String item_name = home_list.get(position).getItem_name();
        String item_address = home_list.get(position).getItem_address();

        holder.name.setText(item_name);
        holder.image.setImageResource(id);
        holder.address.setText(item_address);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, item_name, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, ItemActivity.class);
//                intent.putExtra("item_image", id);
                intent.putExtra("item_name", item_name);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return home_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView name;
        private ImageView image;
        private TextView address;
        private ConstraintLayout parentLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            name = mView.findViewById(R.id.item_name);
            image = mView.findViewById(R.id.item_image);
            address = mView.findViewById(R.id.item_address);
            parentLayout = mView.findViewById(R.id.bookings_parent_layout);
        }
    }
}
