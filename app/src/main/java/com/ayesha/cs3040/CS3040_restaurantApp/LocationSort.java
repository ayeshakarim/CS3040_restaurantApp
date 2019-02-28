package com.ayesha.cs3040.CS3040_restaurantApp;

import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;
import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;

public class LocationSort implements Comparator<RestaurantItem> {

    private double currentLat;
    private double currentLon;

public LocationSort(double lat, double lon){
        currentLat = lat;
        currentLon = lon;
        }

@Override
public int compare(final RestaurantItem location1, final RestaurantItem location2) {
        double lat1 = location1.getLatitude();
        double lon1 = location1.getLongitude();
        double lat2 = location2.getLatitude();
        double lon2 = location2.getLongitude();

        double distanceToPlace1 = distance(currentLat, currentLon, lat1, lon1);
        double distanceToPlace2 = distance(currentLat, currentLon, lat2, lon2);
        return (int) (distanceToPlace1 - distanceToPlace2);
        }

public double distance(double fromLat, double fromLon, double toLat, double toLon) {
        double radius = 6378137;   // approximate Earth radius, *in meters*
        double deltaLat = toLat - fromLat;
        double deltaLon = toLon - fromLon;
        double angle = 2 * Math.asin( Math.sqrt(
        Math.pow(Math.sin(deltaLat/2), 2) +
        Math.cos(fromLat) * Math.cos(toLat) *
        Math.pow(Math.sin(deltaLon/2), 2) ) );
        return radius * angle;
        }
}
