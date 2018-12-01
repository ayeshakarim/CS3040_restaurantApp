package com.ayesha.cs3040.CS3040_restaurantApp.item;

public class Item {

    private int[] imageIds;
    private String name;
    private String address;
    private String telNumber;
    private String website;
    private String[] tags;


    public Item(String name, String address, String telNumber) {
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
    }


    public int[] getImageIds() {
        return imageIds;
    }

    public void setImageIds(int[] imageIds) {
        this.imageIds = imageIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
