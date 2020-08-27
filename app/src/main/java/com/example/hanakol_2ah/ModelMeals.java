package com.example.hanakol_2ah;

class ModelMeals {

    private String mealName ;
    private String mealDescription ;
    private String mealSteps ;
    private String ImageUrl ;
    private String ratingBar;

    public ModelMeals(String mealName, String mealDescription, String mealSteps, String imageUrl, String ratingBar) {
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        this.mealSteps = mealSteps;
        ImageUrl = imageUrl;
        this.ratingBar = ratingBar;
    }

    public ModelMeals() {
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
