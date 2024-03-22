package com.example.hairstylerecommendation.model;

public class HaircutTree {
    private String Id;
    private String name;
    private int count;

    public HaircutTree(){}

    public HaircutTree(String id, String name, int count) {
        Id = id;
        this.name = name;
        this.count = count;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
