package com.ayesha.cs3040.CS3040_restaurantApp.item;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.LocationIdentifier;
import com.ayesha.cs3040.CS3040_restaurantApp.SearchActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.review.Review;

import java.io.Serializable;
import java.util.List;

@Entity
public class RestaurantItem implements Serializable {
    public LocationIdentifier location;

    @PrimaryKey
    public String id;

    @ColumnInfo(name = "name")
    public String item_name;

    @ColumnInfo(name = "priceLevel")
    public int priceLevel;

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double longitude;

    @ColumnInfo(name = "rating")
    public float rating;

    @ColumnInfo(name = "website")
    public String website;

    @ColumnInfo(name = "isVisited")
    public boolean visited;

    public List<Review> reviewList;
    public String item_address;
    public boolean booked;


    public static final RestaurantItem INVALID = new RestaurantItem();


    public RestaurantItem(){

    }

    public RestaurantItem(String id, String name, int priceLevel, float rating, String address, String website) {
        this.id = id;
        this.item_name = name;
        this.item_address = address;
        this.priceLevel = priceLevel;
        this.rating = rating;
        this.website = website;

    }


    public RestaurantItem(String id, String name, int priceLevel, float rating, double latitude, double longitude, String website) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        setItem_address(location.getAddress());
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

//    public boolean isReviewed() {
//        return reviewed;
//    }
//
//    public void setReviewed(boolean reviewed) {
//        this.reviewed = reviewed;
//    }

    public boolean isBooked() {  return booked;  }

    public void setBooked(boolean booked) { this.booked = booked; }


    public void setReview( float rating, String comment, List<FoodItem> foodItems) {

        Review review = new Review ( rating, comment, this, foodItems);
        reviewList.add(review);
    }

//    public String getMealItemName(int position) {
//        return reviewList.get(position).getName();
//    }
//
//    public double getMealItemPrice(int position) {
//        return reviewList.get(position).getPrice();
//    }

    public void setReviews(List<Review> reviews){this.reviewList = reviews;}
    public List<Review> getReviews() {
        return reviewList;
    }

    public int getReviewListSize() {
        return reviewList.size();
    }
}
