package com.example.hanakol_2ah.models;

public class Meals {
    private String MealName;
    private String Description;
    private String Steps;
    private String ImageURL;
    private Float MealRate;
    private String mealOwner;
    private String mealSender;

    public Meals() {
    }


    public Meals(String description, String imageURL, String mealName, Float mealRate, String steps, String mealOwner) {
        MealName = mealName;
        Description = description;
        Steps = steps;
        ImageURL = imageURL;
        MealRate = mealRate;
        this.mealOwner = mealOwner;
    }


    public String getMealName() {
        return MealName;
    }

    public void setMealName(String mealName) {
        MealName = mealName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSteps() {
        return Steps;
    }

    public void setSteps(String steps) {
        Steps = steps;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public Float getMealRate() {
        return MealRate;
    }

    public void setMealRate(Float mealRate) {
        MealRate = mealRate;
    }

    public String getMealOwner() {
        return mealOwner;
    }

    public void setMealOwner(String mealOwner) {
        this.mealOwner = mealOwner;
    }

    public String getMealSender() {
        return mealSender;
    }

    public void setMealSender(String mealSender) {
        this.mealSender = mealSender;
    }


}
