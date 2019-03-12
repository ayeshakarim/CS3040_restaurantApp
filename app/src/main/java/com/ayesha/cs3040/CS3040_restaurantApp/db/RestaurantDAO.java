package com.ayesha.cs3040.CS3040_restaurantApp.db;


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

    @Query("SELECT * FROM bookings WHERE id IN (:bookingId)")
    RestaurantItem getById(final String bookingId);

    @Query("SELECT * FROM bookings WHERE isVisited LIKE :visited")
    List<RestaurantItem> findBookingsVisited(boolean visited);

    @Query("UPDATE bookings SET isVisited = :visited WHERE id = :rId")
    void setVisited(String rId, boolean visited);

    @Query("UPDATE bookings SET address = :address WHERE id = :rId")
    void setAddress(String rId, String address);

    @Query("UPDATE bookings SET dateBooked = :date WHERE id = :rId")
    void updateBookingDate(String rId, Date date);

    @Query("SELECT * FROM bookings WHERE isVisited LIKE :bool ORDER BY dateBooked ")
    List<RestaurantItem> orderByDate(boolean bool);

    @Query("SELECT * FROM bookings WHERE isVisited LIKE :bool ORDER BY name")
    List<RestaurantItem> orderByName(boolean bool);

    @Insert
    void insert(RestaurantItem... bookings);

    @Delete
    void delete(RestaurantItem booking);
}

