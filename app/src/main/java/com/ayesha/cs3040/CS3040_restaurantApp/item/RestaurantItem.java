package com.ayesha.cs3040.CS3040_restaurantApp.item;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.ayesha.cs3040.CS3040_restaurantApp.LocationIdentifier;
import com.ayesha.cs3040.CS3040_restaurantApp.SearchActivity;
import com.ayesha.cs3040.CS3040_restaurantApp.review.Review;
import com.google.firebase.database.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "bookings")
public class RestaurantItem implements Serializable {

    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "name")
    public String name;

    public int priceLevel;

    public double latitude;

    public double longitude;

    public float rating;

    public String website;

    public String address;

    @ColumnInfo(name = "isVisited")
    public boolean visited;

    public Date dateBooked;

    @Ignore
    public List<Review> reviewList;

    @Ignore
    public boolean booked;
    @Ignore
    public LocationIdentifier location;

    @Ignore
    public static final RestaurantItem INVALID = new RestaurantItem();


    @Ignore
    public RestaurantItem(){

    }

    @Ignore
    public RestaurantItem(String id, String name, int priceLevel, float rating, String address, String website) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.priceLevel = priceLevel;
        this.rating = rating;
        this.website = website;

    }


    public RestaurantItem(String id, String name, int priceLevel, float rating, double latitude, double longitude, String website, boolean visited) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
//        this.location = new LocationIdentifier(latitude, longitude);
        this.priceLevel = priceLevel;
        this.rating = rating;
        this.website = website;
        this.visited = visited;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        location = new LocationIdentifier(latitude, longitude);
        setItem_address(location.getAddress());
        return location.getAddress();
    }

    public  Location getLocation() {

        location = new LocationIdentifier(latitude, longitude);
        return location.getLocation();
    }

    public String getItem_address() { return address; }

    public void setItem_address(String item_address) { this.address = item_address; }



    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Date getDateBooked() {
        return dateBooked;
    }

    public void setDateBooked(Date dateBooked) {
        this.dateBooked = dateBooked;
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


    public void setReview( float rating, String comment) {

        Review review = new Review ( rating, comment, getId());
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
