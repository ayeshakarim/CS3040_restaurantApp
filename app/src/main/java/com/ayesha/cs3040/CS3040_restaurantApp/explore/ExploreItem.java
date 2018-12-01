package com.ayesha.cs3040.CS3040_restaurantApp.explore;

public class ExploreItem {
    public String item_name;
    public int id;
    public String item_address;
    public String[] tags;
    public boolean visited;
    public boolean reviewed;

    public static final String[] TAG_LIST =  {"vegetarian", "japanese", "spicy", "dinner", "lunch"};

    public ExploreItem(){

    }

    public ExploreItem(String name, int id, String address, boolean visited, boolean reviewed) {
        this.item_name = name;
        this.id = id;
        this.item_address = address;
        this.tags = TAG_LIST;
        this.visited = visited;
        this.reviewed = reviewed;

    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String name) {
        this.item_name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_address() { return item_address; }

    public void setItem_address(String item_address) { this.item_address = item_address; }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }
}
