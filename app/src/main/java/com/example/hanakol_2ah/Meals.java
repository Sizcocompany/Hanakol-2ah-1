package com.example.hanakol_2ah;

import android.widget.ImageView;

public class Meals {
    private String Name;
    private String Ingredients;
    private String Steps;
    private ImageView Image;

    public Meals() {
    }

    public Meals(String name, String ingredients, String steps) {
        Name = name;
        Ingredients = ingredients;
        Steps = steps;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public void setSteps(String steps) {
        Steps = steps;
    }

    public void setImage(ImageView image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public String getSteps() {
        return Steps;
    }

    public ImageView getImage() {
        return Image;
    }
}
