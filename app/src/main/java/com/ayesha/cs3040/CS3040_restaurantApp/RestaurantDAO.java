package com.ayesha.cs3040.CS3040_restaurantApp;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;

import java.util.Date;
import java.util.List;

@Dao
public interface RestaurantDAO {

    @Query("SELECT * FROM bookings")
    List<RestaurantItem> getAll();

    @Query("SELECT * FROM bookings WHERE id IN (:bookingIds)")
    List<RestaurantItem> loadAllByIds(int[] bookingIds);

    @Query("SELECT * FROM bookings WHERE isVisited LIKE :visited")
    List<RestaurantItem> findBookingsVisited(boolean visited);

    @Query("UPDATE bookings SET isVisited = :visited")
    void updateVisited(boolean visited);

    @Query("UPDATE bookings SET address = :address")
    void setAddress(String address);

    @Query("UPDATE bookings SET dateBooked = :date")
    void updateBookingDate(Date date);

    @Insert
    void insert(RestaurantItem booking);

    @Insert
    void insertAll(RestaurantItem... bookings);

    @Delete
    void delete(RestaurantItem booking);
}

