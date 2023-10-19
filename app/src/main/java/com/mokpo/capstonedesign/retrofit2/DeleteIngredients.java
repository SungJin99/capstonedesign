package com.mokpo.capstonedesign.retrofit2;

public class DeleteIngredients {
    private String[] ingredient_ids;

    public DeleteIngredients(String[] ingredient_ids) {
        this.ingredient_ids = ingredient_ids;
    }

    public String[] getIngredient_ids() {
        return ingredient_ids;
    }

    public void setIngredient_ids(String[] ingredient_ids) {
        this.ingredient_ids = ingredient_ids;
    }
}
