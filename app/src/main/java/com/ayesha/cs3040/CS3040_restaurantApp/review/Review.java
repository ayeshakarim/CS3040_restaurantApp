package com.ayesha.cs3040.CS3040_restaurantApp.review;


import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;
import com.ayesha.cs3040.CS3040_restaurantApp.explore.ExploreItem;

public class Review {
    private String id;
    private int rating;
    private String comment;
    private ExploreItem item;
    private FoodItem foodItem;

    public Review() {
    }

    public Review(String id, int rating, String comment, ExploreItem item, FoodItem foodItem) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.item = item;
        this.foodItem = foodItem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ExploreItem getItem() {
        return item;
    }

    public void setItem(ExploreItem item) {
        this.item = item;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }
}
