package com.ayesha.cs3040.CS3040_restaurantApp;

import android.location.Location;

import com.ayesha.cs3040.CS3040_restaurantApp.explore.ExploreFragment;
import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;

import java.util.ArrayList;

public class InfoFinder implements Runnable {

    private final JSONEvaluator jsonEvaluator = new JSONEvaluator();
    private SearchActivity activity;
    private Location location;

    public void setMainAndLocation(SearchActivity activity, Location location) {
        this.activity = activity;
        this.location = location;
    }

    @Override
    public void run() {
        jsonEvaluator.setLocation(location);
        new Thread(jsonEvaluator).start();
        long startTime = System.currentTimeMillis();
        while (jsonEvaluator.getRestaurants() == null) {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (System.currentTimeMillis() - startTime >= 10000) {
                activity.setSuccess(false);
                activity.runOnUiThread(this);
                return;
            }
        }
        activity.setSuccess(getRestaurants().size() < 0 || getRestaurants() != null);
        activity.runOnUiThread(this);
    }

    public void raisePrice() {
        jsonEvaluator.raisePrice();
    }

    public void lowerPrice() {
        jsonEvaluator.lowerPrice();
    }

    public void lowerRadius() {
        jsonEvaluator.lowerRadius();
    }

    public ArrayList<RestaurantItem> getRestaurants() {
        return jsonEvaluator.getRestaurants();
    }

    public void resetParameters() {
        jsonEvaluator.resetAll();
    }
}

