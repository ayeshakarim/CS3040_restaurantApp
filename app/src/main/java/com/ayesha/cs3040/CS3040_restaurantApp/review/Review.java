package com.ayesha.cs3040.CS3040_restaurantApp.review;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;

import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = RestaurantItem.class, parentColumns = "id", childColumns = "restaurantId", onDelete = CASCADE))
public class Review {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private float rating;

    private String comment;

    private String restaurantId;


    @Ignore
    private RestaurantItem item;
    @Ignore
    private List<FoodItem> foodItems;

    @Ignore
    public Review() {
    }

    public Review( float rating, String comment, String restaurantId) {
        this.rating = rating;
        this.comment = comment;
//        this.item = item;
//        this.foodItems = foodItems;
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

    public void setFoodItem(FoodItem foodItem) {
        this.foodItems.add(foodItem);
    }

    public List<FoodItem> getFoodItems() { return foodItems; }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }
}

