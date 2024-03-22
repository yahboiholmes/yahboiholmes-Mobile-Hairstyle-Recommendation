package com.example.hairstylerecommendation.model;

import java.util.Date;

public class HistoryCut {
    private String id;
    private String name;
    private String faceshape;
    private String hairType;
    private String hairSize;
    private String hairColor;
    private String[] listOfRecommend;
    private String userChoices;
    private String userId;
    private String date;

    public HistoryCut(){}

    public HistoryCut(String id, String name, String faceshape, String hairType, String hairSize, String hairColor, String[] listOfRecommend, String userChoices, String userId, String date) {
        this.id = id;
        this.name = name;
        this.faceshape = faceshape;
        this.hairType = hairType;
        this.hairSize = hairSize;
        this.hairColor = hairColor;
        this.listOfRecommend = listOfRecommend;
        this.userChoices = userChoices;
        this.userId = userId;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaceshape() {
        return faceshape;
    }

    public void setFaceshape(String faceshape) {
        this.faceshape = faceshape;
    }

    public String getHairType() {
        return hairType;
    }

    public void setHairType(String hairType) {
        this.hairType = hairType;
    }

    public String getHairSize() {
        return hairSize;
    }

    public void setHairSize(String hairSize) {
        this.hairSize = hairSize;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String[] getListOfRecommend() {
        return listOfRecommend;
    }

    public void setListOfRecommend(String[] listOfRecommend) {
        this.listOfRecommend = listOfRecommend;
    }

    public String getUserChoices() {
        return userChoices;
    }

    public void setUserChoices(String userChoices) {
        this.userChoices = userChoices;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
