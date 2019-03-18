package com.ayesha.cs3040.CS3040_restaurantApp.search;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class LocationIdentifier implements Serializable {
    private final double latitude;
    private final double longitude;
    private String address;
    private boolean addressFound = true;
    private static SearchActivity activity;
    private final Location location = new Location("");

    public LocationIdentifier(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        try {
            createStreetAddress();
        } catch (IOException e) {
            addressFound = false;
            e.printStackTrace();
        }
    }

    public static void setSearchActivity(SearchActivity mA) {
        activity = mA;
    }

    private void createStreetAddress() throws IOException {
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        address = addresses.get(0).getAddressLine(0);
    }

    public String getAddress() {
        if (addressFound)
            return address;
        return "Address not found";
    }

    public Location getLocation() {
        return location;
    }
}

