package com.example.hairstylerecommendation.model;

public class Haircut {
    private String id;
    private String name;
    private String suitableForFaceShape;
    private String suitableForHairType;
    private String suitableForHairSize;
    private String description;
    private int userChoices;

    public Haircut() {

    }

    public Haircut(String id, String name, String suitableForFaceShape, String suitableForHairType, String suitableForHairSize, String description, int userChoices) {
        this.id = id;
        this.name = name;
        this.suitableForFaceShape = suitableForFaceShape;
        this.suitableForHairType = suitableForHairType;
        this.suitableForHairSize = suitableForHairSize;
        this.description = description;
        this.userChoices = userChoices;
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

    public String getSuitableForFaceShape() {
        return suitableForFaceShape;
    }

    public void setSuitableForFaceShape(String suitableForFaceShape) {
        this.suitableForFaceShape = suitableForFaceShape;
    }

    public String getSuitableForHairType() {
        return suitableForHairType;
    }

    public void setSuitableForHairType(String suitableForHairType) {
        this.suitableForHairType = suitableForHairType;
    }

    public String getSuitableForHairSize() {
        return suitableForHairSize;
    }

    public void setSuitableForHairSize(String suitableForHairSize) {
        this.suitableForHairSize = suitableForHairSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserChoices() {
        return userChoices;
    }

    public void setUserChoices(int userChoices) {
        this.userChoices = userChoices;
    }
}
