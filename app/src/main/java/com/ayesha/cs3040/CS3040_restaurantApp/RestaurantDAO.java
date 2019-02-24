package com.ayesha.cs3040.CS3040_restaurantApp;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;

import java.util.List;

@Dao
public interface RestaurantDAO {

    @Query("SELECT * FROM bookings")
    List<RestaurantItem> getAll();

    @Query("SELECT * FROM bookings WHERE bid IN (:bookingIds)")
    List<RestaurantItem> loadAllByIds(int[] bookingIds);

    @Query("SELECT * FROM bookings WHERE name LIKE :restaurant_name LIMIT 1")
    RestaurantItem findByName(String restaurant_name);

    @Insert
    void insertAll(RestaurantItem... bookings);

    @Delete
    void delete(RestaurantItem booking);
}

