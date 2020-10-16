package com.example.hanakol_2ah.models;

public class RatingMeals {
    private String mealName;
    private String rateNumber;
    private String sender_rating_email;
    private String sender_id;

    public RatingMeals() {
    }

    public RatingMeals( String mealName, String sender_rating_email, String rateNumber ) {
        this.sender_id =sender_id;
        this.mealName = mealName;
        this.rateNumber = rateNumber;
        this.sender_rating_email = sender_rating_email;
    }
    public RatingMeals(String sender_id, String mealName, String sender_rating_email, String rateNumber) {
        this.sender_id =sender_id;
        this.mealName = mealName;
        this.rateNumber = rateNumber;
        this.sender_rating_email = sender_rating_email;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getRateNumber() {
        return rateNumber;
    }

    public void setRateNumber(String rateNumber) {
        this.rateNumber = rateNumber;
    }


    public String getSender_rating_email() {
        return sender_rating_email;
    }

    public void setSender_rating_email(String sender_rating_email) {
        this.sender_rating_email = sender_rating_email;
    }


}
