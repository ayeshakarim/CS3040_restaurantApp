package com.ayesha.cs3040.CS3040_restaurantApp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ayesha.cs3040.CS3040_restaurantApp.review.Review;

import java.util.List;

@Dao
public interface ReviewDAO {

    @Insert
    void insert(Review review);

    @Update
    void update(Review... reviews);

    @Delete
    void delete(Review... reviews);

    @Query("SELECT * FROM review")
    List<Review> getAllReviews();

    @Query("SELECT * FROM review WHERE restaurantId=:restaurantId")
    List<Review> findReviewsForRestaurant(final int restaurantId);
}
