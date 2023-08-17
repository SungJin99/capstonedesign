package com.mokpo.capstonedesign.retrofit2;

import java.util.List;

public class IngredientAddRequest {

    private List<FoodItem> foodlist;

    public List<FoodItem> getFoodlist() {
        return foodlist;
    }

    public void setFoodlist(List<FoodItem> foodlist) {
        this.foodlist = foodlist;
    }

    public static class FoodItem {
        private String name;
        private String memo;
        private int count;
        private String date;

        public FoodItem(String name, String memo, int count, String date) {
            this.name = name;
            this.memo = memo;
            this.count = count;
            this.date = date;
        }

        // getters, setters 생략
    }
}
