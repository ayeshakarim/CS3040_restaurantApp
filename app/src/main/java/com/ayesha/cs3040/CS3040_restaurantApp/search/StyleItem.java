package com.ayesha.cs3040.CS3040_restaurantApp.search;

public class StyleItem {

    private String styleName;
    private int imageSource;

    public StyleItem(){

    }

    public StyleItem(String styleName, int imageSource) {
        this.styleName = styleName;
        this.imageSource = imageSource;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }
}
