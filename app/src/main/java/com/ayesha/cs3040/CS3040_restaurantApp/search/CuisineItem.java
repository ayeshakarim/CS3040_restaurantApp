package com.ayesha.cs3040.CS3040_restaurantApp.search;

public class CuisineItem {

    private String CuisineName;
    private int imageSource;

    public CuisineItem(){

    }

    public CuisineItem(String cuisineName, int imageSource) {
        CuisineName = cuisineName;
        this.imageSource = imageSource;
    }

    public String getCuisineName() {
        return CuisineName;
    }

    public void setCuisineName(String cuisineName) {
        CuisineName = cuisineName;
    }

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }
}
