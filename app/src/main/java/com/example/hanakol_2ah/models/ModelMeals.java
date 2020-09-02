package com.example.hanakol_2ah.models;

import android.widget.Spinner;

class ModelMeals {

    private String mealName ;
    private String mealDescription ;
    private String mealSteps ;
    private String ImageUrl ;
    private String ratingBar;
    private String meatCategory;


    public ModelMeals(String mealName, String mealDescription, String mealSteps, String imageUrl, String ratingBar, String meatCategory ) {
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        this.mealSteps = mealSteps;
        ImageUrl = imageUrl;
        this.ratingBar = ratingBar;
        this.meatCategory = meatCategory;
    }

    public ModelMeals() {
    }

    public String getMeatCategory() {
        return meatCategory;
    }

    public void setMeatCategory(String meatCategory) {
        this.meatCategory = meatCategory;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }

    public String getMealSteps() {
        return mealSteps;
    }

    public void setMealSteps(String mealSteps) {
        this.mealSteps = mealSteps;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(String ratingBar) {
        this.ratingBar = ratingBar;
    }
}
