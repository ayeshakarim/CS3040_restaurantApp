package com.ayesha.cs3040.CS3040_restaurantApp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;
import com.ayesha.cs3040.CS3040_restaurantApp.review.Review;

import java.util.List;

@Dao
public interface FoodItemDAO {

    @Insert
    void insert(FoodItem item);

    @Update
    void update(FoodItem... items);

    @Delete
    void delete(FoodItem... items);

    @Query("SELECT * FROM fooditem")
    List<Review> getAllItems();

    @Query("SELECT * FROM fooditem WHERE reviewId=:reviewId")
    List<Review> findFoodItemsForReview(final int reviewId);
}
