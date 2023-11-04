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
        private String expiration_info;

        private String manufacture_date;

        public FoodItem(String name, String memo, int count, String expiration_info, String manufacture_date) {
            this.name = name;
            this.memo = memo;
            this.count = count;
            this.expiration_info = expiration_info;
            this.manufacture_date = manufacture_date;
        }

        // getters, setters 생략
    }
}
