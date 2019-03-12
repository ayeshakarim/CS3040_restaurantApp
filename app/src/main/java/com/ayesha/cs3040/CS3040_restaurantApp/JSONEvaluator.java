package com.ayesha.cs3040.CS3040_restaurantApp;

import android.location.Location;
import android.util.Log;

import com.ayesha.cs3040.CS3040_restaurantApp.item.RestaurantItem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class JSONEvaluator implements Runnable {

    private static final String key = "AIzaSyBTdkNaGmixwDAj0eYZl0z-Gq9lCvo0bF8";
    private static final String link = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static final String detailsLink = "https://maps.googleapis.com/maps/api/place/details/json?";
    //    private RestaurantItem restaurant;
    private ArrayList<RestaurantItem> restaurants;
    private int radius = 1000;
    private int maxPrice = 5;
    private int minPrice = 0;
    private Location location;
    private String keyword;
    private URL url;
    private static int size;

    void setLocation(Location location) {
        this.location = location;
    }

    void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    static int getSize() { return size;}

    public void run() {
        try {
            for (int i=0; i < 2; i++) {

                Log.w("iiiii", i + "");

                if( keyword == null) {
                Log.w("url", buildURL(location, false));
                url = new URL(buildURL(location, false));}
                else {
                    radius = 10000;
                    if(i == 1){ radius = 50000; }
                    Log.w("url", buildURL(location, true));
                    url = new URL(buildURL(location, true));}
                Scanner scanner = new Scanner(url.openConnection().getInputStream());
                restaurants = new ArrayList<>();
                double longitude;
                double latitude;
                String name;
                String id;
                int priceLevel;
                float rating;
                String website = "Website Not Found :(";
                String currentLine;
                int startPos;
                int endPos;
                int pos = 0;
                while (scanner.hasNext()) {
                    currentLine = scanner.nextLine();
                    if (currentLine.contains("geometry")) {
                        Log.w("pos", String.valueOf(pos++));
                        longitude = -1;
                        latitude = -1;
                        name = null;
                        id = null;
                        priceLevel = -1;
                        rating = -1;
                        while (scanner.hasNext()) {
                            currentLine = scanner.nextLine();
                            if (currentLine.contains("\"lat\"")) {
                                startPos = currentLine.indexOf(":") + 2;
                                endPos = currentLine.indexOf(",");
                                latitude = Double.parseDouble(currentLine.substring(startPos, endPos));
                            } else if (currentLine.contains("\"lng\"")) {
                                startPos = currentLine.indexOf(":") + 2;
                                longitude = Double.parseDouble(currentLine.substring(startPos));
                            } else if (currentLine.contains("\"name\"")) {
                                startPos = currentLine.indexOf(":") + 3;
                                endPos = currentLine.indexOf(",") - 1;
                                name = currentLine.substring(startPos, endPos);
//							} else if (currentLine.contains("photo_reference")) {
//								startPos = currentLine.indexOf(":") + 3;
//								endPos = currentLine.indexOf(",")-1;
//								String photoReference = currentLine.substring(startPos, endPos);
//								photoLocation = getPhotoLocation(photoReference);
                            } else if (currentLine.contains("\"price_level\"")) {
                                startPos = currentLine.indexOf(":") + 2;
                                endPos = currentLine.indexOf(",");
                                priceLevel = Integer.parseInt(currentLine.substring(startPos, endPos));
                            } else if (currentLine.contains("\"rating\"")) {
                                startPos = currentLine.indexOf(":") + 2;
                                endPos = currentLine.indexOf(",");
                                rating = Float.parseFloat(currentLine.substring(startPos, endPos));
                            } else if (currentLine.contains("\"reference\"")) {
                                startPos = currentLine.indexOf(":") + 3;
                                endPos = currentLine.indexOf(",") - 1;
                                String reference = currentLine.substring(startPos, endPos);
                                Log.w("re", reference);
                                id = reference;
                                website = getWebsite(reference);
                                break;
                            }
                        }
                        Log.w("name", name);

                            if (restaurants.size() < 20) {
                                if (id != null && name != null && priceLevel != -1 && rating != -1 && latitude != -1 && longitude != -1) {
                                    restaurants.add(new RestaurantItem(id, name, priceLevel, rating, latitude, longitude, website, false));
//                            Log.v("Size", String.valueOf(restaurants.size()));
                                }
                            } else {
                                return;
                            }
//                        }
                    }
                }
                Log.v("Size", String.valueOf(restaurants.size()));
                size = restaurants.size();

                if( i != 1) {
                    if (restaurants.size() != 20) {
                        radius *= 2;
                        continue;
                    }
                }
//                }
                ArrayList<RestaurantItem> oldList = restaurants;
                restaurants = new ArrayList<>();
                restaurants = oldList;
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            restaurants = new ArrayList<RestaurantItem>();
            restaurants.add(RestaurantItem.INVALID);        }
    }

    private String buildURL(Location location, boolean hasKeyword) {

        if(hasKeyword){
            return link + "key=" + key
                    + "&radius=" + radius
                    + "&keyword=" + keyword
                    + "&maxprice=" + maxPrice
                    + "&minprice=" + minPrice
                    + "&type=restaurant"
                    + "&location="
                    + location.getLatitude()
                    + "," + location.getLongitude();
        }
        else {
            return link + "key=" + key
                    + "&radius=" + radius
                    + "&maxprice=" + maxPrice
                    + "&minprice=" + minPrice
                    + "&type=restaurant"
                    + "&location="
                    + location.getLatitude()
                    + "," + location.getLongitude();
        }
    }

    void resetAll() {
        radius = 1000;
        minPrice = 0;
        maxPrice = 5;
        location = null;
        restaurants = null;
    }

    public ArrayList<RestaurantItem> getRestaurants() {
        return restaurants;
    }

    private String getWebsite(String reference) {
        try {
            Log.w("is",reference);
            String url = detailsLink + "reference=" + reference + "&key="+key;
            Scanner scanner = new Scanner(new URL(url).openConnection().getInputStream());
            String currentLine;
            while (scanner.hasNext()) {
                currentLine = scanner.nextLine();
                if (currentLine.contains("\"website\"")) {
                    int startPos = currentLine.indexOf(":") + 3;
                    int endPos = currentLine.indexOf("\"", startPos);
                    return currentLine.substring(startPos, endPos);
                }
            }
        } catch (IOException e) {
            Log.w("we","notfound");
            e.printStackTrace();
        }
        return "Website Not Found :(";
    }
}
