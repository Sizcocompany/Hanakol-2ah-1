package com.example.hanakol_2ah.models;

public class Meals {
    private String MealName;
    private String Description;
    private String Steps;
    private String ImageURL;

    public Meals() {
    }

    public Meals(String imageURL, String mealName, String description, String steps) {
        MealName = mealName;
        Description = description;
        Steps = steps;
        ImageURL = imageURL;
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
}
