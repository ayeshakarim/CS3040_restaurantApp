package com.ayesha.cs3040.CS3040_restaurantApp.review;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;

import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = RestaurantItem.class, parentColumns = "id", childColumns = "restaurantId", onDelete = CASCADE))
public class Review {

    @PrimaryKey(autoGenerate = true)
    private String id;

    @ColumnInfo
    private float rating;

    @ColumnInfo
    private String comment;

    @ColumnInfo
    private String restaurantId;


    private RestaurantItem item;
    private List<FoodItem> foodItems;

    public Review() {
    }

    public Review( float rating, String comment, RestaurantItem item, List<FoodItem> foodItems) {
        this.rating = rating;
        this.comment = comment;
        this.item = item;
        this.foodItems = foodItems;
        this.restaurantId = item.getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public RestaurantItem getItem() {
        return item;
    }

    public void setItem(RestaurantItem item) {
        this.item = item;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItems.add(foodItem);
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }
}
