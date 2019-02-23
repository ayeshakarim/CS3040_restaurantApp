package com.ayesha.cs3040.CS3040_restaurantApp.item;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.ayesha.cs3040.CS3040_restaurantApp.LocationIdentifier;
import com.ayesha.cs3040.CS3040_restaurantApp.MainActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.SearchActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.item.FoodItem;

import java.io.Serializable;
import java.util.List;

public class RestaurantItem implements Serializable {
    public LocationIdentifier location;
    public String item_name;
    public int priceLevel;
    public double latitude;
    public double longitude;
    public float rating;
    public String website;
    public String item_address;
    public boolean booked;
    public boolean visited;
    public boolean reviewed;
    public List<FoodItem> mealList;

    public static final RestaurantItem INVALID = new RestaurantItem();


    public RestaurantItem(){

    }

    public RestaurantItem(String name, int priceLevel, float rating, String address, String website) {
        this.item_name = name;
        this.item_address = address;
        this.priceLevel = priceLevel;
        this.rating = rating;
        this.website = website;

    }


    public RestaurantItem(String name, int priceLevel, float rating, double latitude, double longitude, String website) {
        this.item_name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = new LocationIdentifier(latitude, longitude);
        this.priceLevel = priceLevel;
        this.rating = rating;
        this.website = website;

    }


    public static void setSearchActivity(SearchActivity activity) {
        LocationIdentifier.setSearchActivity(activity);
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String name) {
        this.item_name = name;
    }


    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public  String getAddress() {
        return location.getAddress();
    }

    public  Location getLocation() {
        return location.getLocation();
    }

    public String getItem_address() { return item_address; }

    public void setItem_address(String item_address) { this.item_address = item_address; }



    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public boolean isBooked() {  return booked;  }

    public void setBooked(boolean booked) { this.booked = booked; }


    public void setMealItem(String name, double price) {

        FoodItem food_item = new FoodItem (name, price);
        mealList.add(food_item);
    }

    public String getMealItemName(int position) {
        return mealList.get(position).getName();
    }

    public double getMealItemPrice(int position) {
        return mealList.get(position).getPrice();
    }

    public List<FoodItem> getMealList() {
        return mealList;
    }
}
