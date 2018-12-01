package com.ayesha.cs3040.CS3040_restaurantApp.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayesha.cs3040.myapp1.R;

import java.util.List;

public class SearchRecyclerAdapter2 extends RecyclerView.Adapter<SearchRecyclerAdapter2.ViewHolder>{

    public List<StyleItem> style_list;

    public SearchRecyclerAdapter2(List<StyleItem> list) { this.style_list = list; }

    @NonNull
    @Override
    public SearchRecyclerAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_horizontal_scroll_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String name = style_list.get(position).getStyleName();
        int imageURL = style_list.get(position).getImageSource();
        holder.name.setText(name);
        holder.image.setImageResource(imageURL);

    }

    @Override
    public int getItemCount() {
        return style_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.search_text);
            image = itemView.findViewById(R.id.search_image);
        }
    }
}
