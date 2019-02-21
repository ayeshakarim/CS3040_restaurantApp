package com.ayesha.cs3040.CS3040_restaurantApp.item;

import java.io.Serializable;
import java.util.Arrays;

public class FoodItem implements Serializable {

    private String name;
    private double price;

//    public static final String[] COURSE_OPTIONS =  {"Starter", "Main", "Dessert", "Appetizer", "Side"};

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
