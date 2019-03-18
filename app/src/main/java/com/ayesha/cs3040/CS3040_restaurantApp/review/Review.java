package com.ayesha.cs3040.CS3040_restaurantApp.review;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = RestaurantItem.class, parentColumns = "id", childColumns = "restaurantId", onDelete = CASCADE))
public class Review implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private float rating;

    private String comment;

    private String imageUri;

    @NonNull
    private String restaurantId;


    @Ignore
    private RestaurantItem item;

    @Ignore
    public Review(String id) {

        this.restaurantId = id;
    }

    public Review( float rating, String comment, String restaurantId) {
        this.rating = rating;
        this.comment = comment;
        this.restaurantId = restaurantId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRestaurantId() { return restaurantId; }

    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }

    public RestaurantItem getItem() {
        return item;
    }

    public void setItem(RestaurantItem item) {
        this.item = item;
    }

    public String getImageUri() { return imageUri; }

    public void setImageUri(Uri imageUri) { this.imageUri = imageUri.toString(); }

    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
}

