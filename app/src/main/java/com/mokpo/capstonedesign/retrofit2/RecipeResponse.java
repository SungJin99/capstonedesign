package com.mokpo.capstonedesign.retrofit2;
import java.util.List;
public class RecipeResponse {
    private List<Recipe> recipes;

    // getter and setter methods
    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
