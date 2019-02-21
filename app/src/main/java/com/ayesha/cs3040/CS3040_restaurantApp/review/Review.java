package com.ayesha.cs3040.CS3040_restaurantApp.review;


import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;

import java.util.List;

public class Review {
    private String id;
    private float rating;
    private String comment;
    private RestaurantItem item;
    private List<FoodItem> foodItems;

    public Review() {
    }

    public Review(String id, float rating, String comment, RestaurantItem item, List<FoodItem> foodItems) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.item = item;
        this.foodItems = foodItems;
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
