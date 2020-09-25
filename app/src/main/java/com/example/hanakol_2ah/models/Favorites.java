package com.example.hanakol_2ah.models;

public class Favorites {
    private String favoritesMealName;
    private String favoritesDescription;
    private String favoritesSteps;
    private String favoritesImageURL;

    public String getFavoritesMealName() {
        return favoritesMealName;
    }

    public void setFavoritesMealName(String favoritesMealName) {
        this.favoritesMealName = favoritesMealName;
    }

    public String getFavoritesDescription() {
        return favoritesDescription;
    }

    public void setFavoritesDescription(String favoritesDescription) {
        this.favoritesDescription = favoritesDescription;
    }

    public String getFavoritesSteps() {
        return favoritesSteps;
    }

    public void setFavoritesSteps(String favoritesSteps) {
        this.favoritesSteps = favoritesSteps;
    }

    public String getFavoritesImageURL() {
        return favoritesImageURL;
    }

    public void setFavoritesImageURL(String favoritesImageURL) {
        this.favoritesImageURL = favoritesImageURL;
    }

    public Float getFavoritesMealRate() {
        return favoritesMealRate;
    }

    public void setFavoritesMealRate(Float favoritesMealRate) {
        this.favoritesMealRate = favoritesMealRate;
    }

    public Favorites(String favoritesMealName, String favoritesDescription, String favoritesSteps, String favoritesImageURL, Float favoritesMealRate) {
        this.favoritesMealName = favoritesMealName;
        this.favoritesDescription = favoritesDescription;
        this.favoritesSteps = favoritesSteps;
        this.favoritesImageURL = favoritesImageURL;
        this.favoritesMealRate = favoritesMealRate;
    }

    private Float favoritesMealRate;
}
