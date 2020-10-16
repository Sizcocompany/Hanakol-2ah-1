//package com.example.hanakol_2ah.models;
//
//public class Meals {
//    private String MealName;
//    private String Description;
//    private String Steps;
//    private String ImageURL;
//    private Float MealRate;
//    private String mealOwner;
//    private String mealOwnerEmail;
//    private String mealSender;
//    private String mealCreationDate;
//
//    public Meals() {
//    }
//
//
//    public Meals(String description, String imageURL, String mealName, Float mealRate, String steps, String mealOwner, String mealCreationDate , String mealOwnerEmail ) {
//        MealName = mealName;
//        Description = description;
//        Steps = steps;
//        ImageURL = imageURL;
//        MealRate = mealRate;
//        this.mealOwner = mealOwner;
//        this.mealOwnerEmail = mealOwnerEmail;
//        this.mealCreationDate = mealCreationDate;
//    }
//
//    public String getMealCreationDate() {
//        return mealCreationDate;
//    }
//
//    public void setMealCreationDate(String mealCreationDate) {
//        this.mealCreationDate = mealCreationDate;
//    }
//
//    public String getMealName() {
//        return MealName;
//    }
//
//    public void setMealName(String mealName) {
//        MealName = mealName;
//    }
//
//    public String getDescription() {
//        return Description;
//    }
//
//    public void setDescription(String description) {
//        Description = description;
//    }
//
//    public String getSteps() {
//        return Steps;
//    }
//
//    public void setSteps(String steps) {
//        Steps = steps;
//    }
//
//    public String getImageURL() {
//        return ImageURL;
//    }
//
//    public void setImageURL(String imageURL) {
//        ImageURL = imageURL;
//    }
//
//    public Float getMealRate() {
//        return MealRate;
//    }
//
//    public void setMealRate(Float mealRate) {
//        MealRate = mealRate;
//    }
//
//    public String getMealOwner() {
//        return mealOwner;
//    }
//
//    public void setMealOwner(String mealOwner) {
//        this.mealOwner = mealOwner;
//    }
//
//    public String getMealSender() {
//        return mealSender;
//    }
//
//    public void setMealSender(String mealSender) {
//        this.mealSender = mealSender;
//    }
//
//    public String getMealOwnerEmail() {
//        return mealOwnerEmail;
//    }
//
//    public void setMealOwnerEmail(String mealOwnerEmail) {
//        this.mealOwnerEmail = mealOwnerEmail;
//    }
//}









package com.example.hanakol_2ah.models;

public class Meals {

    private String MealName;
    private String Description;
    private String Steps;
    private String ImageURL;
    private Float MealRate;
    private String mealOwner;
    private String mealSender;
    private String mealCreationDate;
    private  int mealTotalVotes;

    public Meals() {
    }


    public Meals(String description, String imageURL, String mealName, Float mealRate, String steps, String mealOwner, String mealCreationDate , int mealTotalVotes) {
        MealName = mealName;
        Description = description;
        Steps = steps;
        ImageURL = imageURL;
        MealRate = mealRate;
        this.mealOwner = mealOwner;
        this.mealCreationDate = mealCreationDate;
        this.mealTotalVotes = mealTotalVotes;
    }

    public String getMealCreationDate() {
        return mealCreationDate;
    }

    public void setMealCreationDate(String mealCreationDate) {
        this.mealCreationDate = mealCreationDate;
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

    public int getMealTotalVotes() {
        return mealTotalVotes;
    }

    public void setMealTotalVotes(int mealTotalVotes) {
        this.mealTotalVotes = mealTotalVotes;
    }
}
