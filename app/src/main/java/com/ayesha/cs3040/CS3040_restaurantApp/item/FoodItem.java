package com.ayesha.cs3040.CS3040_restaurantApp.item;

import java.util.Arrays;

public class FoodItem {

    private String name;
    private float price;
    private String imgURL;
    private String course;

    public static final String[] COURSE_OPTIONS =  {"Starter", "Main", "Dessert", "Appetizer", "Side"};

    public FoodItem(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public FoodItem(String name, float price, String imgURL, String course) {
        this.name = name;
        this.price = price;
        this.imgURL = imgURL;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {

        boolean contains = Arrays.asList(COURSE_OPTIONS).contains(course);

        if(contains) {
            this.course = course;
        }
    }
}
