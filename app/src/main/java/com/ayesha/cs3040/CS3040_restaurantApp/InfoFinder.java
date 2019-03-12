package com.ayesha.cs3040.CS3040_restaurantApp;

import android.location.Location;
import android.util.Log;

import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;

import java.util.ArrayList;

public class InfoFinder implements Runnable {

    private final JSONEvaluator jsonEvaluator = new JSONEvaluator();
    private SearchActivity activity;
    private Location location;
    private String keyword;


    public void setMainAndLocation(SearchActivity activity, Location location) {
        this.activity = activity;
        this.location = location;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void run() {
        jsonEvaluator.setLocation(location);
        jsonEvaluator.setKeyword(keyword);
        new Thread(jsonEvaluator).start();
        long startTime = System.currentTimeMillis();
        while (jsonEvaluator.getRestaurants() == null || jsonEvaluator.getRestaurants().size() != 20) {
            try {
                Thread.sleep(0);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (System.currentTimeMillis() - startTime >= 10000) {
                activity.setSuccess(false);
                activity.runOnUiThread(activity);
//                activity.setRestaurantList();
                return;
            }
        }



        activity.setSuccess(getRestaurants().contains(RestaurantItem.INVALID));
        activity.runOnUiThread(activity);
//        activity.setRestaurantList();

    }


    public ArrayList<RestaurantItem> getRestaurants() {
        return jsonEvaluator.getRestaurants();
    }

    public void resetParameters() {
        jsonEvaluator.resetAll();
    }
}

