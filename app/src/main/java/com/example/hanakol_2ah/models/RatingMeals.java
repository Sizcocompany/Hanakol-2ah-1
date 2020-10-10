package com.example.hanakol_2ah.models;

public class RatingMeals {
    private String mealName;
    private int rateNumber;
    private double mealAvarage;
    private String sender_rating_email;

    public RatingMeals(String sender_rating_email,String mealName, int rateNumber, double mealAvarage) {
        this.mealName = mealName;
        this.rateNumber = rateNumber;
        this.mealAvarage = mealAvarage;
        this.sender_rating_email = sender_rating_email;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getRateNumber() {
        return rateNumber;
    }

    public void setRateNumber(int rateNumber) {
        this.rateNumber = rateNumber;
    }

    public double getMealAvarage() {
        return mealAvarage;
    }

    public void setMealAvarage(double mealAvarage) {
        this.mealAvarage = mealAvarage;
    }

    public String getSender_rating_email() {
        return sender_rating_email;
    }

    public void setSender_rating_email(String sender_rating_email) {
        this.sender_rating_email = sender_rating_email;
    }
}
