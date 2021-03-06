package com.ayesha.cs3040.CS3040_restaurantApp.item;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.ayesha.cs3040.CS3040_restaurantApp.review.Review;

import java.io.Serializable;
import java.util.Arrays;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Review.class, parentColumns = "id", childColumns = "reviewId", onDelete = CASCADE))
public class FoodItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int foodId = 0;

    @ColumnInfo(name = "foodItem")
    @NonNull
    private String name;

    @NonNull
    @ColumnInfo(name = "foodPrice")
    private double price;

    @NonNull
    @ColumnInfo
    private int reviewId;

//    public static final String[] COURSE_OPTIONS =  {"Starter", "Main", "Dessert", "Appetizer", "Side"};

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;

    }

    @Ignore
    public FoodItem(int id) {

        this.reviewId = id;
    }


    public int getFoodId() { return foodId; }

    public void setFoodId(int foodId) { this.foodId = foodId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

}
