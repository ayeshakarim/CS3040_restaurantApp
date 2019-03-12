package com.ayesha.cs3040.CS3040_restaurantApp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.ayesha.cs3040.CS3040_restaurantApp.review.Review;

@Database(entities = { RestaurantItem.class, Review.class, FoodItem.class},
        version = 3)
@TypeConverters({Converters.class})
public abstract class RestaurantDatabase extends RoomDatabase {

    private static RestaurantDatabase INSTANCE;


    public static RestaurantDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RestaurantDatabase.class, "restaurant-app.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract RestaurantDAO getRestaurantDao();
    public abstract ReviewDAO getReviewDao();
    public abstract FoodItemDAO getFoodItemDao();
}
